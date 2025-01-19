package com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.data.GeminiAiService
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.ui.GuidedImageryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThoughtViewModel(private val geminiService: GeminiService) : ViewModel() {
    var uiState by mutableStateOf(ThoughtUiState())
        private set

    fun generateThought(prompt: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            val response = geminiService.generateThought(prompt)
            uiState = uiState.copy(
                generatedText = response,
                isLoading = false
            )
        }
    }

}


data class ThoughtUiState(
    val generatedText: String = "",
    val isLoading: Boolean = false
)