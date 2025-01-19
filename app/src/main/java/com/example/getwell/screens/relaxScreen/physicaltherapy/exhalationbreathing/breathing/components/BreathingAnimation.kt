package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.BreathingState

@Composable
fun BreathingAnimation(
    isExerciseStarted: Boolean,
    breathingState: BreathingState,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = when (breathingState) {
            BreathingState.INHALE -> 1.3f
            BreathingState.HOLD -> 1.3f
            BreathingState.EXHALE -> 1.0f
            BreathingState.IDLE -> 1.0f
        },
        animationSpec = tween(
            durationMillis = 5000,
            easing = FastOutSlowInEasing
        ), label = ""
    )
    
    Surface(
        modifier = modifier
            .size(160.dp)
            .background(Color.Unspecified, CircleShape)
            .scale(scale),
        shape = CircleShape,
    ) {
        Box(modifier = modifier
            .size(160.dp)
            .background(brush = Brush.verticalGradient(
                listOf(
                    Color.Red,
                    Color.Magenta,
                )
            ), CircleShape)
            .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (breathingState) {
                    BreathingState.INHALE -> "Inhale"
                    BreathingState.HOLD -> "Hold"
                    BreathingState.EXHALE -> "Exhale"
                    BreathingState.IDLE -> "Ready"
                },
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}