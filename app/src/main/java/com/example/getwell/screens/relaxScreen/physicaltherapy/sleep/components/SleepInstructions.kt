package com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SleepInstructions(currentStep: Int) {
    val instructions = listOf(
        "If you can't sleep, get out of bed and sit in a comfortable chair",
        "Choose a relaxing activity like reading or listening to calm music",
        "Stay relaxed and don't force yourself to sleep",
        "When you feel drowsy, return to bed"
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
                color = Color.Cyan,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = instructions[currentStep],
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}