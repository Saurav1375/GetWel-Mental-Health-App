package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.util

object AIPromptGenerator {
    fun generateCopingStrategiesPrompt(
        initialThought: String,
        consequences: List<String>
    ): String {
        return """
            Based on the following thought and consequences, suggest some coping strategies:
            
            Initial Thought: $initialThought
            
            Consequences:
            ${consequences.joinToString("\n") { "- $it" }}
            
            Please provide 2 practical, evidence-based coping strategies that can help manage these thoughts and consequences.
            For each strategy, include(explain in short):
            1. A clear explanation of the strategy
            2. How to implement it
            3. Why it's effective for this situation
        """.trimIndent()
    }
}