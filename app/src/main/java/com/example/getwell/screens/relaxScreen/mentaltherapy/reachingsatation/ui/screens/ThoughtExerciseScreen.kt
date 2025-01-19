package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.data.Screen
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components.AISuggestionCard
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components.ConsequenceCard
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components.InputField
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components.MoodSlider
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.components.WelcomeCard
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.viewmodel.ThoughtExerciseState
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.viewmodel.ThoughtExerciseViewModel
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.util.ValidationUtils


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThoughtExerciseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
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
                androidx.compose.material.IconButton(onClick = { navController.popBackStack() }) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                androidx.compose.material.Text(
                    text = "Reaching the Point of Satiation",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )
            }
        }
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
            ThoughtExercise(onViewPastExercises = { navController.navigate(Screen.PastSatationScreen.route)})
        }
    }
}
@Composable
fun ThoughtExercise(
    onViewPastExercises: () -> Unit,
    viewModel: ThoughtExerciseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentThought by remember { mutableStateOf("") }
    var currentConsequence by remember { mutableStateOf("") }
    var moodBefore by remember { mutableStateOf(5) }
    var moodAfter by remember { mutableStateOf(5) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (val state = uiState) {
            is ThoughtExerciseState.Initial -> {
                item {
                    WelcomeCard(
                        onStartExercise = { viewModel.startExercise() },
                        onViewPastExercises = onViewPastExercises
                    )
                }
            }

            is ThoughtExerciseState.Ready -> {
                item {
                    Text(
                        modifier = Modifier.padding(top = 32.dp, bottom = 2.dp),
                        text = "Enter your Upsetting Thought",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Cyan
                    )
                }

                item {
                    InputField(
                        value = currentThought,
                        onValueChange = { currentThought = it },
                        label = "What's bothering you?",
                        onSubmit = {
                            if (ValidationUtils.isValidThought(currentThought)) {
                                viewModel.setInitialThought(currentThought)
                            }
                        },
                        buttonText = "Save Thought"
                    )
                }
            }

            is ThoughtExerciseState.ThoughtCaptured -> {


                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(40, 249, 111)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Your Initial Thought:",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                            Text(
                                text = state.initialThought,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 8.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
                item {
                    Text(
                        text = "Enter a possible Consequence:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Cyan
                    )
                }

                item {
                    InputField(
                        value = currentConsequence,
                        onValueChange = { currentConsequence = it },
                        label = "What could happen? (Add each consequence one by one)",
                        onSubmit = {
                            if (ValidationUtils.isValidConsequence(currentConsequence)) {
                                viewModel.addConsequence(currentConsequence)
                                currentConsequence = ""
                            }
                        },
                        buttonText = "Add Consequence"
                    )
                }

                items(state.consequences) { consequence ->
                    ConsequenceCard(
                        consequence = consequence,
                        isRational = state.rationalAnalysis[consequence],
                        onMarkRational = { viewModel.analyzeConsequence(consequence, true) },
                        onMarkIrrational = { viewModel.analyzeConsequence(consequence, false) }
                    )
                }

                item {
                    if (state.consequences.isNotEmpty()) {
                        Button(
                            onClick = { viewModel.generateAISuggestion() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Color.Cyan)
                        ) {
                            Text("Get Suggestions", color = Color.Black)
                        }
                    }
                }

                item {
                    if (state.aiSuggestion.isNotEmpty()) {
                        AISuggestionCard(suggestion = state.aiSuggestion)
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MoodSlider(
                            label = "Mood Before",
                            value = moodBefore,
                            onValueChange = { moodBefore = ValidationUtils.validateMoodValue(it) }
                        )

                        MoodSlider(
                            label = "Mood After",
                            value = moodAfter,
                            onValueChange = { moodAfter = ValidationUtils.validateMoodValue(it) }
                        )

                        Button(
                            onClick = { viewModel.saveEntry(moodBefore, moodAfter) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Color.Cyan)
                        ) {
                            Text("Complete Exercise", color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }

            is ThoughtExerciseState.Completed -> {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Exercise Completed!",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "Thank you for participating. Your responses have been saved.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.startNew() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Start New Exercise")
                                }
                                Button(
                                    onClick = onViewPastExercises,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("View Past Exercises")
                                }
                            }
                        }
                    }
                }
            }

            is ThoughtExerciseState.Error -> {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Error",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Button(
                                onClick = { viewModel.startNew() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                Text("Try Again")
                            }
                        }
                    }
                }
            }
        }
    }
}