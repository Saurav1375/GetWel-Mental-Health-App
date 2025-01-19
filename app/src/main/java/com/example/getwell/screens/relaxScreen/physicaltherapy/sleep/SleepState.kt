package com.example.getwell.screens.relaxScreen.physicaltherapy.sleep

enum class SleepState {
    IDLE,
    GET_UP,
    ACTIVITY,
    RELAX,
    DROWSY
}

data class SleepScreenState(
    val isExerciseStarted: Boolean = false,
    val currentStep: Int = 0,
    val sleepState: SleepState = SleepState.IDLE
)