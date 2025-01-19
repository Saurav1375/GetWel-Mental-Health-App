package com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class VoiceGuidance(context: Context) {
    private var textToSpeech: TextToSpeech? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    private var currentInstructions: String = ""

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.apply {
                    language = Locale.US
                    setSpeechRate(0.7f) // Slower speech rate
                    setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            _isPlaying.value = true
                        }

                        override fun onDone(utteranceId: String?) {
                            _isPlaying.value = false
                        }

                        override fun onError(utteranceId: String?) {
                            _isPlaying.value = false
                        }
                    })
                }
            }
        }
    }

    fun speak(text: String) {
        currentInstructions = text
        textToSpeech?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "utteranceId"
        )
        _isPlaying.value = true
    }

    fun pause() {
        textToSpeech?.stop()
        _isPlaying.value = false
    }

    fun resume() {
        speak(currentInstructions)
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}