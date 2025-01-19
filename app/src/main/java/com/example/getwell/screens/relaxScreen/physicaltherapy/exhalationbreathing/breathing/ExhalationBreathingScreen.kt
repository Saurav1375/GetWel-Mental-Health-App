package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
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
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.BreathingAnimation
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.BreathingInstructions
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.ControlButton
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.PersonAnimation
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.rememberSpeechInstructor
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExhalationBreathingScreen(
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
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
                    text = "Exhalation Breathing",
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

            ExhalationBreathing()
        }
    }

}


@Composable
fun ExhalationBreathing() {
    var screenState by remember {
        mutableStateOf(BreathingScreenState())
    }

    val context = LocalContext.current
    val speechInstructor = rememberSpeechInstructor(context)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BreathingAnimation(
                isExerciseStarted = screenState.isExerciseStarted,
                breathingState = screenState.breathingState,
//                modifier = Modifier.weight(0.5f)
            )

            PersonAnimation(
                currentStep = screenState.currentStep,
                breathingState = screenState.breathingState,
//                modifier = Modifier.weight(1f)
            )
        }

        BreathingInstructions(screenState.currentStep)

        Spacer(modifier = Modifier.height(8.dp))

        ControlButton(
            isExerciseStarted = screenState.isExerciseStarted,
            onToggle = {
                val newState = !screenState.isExerciseStarted
                screenState = screenState.copy(
                    isExerciseStarted = newState,
                    breathingState = if (newState) {
                        BreathingState.INHALE
                    } else {
                        BreathingState.IDLE
                    }
                )
                if (newState) {
                    speechInstructor.speak("Starting breathing exercise. Get ready.")
                }
            }
        )
    }

    LaunchedEffect(screenState.isExerciseStarted) {
        if (screenState.isExerciseStarted) {
            while (true) {
                // Inhale phase
                screenState = screenState.copy(
                    currentStep = 1,
                    breathingState = BreathingState.INHALE
                )
                speechInstructor.speakBreathingState(BreathingState.INHALE)
                delay(5000)

                // Hold phase
                screenState = screenState.copy(
                    currentStep = 2,
                    breathingState = BreathingState.HOLD
                )
                speechInstructor.speakBreathingState(BreathingState.HOLD)
                delay(5000)

                // Exhale phase
                screenState = screenState.copy(
                    currentStep = 3,
                    breathingState = BreathingState.EXHALE
                )
                speechInstructor.speakBreathingState(BreathingState.EXHALE)
                delay(5000)

                // Practice without arms
                screenState = screenState.copy(
                    currentStep = 4,
                    breathingState = BreathingState.INHALE
                )
                speechInstructor.speak("Now practice breathing without moving your arms")
                delay(5000)
            }
        } else {
            screenState = screenState.copy(
                currentStep = 0,
                breathingState = BreathingState.IDLE
            )
            speechInstructor.speakBreathingState(BreathingState.IDLE)
        }
    }
}