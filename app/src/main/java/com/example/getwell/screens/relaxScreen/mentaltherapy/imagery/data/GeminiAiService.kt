package com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.data

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiAiService(private val apiKey: String) {
    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    suspend fun generateImagery(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val enhancedPrompt = "Create a short, relaxing guided imagery description for: $prompt. " +
                "Include sensory details like sights, sounds, smells, and feelings. Make it soothing and peaceful."
            
            val response = model.generateContent(enhancedPrompt)
            response.text ?: "Unable to generate imagery description."
        } catch (e: Exception) {
            "Sorry, I couldn't generate the imagery description. Please try again."
        }
    }
}