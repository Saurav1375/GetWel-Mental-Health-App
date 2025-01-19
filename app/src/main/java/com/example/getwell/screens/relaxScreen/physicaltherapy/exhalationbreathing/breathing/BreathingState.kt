package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing

enum class BreathingState {
    IDLE,
    INHALE,
    HOLD,
    EXHALE
}

data class BreathingScreenState(
    val isExerciseStarted: Boolean = false,
    val currentStep: Int = 0,
    val breathingState: BreathingState = BreathingState.IDLE
)