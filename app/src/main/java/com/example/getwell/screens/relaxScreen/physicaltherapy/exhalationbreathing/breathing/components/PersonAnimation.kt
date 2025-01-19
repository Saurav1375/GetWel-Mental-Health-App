package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.BreathingState

@Composable
fun PersonAnimation(
    currentStep: Int,
    breathingState: BreathingState,
    modifier: Modifier = Modifier
) {
    val armAngle by animateFloatAsState(
        targetValue = when (breathingState) {
            BreathingState.INHALE -> 90f
            BreathingState.HOLD -> 180f
            BreathingState.EXHALE -> 45f
            BreathingState.IDLE -> 0f
        },
        animationSpec = tween(
            durationMillis = 5000,
            easing = FastOutSlowInEasing
        ), label = ""
    )

    Canvas(
        modifier = modifier.size(200.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val bodyLength = size.height * 0.4f
        val armLength = size.width * 0.25f
        val headRadius = size.width * 0.1f

        // Draw head
        drawCircle(
            color = Color.White,
            radius = headRadius,
            center = Offset(centerX, centerY - bodyLength / 2 - headRadius),
            style = Fill
        )

        // Draw body
        drawLine(
            color = Color.White,
            start = Offset(centerX, centerY - bodyLength / 2),
            end = Offset(centerX, centerY + bodyLength / 2),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )

        // Draw arms with animation
        val armRadians = Math.toRadians(armAngle.toDouble())
        val leftArmEnd = Offset(
            x = centerX - (armLength * kotlin.math.cos(armRadians)).toFloat(),
            y = centerY - (armLength * kotlin.math.sin(armRadians)).toFloat()
        )
        val rightArmEnd = Offset(
            x = centerX + (armLength * kotlin.math.cos(armRadians)).toFloat(),
            y = centerY - (armLength * kotlin.math.sin(armRadians)).toFloat()
        )

        // Left arm
        drawLine(
            color = Color.White,
            start = Offset(centerX, centerY),
            end = leftArmEnd,
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )

        // Right arm
        drawLine(
            color = Color.White,
            start = Offset(centerX, centerY),
            end = rightArmEnd,
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )

        // Draw legs
        val legSpread = size.width * 0.15f
        drawLine(
            color = Color.White,
            start = Offset(centerX, centerY + bodyLength / 2),
            end = Offset(centerX - legSpread, centerY + bodyLength),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.White,
            start = Offset(centerX, centerY + bodyLength / 2),
            end = Offset(centerX + legSpread, centerY + bodyLength),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }
}