package com.example.getwell.screens.relaxScreen.gamesection.dailyref

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.authSystem.UserData
import com.example.getwell.di.Injection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar

class ReflectionRepository(
    private val firestore: FirebaseFirestore,
    private val userData: UserData?
) {
    private fun getCurrentUserId(): String {
        return userData?.emailId
            ?: "jkdbigd"
    }

    private fun getUserReflectionsRef() =
        firestore.collection("users")
            .document(getCurrentUserId())
            .collection("reflections")

    private fun getUserStreakRef() =
        firestore.collection("users")
            .document(getCurrentUserId())

    // Submit Reflection
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun submitReflection(answer: String, question : String): Reflection {
        val reflection = Reflection(
            question = question,
            answer = answer
        )
        updateUserStreak()
        // Add reflection to Firestore
        getUserReflectionsRef().add(reflection)

        // Update streak

        return reflection
    }

    // Update Streak Logic
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateUserStreak() {
        val userStreakRef = getUserStreakRef()
        val today = LocalDate.now()

        // Fetch current streak
        val currentStreak = userStreakRef
            .get()
            .await()
            .toObject(UserStreak::class.java)
            ?: UserStreak()

        // Calculate days since last reflection
        val lastReflectionDate = currentStreak.lastReflectionDate.takeIf { it.isNotEmpty() }
            ?.let { LocalDate.parse(it) }

        val updatedStreak = when (
            lastReflectionDate) {
            null -> { // No previous reflection
                currentStreak.copy(
                    currentStreak = 1,
                    longestStreak = 1,
                    lastReflectionDate = today.toString()
                )
            }
            // Reflection on consecutive day
            today.minusDays(1) -> {
                currentStreak.copy(
                    currentStreak = currentStreak.currentStreak + 1,
                    longestStreak = maxOf(
                        currentStreak.currentStreak + 1,
                        currentStreak.longestStreak
                    ),
                    lastReflectionDate = today.toString()
                )
            }
            // Reflection on same day (no change)
            today -> {
                currentStreak
            }
            // Missed a day - reset streak
            else -> {
                currentStreak.copy(
                    currentStreak = 1,
                    longestStreak = currentStreak.longestStreak,
                    lastReflectionDate = today.toString()
                )
            }
        }

        // Save updated streak
        userStreakRef.set(updatedStreak)
    }

    // Check and Reset Streak
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun checkAndResetStreak() {
        val userStreakRef = getUserStreakRef()
        val today = LocalDate.now()

        // Fetch current streak
        val currentStreak = userStreakRef
            .get()
            .await()
            .toObject(UserStreak::class.java)
            ?: return

        // If no last reflection date, do nothing
        val lastReflectionDate = currentStreak.lastReflectionDate.takeIf { it.isNotEmpty() }
            ?.let { LocalDate.parse(it) }
            ?: return

        // Check if more than one day has passed since last reflection
        val daysSinceLastReflection = ChronoUnit.DAYS.between(lastReflectionDate, today)

        if (daysSinceLastReflection > 1) {
            // Reset streak
            val resetStreak = UserStreak(
                currentStreak = 0,
                longestStreak = currentStreak.longestStreak,
                lastReflectionDate  = currentStreak.lastReflectionDate
            )

            userStreakRef.set(resetStreak)
        }
    }
    fun getTodayReflection(): Flow<Reflection?> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val endOfDay = calendar.timeInMillis

        return callbackFlow {
            val reflectionRef = getUserReflectionsRef()
                .whereGreaterThanOrEqualTo("timestamp", startOfDay)
                .whereLessThan("timestamp", endOfDay)
                .limit(1) // assuming only one reflection per day

            val listenerRegistration = reflectionRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // close the flow if there's an error
                    return@addSnapshotListener
                }

                val reflection = snapshot?.documents?.firstOrNull()?.toObject(Reflection::class.java)
                trySend(reflection) // emit the reflection if it exists
            }

            awaitClose { listenerRegistration.remove() }
        }
    }


    // Get Past Reflections
    suspend fun getPastReflections(): List<Reflection> {
        return getUserReflectionsRef()
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(30)
            .get()
            .await()
            .toObjects(Reflection::class.java)
    }

    // Get Current Streak
    suspend fun getCurrentStreak(): UserStreak {
        val userStreakRef = getUserStreakRef()
        return userStreakRef
            .get()
            .await()
            .toObject(UserStreak::class.java)
            ?: UserStreak()
    }
}
