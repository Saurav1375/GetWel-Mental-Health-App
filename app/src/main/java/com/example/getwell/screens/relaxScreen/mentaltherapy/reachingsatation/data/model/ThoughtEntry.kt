package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.model

import com.google.firebase.Timestamp

data class ThoughtEntry(
    val id: String = "",
    val userId: String = "",
    val initialThought: String = "",
    val consequences: List<String> = emptyList(),
    val rationalAnalysis: Map<String, Boolean> = emptyMap(), // consequence to isRational mapping
    val copingStrategies: List<String> = emptyList(),
    val aiSuggestion: String = "",
    val moodBefore: Int = 0,
    val moodAfter: Int = 0,
    val timestamp: Timestamp = Timestamp.now()
)