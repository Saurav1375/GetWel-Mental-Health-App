package com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.getwell.R
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.SleepState

@Composable
fun PersonSleepAnimation(
    currentStep: Int,
    sleepState: SleepState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {Image(
        painter = painterResource(
            id = when (sleepState) {
                SleepState.GET_UP -> R.drawable.sitting // Change this if you want different images
                SleepState.ACTIVITY -> R.drawable.bookreading
                SleepState.RELAX -> R.drawable.relaxsitting
                SleepState.DROWSY, SleepState.IDLE -> R.drawable.sleep // Same image for both
            }
        ),
        contentDescription = "Person in sleep exercise",
        modifier = Modifier.size(180.dp)
    )
    }
}
