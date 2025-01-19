package com.example.getwell.screens.resourcesection.practical

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException
import java.util.concurrent.TimeUnit

class YouTubeService {
    data class CacheEntry(
        val video: YouTubeVideo,
        val timestamp: Long = System.currentTimeMillis()
    )

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    // Cache with size limit and expiry time
    private val cache = object : LinkedHashMap<String, CacheEntry>(100, 0.75f, true) {
        private val MAX_ENTRIES = 100
        private val CACHE_DURATION_MS = 24 * 60 * 60 * 1000 // 24 hours

        override fun removeEldestEntry(eldest: Map.Entry<String, CacheEntry>): Boolean {
            val now = System.currentTimeMillis()
            return size > MAX_ENTRIES || (now - eldest.value.timestamp > CACHE_DURATION_MS)
        }
    }

    private fun isEntryValid(entry: CacheEntry): Boolean {
        val now = System.currentTimeMillis()
        return (now - entry.timestamp) <= 24 * 60 * 60 * 1000 // 24 hours validity
    }

    suspend fun getVideoInfo(url: String): Result<YouTubeVideo> = withContext(Dispatchers.IO) {
        try {
            // Check cache first
            cache[url]?.let { cacheEntry ->
                if (isEntryValid(cacheEntry)) {
                    return@withContext Result.success(cacheEntry.video)
                } else {
                    cache.remove(url) // Remove expired entry
                }
            }

            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .build()

            val response = client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Failed to fetch video info: ${response.code}")
                response.body?.string() ?: throw IOException("Empty response")
            }

            val document = Jsoup.parse(response)
            val videoId = YouTubeUtils.extractVideoId(url) ?: throw IOException("Invalid YouTube URL")

            // Try multiple selectors for title
            val title = listOf(
                { document.select("meta[property=og:title]").attr("content") },
                { document.select("meta[name=title]").attr("content") },
                { document.select("#video-title").text() },
                { document.select("h1.ytd-video-primary-info-renderer").text() },
                { document.select("title").text().replace(" - YouTube", "") },
                { "Untitled Video" }
            ).firstNotNullOf { selector -> selector().takeIf { it.isNotBlank() } }

            // Enhanced thumbnail extraction
            val thumbnail = listOf(
                { document.select("meta[property=og:image]").attr("content") },
                { document.select("link[rel=image_src]").attr("href") },
                { "https://img.youtube.com/vi/$videoId/maxresdefault.jpg" },
                { "https://img.youtube.com/vi/$videoId/hqdefault.jpg" },
                { YouTubeUtils.getFallbackThumbnailUrl(videoId) }
            ).firstNotNullOf { selector -> selector().takeIf { it.isNotBlank() } }

            // Enhanced description extraction
            val description = listOf(
                { document.select("meta[property=og:description]").attr("content") },
                { document.select("meta[name=description]").attr("content") },
                { document.select("#description-text").text() },
                { document.select("ytd-video-secondary-info-renderer #description").text() },
                { "No description available" }
            ).firstNotNullOf { selector -> selector().takeIf { it.isNotBlank() } }

            val video = YouTubeVideo(
                id = videoId,
                title = title,
                thumbnailUrl = thumbnail,
                description = description,
                videoUrl = url
            )

            // Cache the result with timestamp
            cache[url] = CacheEntry(video)
            Result.success(video)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Utility method to clear cache
    fun clearCache() {
        cache.clear()
    }

    // Utility method to get cache size
    fun getCacheSize(): Int = cache.size

    // Utility method to remove specific URL from cache
    fun removeFromCache(url: String) {
        cache.remove(url)
    }
}



