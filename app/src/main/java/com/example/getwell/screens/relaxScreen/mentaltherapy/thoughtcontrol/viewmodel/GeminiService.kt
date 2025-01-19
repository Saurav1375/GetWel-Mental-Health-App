package com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.viewmodel

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class GeminiService(apiKey: String) {
    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    suspend fun generateThought(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            // Enhance prompt to guide the model for specific responses
            val enhancedPrompt = """
                I want to reframe a negative thought into a positive one. 
                The thought is: "$prompt". 
                Please provide a positive and empowering alternative to this thought.
            """.trimIndent()

            // Call the Gemini model to generate content
            val response = model.generateContent(enhancedPrompt)

            // Log response for debugging
            Log.d("GeminiService", "Generated Response: ${response.text}")

            // Handle potential null response gracefully
            response.text ?: "Unable to generate positive thought right now."
        } catch (e: Exception) {
            // Log the error
            Log.e("GeminiService", "Error generating thought", e)

            // Return a default error message
            "Sorry, I couldn't generate the positive thought. Please try again."
        }
    }
}
