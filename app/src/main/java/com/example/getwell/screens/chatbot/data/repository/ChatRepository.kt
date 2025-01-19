package com.example.getwell.screens.chatbot.data.repository

import com.example.getwell.screens.chatbot.data.model.Message
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getChatMessages(userId: String): Flow<List<Message>> = callbackFlow {
        val listenerRegistration = firestore.collection("chats")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .limit(500)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.data?.let { data ->
                        Message(
                            content = data["content"] as? String ?: "",
                            isUser = data["isUser"] as? Boolean ?: false,
                            timestamp = (data["timestamp"] as? com.google.firebase.Timestamp)?.toDate() ?: Date(),
                            userId = data["userId"] as? String ?: ""
                        )
                    }
                } ?: emptyList()


                trySend(messages)
            }
            
        awaitClose { listenerRegistration.remove() }
    }

    suspend fun sendMessage(message: Message) {
        val messageData = mapOf(
            "content" to message.content,
            "isUser" to message.isUser,
            "timestamp" to message.timestamp,
            "userId" to message.userId
        )

        firestore.collection("chats")
            .add(messageData)
            .await()
    }
}