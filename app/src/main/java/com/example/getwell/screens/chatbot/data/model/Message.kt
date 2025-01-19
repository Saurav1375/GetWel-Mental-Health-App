package com.example.getwell.screens.chatbot.data.model

import java.util.Date

data class Message(
    val id: String = "",
    val content: String = "",
    val timestamp: Date = Date(),
    val isUser: Boolean = false,
    val userId: String = ""
)