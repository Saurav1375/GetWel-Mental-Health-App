package com.example.getwell.domain.provider

import com.example.getwell.data.Mood
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class MoodProvider(private val firestore: FirebaseFirestore) {


    suspend fun saveMood(userId: String, moodId: Int) {
        val mood = Mood(
            id = moodId,
            timestamp = Timestamp.now() // Current time
        )

        val moodData = hashMapOf(
            "id" to mood.id,
            "timestamp" to mood.timestamp
        )

        try {
            // Save the mood data to Firestore under the "moods" collection
            firestore
                .collection("users")
                .document(userId)
                .collection("moods")
                .add(moodData)
                .await() // Wait for the Firestore operation to complete
            println("Mood saved successfully")
        } catch (exception: Exception) {
            println("Error saving mood: ${exception.message}")
        }
    }


     suspend fun getMoodsForCurrentDay(userId: String): Pair<List<Int>, List<Timestamp>> {
        // Get the start and end of the current day
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = Timestamp(calendar.time)

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = Timestamp(calendar.time)

        // Retrieve all moods for the specified user within the current day
        val documents = firestore.collection("users")
            .document(userId)
            .collection("moods")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .orderBy("timestamp") // Order by timestamp in ascending order
            .get()
            .await() // Wait for the Firestore query to complete

        val moodList = mutableListOf<Int>()
        val timestampList = mutableListOf<Timestamp>()

        for (document in documents) {
            val id = document.getLong("id")?.toInt() // Cast to Int
            val timestamp = document.getTimestamp("timestamp")

            if (id != null && timestamp != null) {
                moodList.add(id)
                timestampList.add(timestamp)
            }
        }

        // Return the lists as a Pair
        return Pair(moodList, timestampList)
    }
}
