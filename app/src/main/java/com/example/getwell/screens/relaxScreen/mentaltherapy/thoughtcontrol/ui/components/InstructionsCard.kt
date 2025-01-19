package com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.data.ThoughtInstructions

@Composable
fun InstructionsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(Color(31,31,37))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "How to Control Irrational Thoughts",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Cyan,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            ThoughtInstructions.steps.forEach { step ->
                Text(
                    text = step,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = Color.White
                )
            }
        }
    }
}