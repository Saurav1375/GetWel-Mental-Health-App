package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BreathingInstructions(currentStep: Int) {
    val instructions = listOf(
        "Lie down on your back with arms at your sides",
        "Begin to breathe in slowly, raising your arms towards the ceiling",
        "Move arms over your head to the floor, complete inhalation",
        "Breathe out slowly, return arms to sides",
        "Practice breathing without moving arms"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            Color(31,31,37)
        )

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Step ${currentStep + 1}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = instructions[currentStep],
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
    }
}