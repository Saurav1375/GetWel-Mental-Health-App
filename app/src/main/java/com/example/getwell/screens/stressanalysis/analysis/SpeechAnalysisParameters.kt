package com.example.getwell.screens.stressanalysis.analysis

data class VoiceStressIndicators(
    val stressKeywordCount: Int = 0,
    val positiveKeywordCount: Int = 0,
    val wordRepetitions: Int = 0,
    val pauseFrequency: Int = 0,
    val speakingRate: Float = 0f
)

object SpeechAnalysisParameters {
    val HIGH_STRESS_KEYWORDS = listOf(
        "stressed", "anxious", "worried", "tired", "exhausted",
        "overwhelmed", "pressure", "difficult", "hard", "struggle",
        "panic", "fear", "nervous", "tension", "burden",
        "depressed", "frustrated", "angry", "upset", "confused",
        "distressed", "helpless", "hopeless", "burned out", "freaked out",
        "paranoid", "trapped", "numb", "isolated", "tense",
        "nervous breakdown", "overloaded", "chaotic", "losing control",
        "fatigued", "restless", "desperate", "vulnerable", "crying",
        "overthinking", "stifled", "shaken", "panicking", "helplessness"
    )


    val MODERATE_STRESS_KEYWORDS = listOf(
        "busy", "deadline", "workload", "responsibility", "challenge",
        "concerned", "uncertain", "unsure", "restless", "uneasy",
        "pressure", "strained", "juggling", "overworked", "demanding",
        "irritated", "frustrating", "on edge", "tight schedule", "anxiety",
        "worried", "stretched", "stressed out", "insecure", "unsettled",
        "sleepless", "frantic", "undecided", "complex", "tough",
        "disconnected", "burning out", "challenging", "tiredness", "unfocused"
    )


    val POSITIVE_KEYWORDS = listOf(
        "happy", "calm", "relaxed", "peaceful", "good",
        "great", "excellent", "wonderful", "confident", "positive",
        "balanced", "focused", "energetic", "motivated", "optimistic",
        "grateful", "blessed", "joyful", "content", "satisfied",
        "hopeful", "cheerful", "excited", "elated", "upbeat",
        "lucky", "proud", "at ease", "inspired", "joyous",
        "enthusiastic", "excited", "cheerful", "serene", "satisfied",
        "free", "refreshed", "grounded", "supportive", "peaceful",
        "thriving", "empowered", "fulfilled", "proud", "gratified",
        "positive energy", "motivated", "ambitious", "determined", "centered",
        "loving", "affectionate", "calmness", "graceful", "contentment"
    )


    // Weights for different speech parameters
    const val HIGH_STRESS_KEYWORD_WEIGHT = 0.4f
    const val MODERATE_STRESS_KEYWORD_WEIGHT = 0.2f
    const val POSITIVE_KEYWORD_WEIGHT = 0.2f
    const val WORD_REPETITION_WEIGHT = 0.1f
    const val SPEAKING_RATE_WEIGHT = 0.1f

    // Thresholds
    const val NORMAL_SPEAKING_RATE_WPM = 150f // words per minute
    const val HIGH_STRESS_SPEAKING_RATE_WPM = 180f
    const val LOW_STRESS_SPEAKING_RATE_WPM = 120f
}