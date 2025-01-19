package com.example.getwell.screens.stressanalysis.analysis

data class FacialStressIndicators(
    val eyeOpenness: Float = 0f,
    val smileProbability: Float = 0f,
    val leftEyeSquint: Float = 0f,
    val rightEyeSquint: Float = 0f,
    val browDistance: Float = 0f,
    val jawTension: Float = 0f
)

object ImageAnalysisParameters {
    // Threshold values for different facial features
    const val EYE_OPENNESS_THRESHOLD = 0.6f
    const val SMILE_THRESHOLD = 0.3f
    const val EYE_SQUINT_THRESHOLD = 0.4f
    const val BROW_DISTANCE_THRESHOLD = 0.5f
    const val JAW_TENSION_THRESHOLD = 0.7f

    // Weights for different parameters (internal cues weighted more heavily)
    const val EYE_OPENNESS_WEIGHT = 0.3f
    const val SMILE_WEIGHT = 0.1f // External cue - weighted less
    const val EYE_SQUINT_WEIGHT = 0.2f
    const val BROW_DISTANCE_WEIGHT = 0.2f
    const val JAW_TENSION_WEIGHT = 0.2f
}