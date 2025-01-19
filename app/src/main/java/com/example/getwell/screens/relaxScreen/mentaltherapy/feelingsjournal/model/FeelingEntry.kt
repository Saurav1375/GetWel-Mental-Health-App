package com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.model

import com.google.firebase.Timestamp

data class FeelingEntry(
    val id: String = "",
    val content: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val userId: String = ""
)