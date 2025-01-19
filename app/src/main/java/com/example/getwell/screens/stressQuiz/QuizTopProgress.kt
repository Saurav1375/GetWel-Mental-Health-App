package com.example.getwell.screens.stressQuiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun QUizTopProgress(
    questionIndex: Int,
    totalQuestionCount: Int
){
    val progress by animateFloatAsState(
        targetValue = (questionIndex - 1)/totalQuestionCount.toFloat(), label = ""
    )

    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth().height(10.dp),
        progress = progress,
    )
}