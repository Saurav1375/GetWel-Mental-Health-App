package com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.ui

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.data.GeminiAiService
import java.util.Locale




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidedImageryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()


    // TextToSpeech initialization inside remember block for better lifecycle handling
    val textToSpeech = remember {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                //TODO
            }
        }
    }

    textToSpeech.setSpeechRate(0.7f)

    val geminiAiService = GeminiAiService("AIzaSyDBcqnegXZr8g0h5ED7kKZ4Nc_lraS_jIQ")
    val imageryViewModel = GuidedImageryViewModel(geminiAiService)
    imageryViewModel.initTextToSpeech(textToSpeech)

    // Ensure TextToSpeech is shutdown when the Composable leaves the composition
    DisposableEffect(context) {
        onDispose {
            textToSpeech.shutdown() // This will stop the speech when the screen is closed or composable leaves the composition
        }
    }

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
                        contentDescription = "Arrow Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Guided Imagery",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
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
            GuidedImagery(viewModel = imageryViewModel)
        }
    }
}

@Composable
fun GuidedImagery(
    viewModel: GuidedImageryViewModel,
    modifier: Modifier = Modifier
) {
    var userPrompt by remember { mutableStateOf("") }
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Instructions Section
        Text(
            text = """
                Guided imagery is a relaxation technique that helps reduce stress through visualization of peaceful scenes.
                
                How to use:
                1. Find a quiet, comfortable place
                2. Close your eyes and take deep breaths
                3. Enter a scene you'd like to visualize or choose from suggestions
                4. Listen to the description and immerse yourself in the imagery
                5. Focus on all sensory details - sights, sounds, smells, and feelings
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Input Section
        OutlinedTextField(
            value = userPrompt,
            onValueChange = { userPrompt = it },
            textStyle = TextStyle(color = Color.White),
            label = {
                Text(
                    text = "Enter scene to visualize (e.g., 'peaceful beach')",
                    style = TextStyle(color = Color(200, 200, 200))
                )
            },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Cyan
            )
        )

        // Button to generate imagery
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = { viewModel.generateImagery(userPrompt) },
            colors = ButtonDefaults.buttonColors(Color.Cyan)
        ) {
            Text("Generate Imagery")
        }

        // Generated Content Section
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        if (uiState.generatedText.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(Color(31, 31, 37))
            ) {
                Text(
                    text = uiState.generatedText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
