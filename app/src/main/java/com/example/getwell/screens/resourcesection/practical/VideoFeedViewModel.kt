package com.example.getwell.screens.resourcesection.practical

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class VideoFeedViewModel : ViewModel() {
    private val youTubeService = YouTubeService()
    private val videoLinks = CopyOnWriteArrayList<String>()
    private val videoCache = ConcurrentHashMap<String, YouTubeVideo>()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _shuffledIndices = MutableStateFlow(listOf<Int>())
    private val _videos = MutableStateFlow<PagingData<YouTubeVideo>>(PagingData.empty())
    val videos: Flow<PagingData<YouTubeVideo>> = _videos.asStateFlow()

    init {
        Log.d("VideoFeedViewModel", "Initializing ViewModel")
        setupPaging()
    }

    private fun setupPaging() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = false,
                    prefetchDistance = 2,
                    initialLoadSize = 10
                )
            ) {
                VideosPagingSource(
                    videoLinks = videoLinks.toList(),
                    scope = viewModelScope,
                    youTubeService = youTubeService,
                    videoCache = videoCache,
                    shuffledIndices = _shuffledIndices.value,
                    onError = { error -> _error.value = error }
                )
            }.flow.cachedIn(viewModelScope).collect { pagingData ->
                _videos.value = pagingData
            }
        }
    }

    private fun reshuffleVideos() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (videoLinks.isEmpty()) {
                    Log.d("VideoFeedViewModel", "No videos to shuffle")
                    return@launch
                }

                _shuffledIndices.value = List(videoLinks.size) { it }.shuffled()
                Log.d("VideoFeedViewModel", "Reshuffled ${_shuffledIndices.value.size} videos")
                _error.value = null

                setupPaging()
            } catch (e: Exception) {
                Log.e("VideoFeedViewModel", "Error shuffling videos", e)
                _error.value = "Error shuffling videos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addVideoLink(link: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                if (link.isBlank()) {
                    Log.w("VideoFeedViewModel", "Attempted to add empty link")
                    return@launch
                }

                if (!videoLinks.contains(link)) {
                    videoLinks.add(link)
                    // Prefetch video info
                    try {
                        youTubeService.getVideoInfo(link).getOrNull()?.let { video ->
                            videoCache[link] = video
                        }
                    } catch (e: Exception) {
                        Log.e("VideoFeedViewModel", "Error prefetching video info", e)
                    }

                    Log.d("VideoFeedViewModel", "Added video link. Total: ${videoLinks.size}")
                    reshuffleVideos()
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("VideoFeedViewModel", "Error adding video link", e)
                _error.value = "Error adding video: ${e.message}"
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                videoCache.clear()
                reshuffleVideos()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    override fun onCleared() {
        super.onCleared()
        videoCache.clear()
    }
}