package com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Congratulations!") },
        text = {
            Column {
                Text(
                    "You've successfully expressed your feelings! This is a great step towards emotional well-being.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Tips for emotional well-being:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Practice mindful breathing")
                Text("• Take regular walks in nature")
                Text("• Maintain a consistent sleep schedule")
                Text("• Connect with loved ones")
                Text("• Continue journaling regularly")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Continue")
            }
        }
    )
}