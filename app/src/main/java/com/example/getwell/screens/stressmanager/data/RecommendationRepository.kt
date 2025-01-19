package com.example.getwell.screens.stressmanager.data

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecommendationRepository(private val apiKey: String) {
    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    suspend fun generateDailyPlan(stressLevel: StressLevel): String = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(stressLevel)
            val response = model.generateContent(prompt)
            println(response.text)
            response.text ?: "Unable to generate recommendations at this time."
        }catch (e: Exception) {
            println("Error: ${e.message}")
            "Sorry, I couldn't generate the recommendations. Please try again."
        }
    }

    private fun buildPrompt(stressLevel: StressLevel): String {
        return """
            Generate a detailed daily plan to help reduce ${stressLevel.label.lowercase()} stress levels.
            The plan should include:
            1. Morning routine
            2. Recommended activities throughout the day
            3. Evening wind-down routine
            4. Specific stress-reduction techniques
            5. Self-care activities
            
            Make the recommendations specific to ${stressLevel.label.lowercase()} stress level, where:
            ${when (stressLevel) {
                StressLevel.LOW -> "Focus on maintenance and prevention with light activities."
                StressLevel.MODERATE -> "Include calming activities and stress management techniques."
                StressLevel.HIGH -> "Prioritize immediate stress relief and recovery activities."
                StressLevel.EXTREME -> "Focus on intensive stress relief and professional support recommendations."
            }}
            
            Format the response in a clear,concise easy-to-follow schedule and not too long and dont use hastags and stars.
        """.trimIndent()
    }
}