package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun ControlButton(
    isExerciseStarted: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onToggle,
        modifier = modifier.size(64.dp),

        shape = MaterialTheme.shapes.extraLarge
    ) {
        Icon(
            modifier = Modifier.scale(1.5f),
            imageVector = if (isExerciseStarted) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isExerciseStarted) "Pause" else "Start"
        )
    }
}