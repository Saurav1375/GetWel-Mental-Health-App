package com.example.getwell.screens.relaxScreen.physicaltherapy.pranayama

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

enum class PranayamaPhase {
    IDLE,
    RIGHT_NOSTRIL_INHALE,
    LEFT_NOSTRIL_EXHALE,
    LEFT_NOSTRIL_INHALE,
    RIGHT_NOSTRIL_EXHALE
}

data class PranayamaState(
    val isActive: Boolean = false,
    val currentRound: Int = 1,
    val totalRounds: Int = 5,
    val currentPhase: PranayamaPhase = PranayamaPhase.IDLE,
    val currentInstruction: String = "Hold your right hand up. Curl your index and middle fingers towards your palm.\nPress start to begin practice",
    val isBreathingIn: Boolean = false,
    val phaseDuration: Long = 7000L // 5 seconds per phase
)

class PranayamaViewModel : ViewModel() {
    private val _state = MutableStateFlow(PranayamaState())
    val state: StateFlow<PranayamaState> = _state.asStateFlow()

    private var practiceJob: Job? = null
    private var textToSpeech: TextToSpeech? = null

    fun initTextToSpeech(context: Context) {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
            }
        }
    }

    fun startPractice() {
        // Cancel any existing job before starting a new one
        practiceJob?.cancel()

        if (_state.value.isActive) return

        _state.update { it.copy(isActive = true) }
        practiceJob = viewModelScope.launch {
            while (_state.value.isActive &&
                _state.value.currentRound <= _state.value.totalRounds) {
                performPranayamaRound()
            }
            if (_state.value.currentRound > _state.value.totalRounds) {
                resetPractice()
            }
        }
    }

    fun pausePractice() {
        practiceJob?.cancel()
        _state.update { it.copy(isActive = false) }
    }

    fun updateTotalRounds(rounds: Int) {
        if (rounds > 0) {
            _state.update { it.copy(totalRounds = rounds) }
        }
    }

    fun resetPractice() {
        practiceJob?.cancel()
        _state.update {
            PranayamaState(
                totalRounds = it.totalRounds,
                isActive = false
            )
        }
    }

    private suspend fun performPranayamaRound() {
        // Right nostril inhale

        updatePhase(
            PranayamaPhase.RIGHT_NOSTRIL_INHALE,
            "Close left nostril with ring finger. Inhale through right nostril.",
            true
        )
        delay(_state.value.phaseDuration)

        // Left nostril exhale
        updatePhase(
            PranayamaPhase.LEFT_NOSTRIL_EXHALE,
            "Close right nostril with thumb. Exhale through left nostril.",
            false
        )
        delay(_state.value.phaseDuration)

        // Left nostril inhale
        updatePhase(
            PranayamaPhase.LEFT_NOSTRIL_INHALE,
            "Keep right nostril closed. Inhale through left nostril.",
            true
        )
        delay(_state.value.phaseDuration)

        // Right nostril exhale
        updatePhase(
            PranayamaPhase.RIGHT_NOSTRIL_EXHALE,
            "Close left nostril. Exhale through right nostril.",
            false
        )
        delay(_state.value.phaseDuration)

        _state.update {
            it.copy(currentRound = it.currentRound + 1)
        }
    }

    private fun updatePhase(
        phase: PranayamaPhase,
        instruction: String,
        isBreathingIn: Boolean
    ) {
        _state.update { 
            it.copy(
                currentPhase = phase,
                currentInstruction = instruction,
                isBreathingIn = isBreathingIn
            )
        }
        textToSpeech?.speak(instruction, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeech?.shutdown()
    }
}