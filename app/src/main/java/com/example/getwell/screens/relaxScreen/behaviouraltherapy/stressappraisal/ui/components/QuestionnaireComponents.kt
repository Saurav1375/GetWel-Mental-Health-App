package com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.AppraisalResult
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.StressSituation

@Composable
fun SituationInput(
    situation: StressSituation,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = situation.description,
        onValueChange = onDescriptionChange,
        textStyle = TextStyle(
            color = Color.White
        ),
        label = { Text("Describe your stressful situation", color = Color(200, 200, 200)) },
        modifier = modifier.fillMaxWidth(),
        minLines = 3
    )
}

@Composable
fun QuestionSection(
    title: String,
    options: Array<String>,
    selectedOption: Any?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.White
        )
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedOption == index,
                    onClick = { onOptionSelected(index) }
                )
                Text(
                    text = option,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(androidx.compose.ui.Alignment.CenterVertically),
                    color = Color.White

                )
            }
        }
    }
}

@Composable
fun AppraisalResult(
    result: AppraisalResult,
    modifier: Modifier = Modifier
) {
    val (color, textFirst, textSecond) = when (result) {
        AppraisalResult.POSITIVE -> Triple(
            Color.Green,
            "Positive Appraisal",
            "Your approach to this situation is positive and constructive."
        )
        AppraisalResult.NEUTRAL -> Triple(
            Color.Yellow,
            "Neutral Appraisal",
            "Your approach is balanced between positive and negative aspects."
        )
        AppraisalResult.NEGATIVE -> Triple(
            Color.Red,
            "Negative Appraisal",
            "Your approach might benefit from a more positive perspective."
        )
    }

    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = textFirst,
                style = MaterialTheme.typography.titleLarge,
                color = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textSecond,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}