package com.example.getwell.screens.relaxScreen.physicaltherapy.sleep

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
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.ControlButton
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components.PersonSleepAnimation
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components.SleepAnimation
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components.SleepInstructions
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components.rememberSleepInstructor
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RestfulSleepScreen(
    navController: NavHostController
)
{
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
                    text = "Restful Sleep",
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

            RestfulSleep()
        }
    }

}

@Composable
fun RestfulSleep() {
    var screenState by remember { 
        mutableStateOf(SleepScreenState())
    }
    
    val context = LocalContext.current
    val speechInstructor = rememberSleepInstructor(context)
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
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SleepAnimation(
                isExerciseStarted = screenState.isExerciseStarted,
                sleepState = screenState.sleepState,
//                modifier = Modifier.weight(1f)
            )
            
            PersonSleepAnimation(
                currentStep = screenState.currentStep,
                sleepState = screenState.sleepState,
//                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        SleepInstructions(screenState.currentStep)
        Spacer(modifier = Modifier.height(8.dp))
        
        ControlButton(
            isExerciseStarted = screenState.isExerciseStarted,
            onToggle = { 
                val newState = !screenState.isExerciseStarted
                screenState = screenState.copy(
                    isExerciseStarted = newState,
                    sleepState = if (newState) SleepState.GET_UP else SleepState.IDLE
                )
                if (newState) {
                    speechInstructor.speak("Starting sleep relaxation exercise. Remember, don't try to force sleep.")
                }
            }
        )
    }
    
    LaunchedEffect(screenState.isExerciseStarted) {
        if (screenState.isExerciseStarted) {
            while (true) {
                // Get up phase
                screenState = screenState.copy(
                    currentStep = 0,
                    sleepState = SleepState.GET_UP
                )
                speechInstructor.speakSleepState(SleepState.GET_UP)
                delay(8000)
                
                // Activity phase
                screenState = screenState.copy(
                    currentStep = 1,
                    sleepState = SleepState.ACTIVITY
                )
                speechInstructor.speakSleepState(SleepState.ACTIVITY)
                delay(8000)
                
                // Relax phase
                screenState = screenState.copy(
                    currentStep = 2,
                    sleepState = SleepState.RELAX
                )
                speechInstructor.speakSleepState(SleepState.RELAX)
                delay(8000)
                
                // Drowsy phase
                screenState = screenState.copy(
                    currentStep = 3,
                    sleepState = SleepState.DROWSY
                )
                speechInstructor.speakSleepState(SleepState.DROWSY)
                delay(8000)
            }
        } else {
            screenState = screenState.copy(
                currentStep = 0,
                sleepState = SleepState.IDLE
            )
        }
    }
}