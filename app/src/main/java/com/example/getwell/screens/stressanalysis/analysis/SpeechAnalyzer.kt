package com.example.getwell.screens.stressanalysis.analysis

class SpeechAnalyzer {
    fun analyzeSpeech(spokenText: String): Triple<Float, List<String>, VoiceStressIndicators> {
        val words = spokenText.lowercase().split(" ")
        val indicators = mutableListOf<String>()
        var totalStressScore = 0f

        // Analyze keyword frequencies
        val highStressCount = words.count { word -> 
            SpeechAnalysisParameters.HIGH_STRESS_KEYWORDS.any { it in word }
        }
        
        val moderateStressCount = words.count { word ->
            SpeechAnalysisParameters.MODERATE_STRESS_KEYWORDS.any { it in word }
        }
        
        val positiveCount = words.count { word ->
            SpeechAnalysisParameters.POSITIVE_KEYWORDS.any { it in word }
        }

        // Analyze word repetitions
        val wordFrequencies = words.groupingBy { it }.eachCount()
        val repetitions = wordFrequencies.filter { it.value > 2 }.size

        // Calculate speaking rate (words per minute)
        val speakingRate = calculateSpeakingRate(words.size)

        // Calculate stress scores
        if (highStressCount > 0) {
            totalStressScore += (highStressCount * SpeechAnalysisParameters.HIGH_STRESS_KEYWORD_WEIGHT)
            indicators.add("High stress keywords detected: $highStressCount occurrences")
        }

        if (moderateStressCount > 0) {
            totalStressScore += (moderateStressCount * SpeechAnalysisParameters.MODERATE_STRESS_KEYWORD_WEIGHT)
            indicators.add("Moderate stress keywords detected: $moderateStressCount occurrences")
        }

        if (positiveCount > 0) {
            totalStressScore -= (positiveCount * SpeechAnalysisParameters.POSITIVE_KEYWORD_WEIGHT)
            indicators.add("Positive keywords detected: $positiveCount occurrences")
        }

        // Analyze speaking rate
        val rateScore = analyzeSpeakingRate(speakingRate)
        totalStressScore += (rateScore * SpeechAnalysisParameters.SPEAKING_RATE_WEIGHT)
        
        if (speakingRate > SpeechAnalysisParameters.HIGH_STRESS_SPEAKING_RATE_WPM) {
            indicators.add("Rapid speaking rate detected (possible anxiety indicator)")
        } else if (speakingRate < SpeechAnalysisParameters.LOW_STRESS_SPEAKING_RATE_WPM) {
            indicators.add("Slow speaking rate detected (possible fatigue/depression indicator)")
        }

        // Analyze word repetitions
        if (repetitions > 0) {
            totalStressScore += (repetitions * SpeechAnalysisParameters.WORD_REPETITION_WEIGHT)
            indicators.add("Word repetitions detected: $repetitions words repeated frequently")
        }

        // Normalize total stress score to 0-1 range
        totalStressScore = (totalStressScore.coerceIn(0f, 1f))

        val voiceIndicators = VoiceStressIndicators(
            stressKeywordCount = highStressCount + moderateStressCount,
            positiveKeywordCount = positiveCount,
            wordRepetitions = repetitions,
            speakingRate = speakingRate
        )

        return Triple(totalStressScore, indicators, voiceIndicators)
    }

    private fun calculateSpeakingRate(wordCount: Int): Float {
        // Assuming average speaking time of 15 seconds for the sample
        return (wordCount / 15f) * 60f
    }

    private fun analyzeSpeakingRate(wpm: Float): Float {
        return when {
            wpm > SpeechAnalysisParameters.HIGH_STRESS_SPEAKING_RATE_WPM -> 0.8f
            wpm < SpeechAnalysisParameters.LOW_STRESS_SPEAKING_RATE_WPM -> 0.6f
            else -> 0.3f
        }
    }
}