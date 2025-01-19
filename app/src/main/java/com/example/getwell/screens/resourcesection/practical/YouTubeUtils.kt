package com.example.getwell.screens.resourcesection.practical

import java.util.regex.Pattern

object YouTubeUtils {
    private val VIDEO_ID_REGEX = Pattern.compile(
        "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
    )

    fun extractVideoId(youtubeUrl: String): String? {
        val matcher = VIDEO_ID_REGEX.matcher(youtubeUrl)
        return if (matcher.find()) {
            matcher.group()
        } else null
    }

    fun getFallbackThumbnailUrl(videoId: String): String {
        return "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"
    }
}