package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.repository

import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.model.ThoughtEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThoughtRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun saveThoughtEntry(thoughtEntry: ThoughtEntry): Result<Unit> = runCatching {
        firestore.collection("thoughts")
            .document(thoughtEntry.id)
            .set(thoughtEntry)
            .await()
    }

    suspend fun getThoughtEntries(userId: String): Result<List<ThoughtEntry>> = runCatching {
        firestore.collection("thoughts")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp")
            .get()
            .await()
            .toObjects(ThoughtEntry::class.java)
    }
}