package com.example.getwell.screens.relaxScreen.gamesection.dailyref

import java.time.LocalDate

data class Reflection(
    val id: String = "",
    val question: String = "",
    val answer: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val date: String = LocalDate.now().toString()
)

data class UserStreak(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastReflectionDate: String = ""
)