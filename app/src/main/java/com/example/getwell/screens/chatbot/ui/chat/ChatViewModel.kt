package com.example.getwell.screens.chatbot.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.chatbot.data.model.Message
import com.example.getwell.screens.chatbot.data.repository.ChatRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser

    private val systemPrompt = """
        You are an empathetic mental health assistant. Your role is to:
        1. Provide supportive, non-judgmental responses
        2. Offer coping strategies for stress and anxiety
        3. Share information about mental health and reducing stigma
        4. Encourage professional help when appropriate
        5. Use a warm, understanding tone
        6. Never provide medical diagnoses or treatment advice
        7. Maintain confidentiality and privacy
        8. Handle crisis situations by directing to emergency services
        
        Important: Always prioritize user safety and well-being.
    """.trimIndent()

    init {
        observeMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            // Replace with actual user ID from authentication
            val userId = currentUser?.email!!
            chatRepository.getChatMessages(userId).collect { messageList ->
                _messages.value = messageList
            }
        }
    }

    fun sendMessage(content: String, userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Save user message
            val userMessage = Message(
                content = content,
                timestamp = Date(),
                isUser = true,
                userId = userId
            )
            chatRepository.sendMessage(userMessage)

            try {
                // Get AI response
                val prompt = "$systemPrompt\nUser: $content\nAssistant:"
                val response = generativeModel.generateContent(prompt).text ?: "I apologize, but I'm unable to respond at the moment."

                // Save AI response
                val aiMessage = Message(
                    content = response,
                    timestamp = Date(),
                    isUser = false,
                    userId = userId
                )
                chatRepository.sendMessage(aiMessage)
            } catch (e: Exception) {
                // Handle error
                val errorMessage = Message(
                    content = "I apologize, but I'm having trouble connecting. Please try again.",
                    timestamp = Date(),
                    isUser = false,
                    userId = userId
                )
                chatRepository.sendMessage(errorMessage)
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}