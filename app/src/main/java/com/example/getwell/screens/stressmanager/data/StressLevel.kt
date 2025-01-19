package com.example.getwell.screens.stressmanager.data

enum class StressLevel(val label: String) {
    LOW("Low"),
    MODERATE("Moderate"),
    HIGH("High"),
    EXTREME("Extreme");

    companion object {
        fun fromValue(value: Int): StressLevel = when {
            value <= 20 -> LOW
            value <= 60 -> MODERATE
            value <= 80 -> HIGH
            else -> EXTREME
        }
    }
}