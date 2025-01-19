package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeCard(
    onStartExercise: () -> Unit,
    onViewPastExercises: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(Color(31,31,37))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to the Point of Satiation Exercise",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = Color.Cyan
            )

            Text(
                text = "This exercise will help you process troubling thoughts by:",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("1. Exploring your thoughts fully",color = Color.White)
                Text("2. Examining possible consequences",color = Color.White)
                Text("3. Analyzing each consequence rationally",color = Color.White)
                Text("4. Getting AI-powered coping strategies",color = Color.White)
                Text("5. Tracking your mood changes",color = Color.White)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onStartExercise,
                    colors = ButtonDefaults.buttonColors(Color.Cyan),
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Start Exercise",color = Color.Black, textAlign = TextAlign.Center)
                }
                OutlinedButton(
                    onClick = onViewPastExercises,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("View Past Exercises",color = Color.Cyan, textAlign = TextAlign.Center)
                }
            }
        }
    }
}