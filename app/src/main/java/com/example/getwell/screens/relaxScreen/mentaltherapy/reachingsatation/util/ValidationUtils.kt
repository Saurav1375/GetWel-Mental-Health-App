package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.util

object ValidationUtils {
    fun isValidThought(thought: String): Boolean {
        return thought.trim().length >= 3
    }

    fun isValidConsequence(consequence: String): Boolean {
        return consequence.trim().length >= 3
    }

    fun validateMoodValue(mood: Int): Int {
        return mood.coerceIn(0, 10)
    }
}