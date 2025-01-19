package com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.repository

import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.model.FeelingEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FeelingsRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val feelingsCollection = firestore.collection("feelings")

    suspend fun saveFeelingEntry(entry: FeelingEntry): Result<Unit> = runCatching {
        feelingsCollection.add(entry).await()
    }

    suspend fun getFeelingEntries(userId: String): Result<List<FeelingEntry>> = runCatching {
        feelingsCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp")
            .get()
            .await()
            .toObjects(FeelingEntry::class.java)
    }
}