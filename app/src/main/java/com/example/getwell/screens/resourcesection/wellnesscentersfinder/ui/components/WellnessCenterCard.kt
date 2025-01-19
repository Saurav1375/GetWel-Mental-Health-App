package com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.data.WellnessCenter

@Composable
fun WellnessCenterCard(
    wellnessCenter: WellnessCenter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(Color(31,31,37)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = wellnessCenter.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Cyan
            )
            Spacer(modifier = Modifier.height(8.dp))
            wellnessCenter.address?.let { address ->
                Text(
                    text = address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            wellnessCenter.type?.let { type ->
                Text(
                    text = "Type: $type",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            wellnessCenter.distance?.let { distance ->
                Text(
                    text = "Distance: ${distance}km",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }

        }
    }
}