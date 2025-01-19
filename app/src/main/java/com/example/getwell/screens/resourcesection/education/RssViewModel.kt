package com.example.getwell.screens.resourcesection.education

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
sealed class FeedState {
    object Loading : FeedState()
    data class Success(
        val items: Map<String, List<FeedItem>>,
        val errors: Map<String, String>
    ) : FeedState()
    data class Error(val message: String) : FeedState()
}

// FeedViewModel.kt
class FeedViewModel : ViewModel() {
    private val repository = FeedRepository()
    private val _feedState = MutableStateFlow<FeedState>(FeedState.Success(emptyMap(), emptyMap()))
    val feedState: StateFlow<FeedState> = _feedState.asStateFlow()

    private var allUrls: List<String> = emptyList()

    fun fetchFeeds(urls: List<String>) {
        allUrls = urls
        viewModelScope.launch {
            _feedState.value = FeedState.Loading

            try {
                val results = repository.getMultipleFeedItems(urls)

                val successfulFeeds = mutableMapOf<String, List<FeedItem>>()
                val errors = mutableMapOf<String, String>()

                results.forEach { (url, result) ->
                    result.fold(
                        onSuccess = { items ->
                            successfulFeeds[url] = items
                        },
                        onFailure = { error ->
                            errors[url] = error.message ?: "Unknown error occurred"
                        }
                    )
                }

                _feedState.value = FeedState.Success(successfulFeeds, errors)
            } catch (e: Exception) {
                _feedState.value = FeedState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun retryFailedFeeds() {
        val currentState = _feedState.value as? FeedState.Success ?: return
        val failedUrls = currentState.errors.keys.toList()

        viewModelScope.launch {
            try {
                val results = repository.getMultipleFeedItems(failedUrls)

                val newSuccessfulFeeds = currentState.items.toMutableMap()
                val newErrors = currentState.errors.toMutableMap()

                results.forEach { (url, result) ->
                    result.fold(
                        onSuccess = { items ->
                            newSuccessfulFeeds[url] = items
                            newErrors.remove(url)
                        },
                        onFailure = { error ->
                            newErrors[url] = error.message ?: "Unknown error occurred"
                        }
                    )
                }

                _feedState.value = FeedState.Success(newSuccessfulFeeds, newErrors)
            } catch (e: Exception) {
                // Keep existing successful feeds even if retry fails
                _feedState.value = currentState
            }
        }
    }

    fun getCurrentUrls(): List<String> = allUrls
}