package com.example.getwell.screens.stressanalysis.analysis

import com.google.mlkit.vision.face.Face
import kotlin.math.abs
import kotlin.math.atan2

class ImageAnalyzer {
    fun analyzeFacialFeatures(face: Face): Pair<Float, List<String>> {
        val indicators = mutableListOf<String>()
        var totalStressScore = 0f

        // Analyze eye openness (internal cue)
        val leftEyeOpenness = face.leftEyeOpenProbability ?: 0f
        val rightEyeOpenness = face.rightEyeOpenProbability ?: 0f
        val avgEyeOpenness = (leftEyeOpenness + rightEyeOpenness) / 2
        
        if (avgEyeOpenness < ImageAnalysisParameters.EYE_OPENNESS_THRESHOLD) {
            indicators.add("Reduced eye openness detected (possible fatigue)")
            totalStressScore += ImageAnalysisParameters.EYE_OPENNESS_WEIGHT
        }

        // Analyze smile (external cue - weighted less)
        val smileProbability = face.smilingProbability ?: 0f
        if (smileProbability < ImageAnalysisParameters.SMILE_THRESHOLD) {
            indicators.add("Low smile probability detected")
            totalStressScore += ImageAnalysisParameters.SMILE_WEIGHT
        }

        // Analyze eye squinting
        val leftEyeContour = face.getContour(com.google.mlkit.vision.face.FaceContour.LEFT_EYE)?.points
        val rightEyeContour = face.getContour(com.google.mlkit.vision.face.FaceContour.RIGHT_EYE)?.points
        
        if (leftEyeContour != null && rightEyeContour != null) {
            val eyeSquintScore = calculateEyeSquint(leftEyeContour, rightEyeContour)
            if (eyeSquintScore > ImageAnalysisParameters.EYE_SQUINT_THRESHOLD) {
                indicators.add("Increased eye squinting detected (possible stress indicator)")
                totalStressScore += ImageAnalysisParameters.EYE_SQUINT_WEIGHT
            }
        }

        // Analyze brow position
        val leftBrow = face.getContour(com.google.mlkit.vision.face.FaceContour.LEFT_EYEBROW_TOP)?.points
        val rightBrow = face.getContour(com.google.mlkit.vision.face.FaceContour.RIGHT_EYEBROW_TOP)?.points
        
        if (leftBrow != null && rightBrow != null) {
            val browTensionScore = calculateBrowTension(leftBrow, rightBrow)
            if (browTensionScore > ImageAnalysisParameters.BROW_DISTANCE_THRESHOLD) {
                indicators.add("Elevated brow tension detected")
                totalStressScore += ImageAnalysisParameters.BROW_DISTANCE_WEIGHT
            }
        }

        // Analyze jaw tension
        val jawBottom = face.getContour(com.google.mlkit.vision.face.FaceContour.FACE)?.points
        if (jawBottom != null) {
            val jawTensionScore = calculateJawTension(jawBottom)
            if (jawTensionScore > ImageAnalysisParameters.JAW_TENSION_THRESHOLD) {
                indicators.add("Increased jaw tension detected")
                totalStressScore += ImageAnalysisParameters.JAW_TENSION_WEIGHT
            }
        }

        return Pair(totalStressScore, indicators)
    }

    private fun calculateEyeSquint(leftEyePoints: List<android.graphics.PointF>, rightEyePoints: List<android.graphics.PointF>): Float {
        // Calculate eye height to width ratio
        val leftEyeRatio = calculateEyeRatio(leftEyePoints)
        val rightEyeRatio = calculateEyeRatio(rightEyePoints)
        return (leftEyeRatio + rightEyeRatio) / 2
    }

    private fun calculateEyeRatio(eyePoints: List<android.graphics.PointF>): Float {
        val height = abs(eyePoints[4].y - eyePoints[8].y)
        val width = abs(eyePoints[0].x - eyePoints[8].x)
        return if (width != 0f) height / width else 0f
    }

    private fun calculateBrowTension(leftBrow: List<android.graphics.PointF>, rightBrow: List<android.graphics.PointF>): Float {
        // Calculate average brow height relative to face height
        val leftBrowHeight = leftBrow.map { it.y }.average().toFloat()
        val rightBrowHeight = rightBrow.map { it.y }.average().toFloat()
        return abs(leftBrowHeight - rightBrowHeight)
    }

    private fun calculateJawTension(jawPoints: List<android.graphics.PointF>): Float {
        // Calculate jaw line tension based on curve smoothness
        var tension = 0f
        for (i in 1 until jawPoints.size - 1) {
            val angle = calculateAngle(jawPoints[i-1], jawPoints[i], jawPoints[i+1])
            tension += angle
        }
        return tension / (jawPoints.size - 2)
    }

    private fun calculateAngle(p1: android.graphics.PointF, p2: android.graphics.PointF, p3: android.graphics.PointF): Float {
        val angle1 = atan2((p2.y - p1.y).toDouble(), (p2.x - p1.x).toDouble())
        val angle2 = atan2((p3.y - p2.y).toDouble(), (p3.x - p2.x).toDouble())
        return abs(angle1 - angle2).toFloat()
    }
}