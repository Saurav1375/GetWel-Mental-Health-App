package com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.ui

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.data.GeminiAiService
import kotlinx.coroutines.launch
import java.util.Locale

class GuidedImageryViewModel(
    private val geminiAiService: GeminiAiService
) : ViewModel() {
    var uiState by mutableStateOf(GuidedImageryUiState())
        private set

    private var textToSpeech: TextToSpeech? = null

    fun initTextToSpeech(tts: TextToSpeech) {
        textToSpeech = tts
        textToSpeech?.language = Locale.ENGLISH
        textToSpeech?.setSpeechRate(0.7f)
    }

    fun generateImagery(prompt: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            val response = geminiAiService.generateImagery(prompt)
            uiState = uiState.copy(
                generatedText = response,
                isLoading = false
            )
            speakText(response)
        }
    }

    private fun speakText(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeech?.shutdown()
    }
}

data class GuidedImageryUiState(
    val generatedText: String = "",
    val isLoading: Boolean = false
)