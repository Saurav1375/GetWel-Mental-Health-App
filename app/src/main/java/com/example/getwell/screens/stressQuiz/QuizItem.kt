package com.example.getwell.screens.stressQuiz



data class QuizItem(
    val id: Int = 0,  // Default value
    val desc: String = "",  // Default value
    var selectedOption: Int = -1,  // Already has default value
    val answers: List<String> = emptyList(),
)













