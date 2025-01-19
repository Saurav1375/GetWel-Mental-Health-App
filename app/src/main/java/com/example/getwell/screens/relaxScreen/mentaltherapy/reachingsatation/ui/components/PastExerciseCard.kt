package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.model.ThoughtEntry
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PastExerciseCard(
    thoughtEntry: ThoughtEntry,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                        .format(thoughtEntry.timestamp.toDate()),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Mood: ${thoughtEntry.moodBefore} → ${thoughtEntry.moodAfter}",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            
            Text(
                text = thoughtEntry.initialThought,
                style = MaterialTheme.typography.bodyLarge
            )
            
            if (expanded) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                Text(
                    text = "Consequences:",
                    style = MaterialTheme.typography.titleSmall
                )
                thoughtEntry.consequences.forEach { consequence ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "• $consequence",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = if (thoughtEntry.rationalAnalysis[consequence] == true) "Rational" else "Irrational",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (thoughtEntry.rationalAnalysis[consequence] == true)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.error
                        )
                    }
                }
                
                if (thoughtEntry.aiSuggestion.isNotEmpty()) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Suggestions:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = thoughtEntry.aiSuggestion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}