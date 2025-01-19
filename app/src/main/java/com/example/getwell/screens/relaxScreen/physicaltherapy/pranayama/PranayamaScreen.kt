package com.example.getwell.screens.relaxScreen.physicaltherapy.pranayama

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.ExhalationBreathing

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PranayamaScreen(
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
                    text = "Pranayama and Yoga",
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

            Pranayama()
        }
    }

}

@Composable
fun Pranayama(
    viewModel: PranayamaViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.initTextToSpeech(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Rounds selector
        OutlinedTextField(
            value = state.totalRounds.toString(),
            textStyle = TextStyle(
                color = Color.White
            ),
            onValueChange = {
                val input = it.toIntOrNull()
                if (input != null) {
                    // Ensure the value does not exceed 20
                    val rounds = if (input > 20) 20 else input
                    viewModel.updateTotalRounds(rounds)
                }
            },
            maxLines = 1,
            label = { Text("Number of Rounds", color = Color.Cyan) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )


        // Current instruction
        Text(
            text = state.currentInstruction,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        // Progress indicators
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Round: ${state.currentRound}/${state.totalRounds}", color= Color.Cyan, fontWeight = FontWeight.SemiBold)
            Text("Phase: ${state.currentPhase}",color = Color.White )
        }
        Spacer(modifier = Modifier.height(8.dp))


        // Pranayama illustration
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val scale by animateFloatAsState(
                targetValue = if (state.isBreathingIn) 1.2f else 1f,
                animationSpec = tween(durationMillis = state.phaseDuration.toInt()), label = ""
            )
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(
                    when (state.currentPhase) {
                        PranayamaPhase.RIGHT_NOSTRIL_INHALE -> R.drawable.rightinhale
                        PranayamaPhase.LEFT_NOSTRIL_EXHALE -> R.drawable.leftexhale
                        PranayamaPhase.LEFT_NOSTRIL_INHALE -> R.drawable.leftinhale
                        PranayamaPhase.RIGHT_NOSTRIL_EXHALE -> R.drawable.rightexhale
                        else -> R.drawable.blank
                    }
                ),
                contentDescription = "Pranayama Instruction",
                modifier = Modifier
                    .scale(scale)
                    .size(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))


        // Control buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(Color.Cyan),
                onClick = { viewModel.resetPractice() }
            ) {
                Text("Reset", color = Color.White)
            }

            Button(
                colors = ButtonDefaults.buttonColors(Color.Cyan),
                onClick = { 
                    if (state.isActive) viewModel.pausePractice() 
                    else viewModel.startPractice() 
                }
            ) {
                Text(if (state.isActive) "Pause" else "Start", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Benefits text
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(Color(31,31,37))
        ) {
            Text(
                text = "Benefits:\n" +
                        "• Soothes anxiety and reduces tension levels\n" +
                        "• Prevents devastating impact of stress\n" +
                        "• Enhances effectiveness of coping\n" +
                        "• Helps in realistic appraisal of situations\n" +
                        "• Can be practiced anytime and anywhere",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}