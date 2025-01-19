package com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.ControlType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.ImpactType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.ResourceType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.SituationType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.ui.components.AppraisalResult
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.ui.components.QuestionSection
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.ui.components.SituationInput
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.viewmodel.StressAppraisalViewModel
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.ExhalationBreathing

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StressAppraisalScreen(
    navController : NavHostController,
    viewModel: StressAppraisalViewModel = viewModel()
) {
    val situations by viewModel.situations.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()


    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            androidx.compose.material.TopAppBar(
                backgroundColor = Color(31, 31, 37),
                elevation = 5.dp,
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 40.dp),
                contentColor = Color.White

            ) {


                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack",
                        tint = Color.White
                    )

                }
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Appraisal of Stressor",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.White

                    )
                )


            }

        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(49, 38, 45)),
            contentAlignment = Alignment.Center

        ) {
            Image(
                painter = painterResource(id = R.drawable.chatbg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                // Instructions
                if (currentStep == 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color(31,31,37))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Instructions",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "1. Identify three recent situations that caused stress for you\n" +
                                        "2. Answer the questions for each situation\n" +
                                        "3. Review your stress appraisal results",
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.nextStep() },
                                modifier = Modifier.align(androidx.compose.ui.Alignment.End),
                                colors = ButtonDefaults.buttonColors(Color.Cyan)
                            ) {
                                Text("Start Assessment", color = Color.Black)
                            }
                        }
                    }
                } else {
                    val situationIndex = (currentStep - 1) / 5
                    val questionStep = (currentStep - 1) % 5

                    if (situationIndex < 3) {
                        Text(
                            text = "Situation ${situationIndex + 1}",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 16.dp),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )

                        when (questionStep) {
                            0 -> SituationInput(
                                situation = situations[situationIndex],
                                onDescriptionChange = { viewModel.updateSituationDescription(situationIndex, it) }
                            )
                            1 -> QuestionSection(
                                title = "How would you characterize this situation?",
                                options = arrayOf("Challenging", "Threatening", "Harmful"),
                                selectedOption = situations[situationIndex].situationType?.ordinal,
                                onOptionSelected = { viewModel.updateSituationType(situationIndex, SituationType.entries[it]) }
                            )
                            2 -> QuestionSection(
                                title = "Who is in control of the situation?",
                                options = arrayOf("Self", "Others", "God/luck/fate"),
                                selectedOption = situations[situationIndex].control?.ordinal,
                                onOptionSelected = { viewModel.updateControl(situationIndex, ControlType.entries[it]) }
                            )
                            3 -> QuestionSection(
                                title = "How is the situation likely to affect your life?",
                                options = arrayOf(
                                    "Specific area of my life",
                                    "All aspects of my life",
                                    "The lives of others around, along with my life"
                                ),
                                selectedOption = situations[situationIndex].impact?.ordinal,
                                onOptionSelected = { viewModel.updateImpact(situationIndex, ImpactType.entries[it]) }
                            )
                            4 -> QuestionSection(
                                title = "What resources are available to handle it?",
                                options = arrayOf(
                                    "My own capability and effort",
                                    "Help from others",
                                    "Neither"
                                ),
                                selectedOption = situations[situationIndex].resources?.ordinal,
                                onOptionSelected = { viewModel.updateResources(situationIndex, ResourceType.entries[it]) }
                            )
                        }
                    } else {
                        // Results screen
                        Text(
                            text = "Your Stress Appraisal Results",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 16.dp),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )

                        situations.forEachIndexed { index, situation ->
                            Text(
                                text = "Situation ${index + 1}: ${situation.description}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.Cyan,
                                fontWeight = FontWeight.SemiBold
                            )
                            AppraisalResult(
                                result = viewModel.calculateAppraisal(situation)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { viewModel.previousStep() },
                            enabled = currentStep > 1,
                            colors = ButtonColors(
                                containerColor = Color.Cyan,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.Cyan,
                                disabledContentColor = Color.Black
                            )
                        ) {
                            Text("Previous", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                if(currentStep > 15) viewModel.reset() else viewModel.nextStep()
                            },
                            enabled = currentStep <= 16,
                            colors = ButtonDefaults.buttonColors(Color.Cyan)
                        ) {
                            Text(if (currentStep < 15) "Next" else "Finish", color = Color.Black)
                        }
                    }
                }
            }
        }
    }


}