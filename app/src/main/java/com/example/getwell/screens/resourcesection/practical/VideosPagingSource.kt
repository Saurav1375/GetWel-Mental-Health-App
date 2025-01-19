package com.example.getwell.screens.resourcesection.practical

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ConcurrentHashMap

class VideosPagingSource(
    private val videoLinks: List<String>,
    private val scope: CoroutineScope,
    private val youTubeService: YouTubeService,
    private val videoCache: ConcurrentHashMap<String, YouTubeVideo>,
    private val shuffledIndices: List<Int>,
    private val onError: (String) -> Unit
) : PagingSource<Int, YouTubeVideo>() {

    override fun getRefreshKey(state: PagingState<Int, YouTubeVideo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, YouTubeVideo> {
        return try {
            if (videoLinks.isEmpty()) {
                Log.d("PagingSource", "No videos available")
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val page = params.key ?: 0
            val pageSize = params.loadSize

            val startIndex = (page * pageSize).coerceIn(0, shuffledIndices.size)
            val endIndex = ((page * pageSize) + pageSize).coerceIn(0, shuffledIndices.size)

            if (startIndex >= shuffledIndices.size) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (page > 0) page - 1 else null,
                    nextKey = null
                )
            }

            Log.d("PagingSource", "Loading page $page, indices $startIndex to $endIndex")

            val videos = shuffledIndices.subList(startIndex, endIndex)
                .mapNotNull { index ->
                    try {
                        if (index < videoLinks.size) {
                            val link = videoLinks[index]
                            // First try to get from cache
                            videoCache[link] ?: youTubeService.getVideoInfo(link).getOrNull()?.also { video ->
                                // Store in cache if not present
                                videoCache[link] = video
                            }
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        Log.e("PagingSource", "Error loading video at index $index", e)
                        null
                    }
                }

            LoadResult.Page(
                data = videos,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (endIndex < shuffledIndices.size) page + 1 else null
            )
        } catch (e: Exception) {
            Log.e("PagingSource", "Error loading page", e)
            onError("Error loading videos: ${e.message}")
            LoadResult.Error(e)
        }
    }
}