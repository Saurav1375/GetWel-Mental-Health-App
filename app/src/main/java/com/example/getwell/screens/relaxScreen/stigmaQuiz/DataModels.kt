package com.example.getwell.screens.relaxScreen.stigmaQuiz
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random


data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

data class Quiz(
    val id: Int,
    val questions: List<Question>
)


fun generateQuizzes(
    mainQuestionBank: List<Question>,
    numQuizzes: Int = 6,
    questionsPerQuiz: Int = 15
): List<Quiz> {
    // Shuffle the main question bank to ensure randomness
    val shuffledQuestions = mainQuestionBank.shuffled(Random(System.currentTimeMillis()))

    // Initialize a list to hold the quizzes
    val quizzes = mutableListOf<Quiz>()

    for (i in 0 until numQuizzes) {
        // Get a sublist of questions for the current quiz
        val quizQuestions = shuffledQuestions.drop(i * questionsPerQuiz).take(questionsPerQuiz)

        // Create a new Quiz instance and add it to the quizzes list
        quizzes.add(Quiz(id = i + 1, questions = quizQuestions))
    }

    return quizzes
}