package com.example.getwell.domain.provider

import com.example.getwell.data.Mood
import com.example.getwell.screens.stressQuiz.QuizItem
import com.example.getwell.screens.stressanalysis.StressAIObj
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar

class StressRepository(
    private val firestore: FirebaseFirestore
) {
    suspend fun getTodayMoods(userEmail: String): List<Mood> = withContext(Dispatchers.IO) {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        try {
            firestore.collection("users")
                .document(userEmail)
                .collection("moods")
                .whereGreaterThanOrEqualTo("timestamp", Timestamp(startOfDay.time))
                .get()
                .await()
                .documents
                .mapNotNull { doc ->
                    doc.toObject(Mood::class.java)
                }
        } catch (e: Exception) {
            println("Error fetching moods: ${e.message}")
            emptyList()
        }
    }


    suspend fun getTodayAIData(userEmail: String, id : String): List<StressAIObj> = withContext(Dispatchers.IO) {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        try {
            firestore.collection("users")
                .document(userEmail)
                .collection("StressQuiz")
                .document(id)
                .collection("response")
                .whereGreaterThanOrEqualTo("timestamp", Timestamp(startOfDay.time))
                .get()
                .await()
                .documents
                .mapNotNull { doc ->
                    doc.toObject(StressAIObj::class.java)
                }
        } catch (e: Exception) {
            println("Error fetching AI Data: ${e.message}")
            emptyList()
        }
    }

    suspend fun getLatestQuizData(userEmail: String, quizType: String): List<QuizItem>? = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = firestore.collection("users")
                .document(userEmail)
                .collection("StressQuiz")
                .document(quizType)
                .collection("response")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            if (querySnapshot.documents.isNotEmpty()) {
                val document = querySnapshot.documents[0]
                @Suppress("UNCHECKED_CAST")
                val quizList = document.get("quizList") as? List<Map<String, Any>>

                return@withContext quizList?.map { map ->
                    QuizItem(
                        id = (map["id"] as Long).toInt(),
                        desc = map["description"] as String,
                        selectedOption = (map["selectedOption"] as Long).toInt()
                    )
                }
            }
            null
        } catch (e: Exception) {
            println("Error fetching quiz data: ${e.message}")
            null
        }
    }
}