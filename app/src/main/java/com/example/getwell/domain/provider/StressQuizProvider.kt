package com.example.getwell.domain.provider

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.getwell.screens.stressQuiz.QuizItem
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

// Assuming you have initialized Firestore
class StressQuizProvider(
    private val firestore: FirebaseFirestore
    ){
    // Function to add quiz data to Firestore
    fun addQuizDataToFirestore(
        id : String,
       list: List<QuizItem>,
        userEmail: String) {
        // Convert the list of QuizItem to a list of maps
        val quizList = list.map { quizItem ->
            mapOf(
                "id" to quizItem.id,
                "description" to quizItem.desc,
                "selectedOption" to quizItem.selectedOption
            )
        }
        // Create a map with the quiz data and a timestamp
        val quizData = hashMapOf(
            "quizList" to quizList,
            "timestamp" to Timestamp.now()
        )

        // Specify the path: /users/{userEmail}/StressQuiz/DASS
        firestore.collection("users")
            .document(userEmail)
            .collection("StressQuiz")
            .document(id)
            .collection("response")
            .add(quizData)
            .addOnSuccessListener {
                println("Quiz data successfully added to Firestore")
            }
            .addOnFailureListener { e ->
                println("Error adding quiz data: $e")
//                Toast.makeText(context,"Something Went Wrong", Toast.LENGTH_LONG).show()
            }
    }

    fun getLatestResponseTime(
        id: String,
        userEmail: String,
        onResult: (Long) -> Unit
    ) {
        // Query the Firestore for the latest response
        firestore.collection("users")
            .document(userEmail)
            .collection("StressQuiz")
            .document(id)
            .collection("response")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1) // Limit to get only the latest response
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // No responses found, return 0L
                    onResult(0L)
                } else {
                    // Get the latest response time
                    val latestResponse = documents.documents[0]
                    val responseTimestamp = latestResponse.getTimestamp("timestamp")
                    println(responseTimestamp)

                    // Convert Timestamp to milliseconds, if it's not null
                    val responseTimeInMillis = responseTimestamp?.toDate()?.time ?: 0L
                    println(responseTimeInMillis)
                    onResult(responseTimeInMillis)
                }
            }
            .addOnFailureListener {
                // Handle any errors
                println("Error fetching latest response: ${it.message}")
                onResult(0L) // Return 0L in case of failure
            }
    }

    suspend fun getLatestQuizResponse(userEmail: String, id: String): List<QuizItem>? {
        // Specify the path: /users/{userEmail}/StressQuiz/DASS/response
        return try {
            val documents = firestore.collection("users")
                .document(userEmail)
                .collection("StressQuiz")
                .document(id) // Change this to the correct document ID if necessary
                .collection("response")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING) // Order by timestamp
                .limit(1) // Get only the latest response
                .get()
                .await() // Wait for the Firestore operation to complete

            if (documents.isEmpty) {
                null // No quiz responses found
            } else {
                val latestResponse = documents.documents.first()
                val quizList = latestResponse.get("quizList") as? List<*>

                // Safely convert the list of maps back to List<QuizItem>
                quizList?.mapNotNull { item ->
                    when (item) {
                        is Map<*, *> -> {
                            // Ensure we have the expected types in the map
                            val id = (item["id"] as? Long)?.toInt()
                            val desc = item["description"] as? String
                            val selectedOption = (item["selectedOption"] as? Long)?.toInt()

                            if (id != null && desc != null && selectedOption != null) {
                                QuizItem(id, desc, selectedOption)
                            } else {
                                null // Skip invalid items
                            }
                        }
                        else -> null // Skip non-map items
                    }
                } ?: emptyList()
            }
        } catch (e: Exception) {
            println("Error getting quiz response: $e")
            null // Return null on failure
        }
    }




}
