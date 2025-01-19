package com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.SleepState

@Composable
fun SleepAnimation(
    isExerciseStarted: Boolean,
    sleepState: SleepState,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(
        targetValue = when (sleepState) {
            SleepState.DROWSY -> 0.3f
            SleepState.RELAX -> 0.6f
            else -> 1.0f
        },
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        ), label = ""
    )
    
    Surface(
        modifier = modifier
            .size(160.dp).alpha(alpha),
        shape = CircleShape

    ) {
        Box(modifier = modifier
            .size(160.dp)
            .background(brush = Brush.verticalGradient(
                listOf(
                    Color.Gray,
                    Color.Magenta
                )
            ), CircleShape)
            .alpha(alpha),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (sleepState) {
                    SleepState.GET_UP -> "Get Up"
                    SleepState.ACTIVITY -> "Activity"
                    SleepState.RELAX -> "Relax"
                    SleepState.DROWSY -> "Drowsy"
                    SleepState.IDLE -> "Ready"
                },
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}