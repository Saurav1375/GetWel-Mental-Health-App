package com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PositiveThoughtCard(positiveThought: String?) {
    if (positiveThought != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 16.dp),
            colors = CardDefaults.cardColors(Color(31,31,37))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Positive Perspective:",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Cyan
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = positiveThought,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "🎉 Congratulations on transforming your thought!",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Green
                )
            }
        }
    }
}