package com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.data.LocationRepository
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.data.WellnessCenter
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.data.WellnessCenterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WellnessCentersViewModel(application: Application) : AndroidViewModel(application) {
    private val locationRepository = LocationRepository(application)
    private val wellnessCenterRepository = WellnessCenterRepository()

    private val _uiState = MutableStateFlow<WellnessCentersUiState>(WellnessCentersUiState.Loading)
    val uiState: StateFlow<WellnessCentersUiState> = _uiState.asStateFlow()

    init {
        loadWellnessCenters()
    }

    fun loadWellnessCenters() {
        viewModelScope.launch {
            _uiState.value = WellnessCentersUiState.Loading
            try {
                val location = locationRepository.getCurrentLocation()
                if (location != null) {
                    println("Lat: ${location.latitude} Long: ${location.longitude}")
                    val centers = wellnessCenterRepository.getNearbyWellnessCenters(
                        location.latitude,
                        location.longitude
                    )
                    println("Wellness: $centers")
                    _uiState.value = WellnessCentersUiState.Success(centers)
                } else {
                    _uiState.value = WellnessCentersUiState.Error("Unable to get location\nPlease turn on location services")
                }
            } catch (e: Exception) {
                _uiState.value = WellnessCentersUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

sealed class WellnessCentersUiState {
    object Loading : WellnessCentersUiState()
    data class Success(val centers: List<WellnessCenter>) : WellnessCentersUiState()
    data class Error(val message: String) : WellnessCentersUiState()
}