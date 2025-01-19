package com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
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
import com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.ui.components.InstructionsCard
import com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.ui.components.PositiveThoughtCard
import com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.viewmodel.GeminiService
import com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.viewmodel.ThoughtViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IrrationalThoughtScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {


    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val geminiService = GeminiService("AIzaSyDBcqnegXZr8g0h5ED7kKZ4Nc_lraS_jIQ")
    val viewModel = ThoughtViewModel(geminiService)
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
                    text = "Irrational Thought Control",
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
           ThoughtControlScreen(viewModel)
        }
    }
}

@Composable
fun ThoughtControlScreen(
    viewModel: ThoughtViewModel
) {

    var userPrompt by remember { mutableStateOf("") }
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InstructionsCard()
        Spacer(modifier = Modifier.height(16.dp))
        // Input Section
        androidx.compose.material.OutlinedTextField(
            value = userPrompt,
            onValueChange = { userPrompt = it },
            textStyle = TextStyle(color = Color.White),
            label = {
                androidx.compose.material.Text(
                    text = "Enter your Irrational thought..",
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
        androidx.compose.material.Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = { viewModel.generateThought(userPrompt) },
            colors = ButtonDefaults.buttonColors(Color.Cyan)
        ) {
            androidx.compose.material.Text("Transform Thought")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Generated Content Section
        if (uiState.isLoading) {
            androidx.compose.material.CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        if (uiState.generatedText.isNotEmpty()) {
            PositiveThoughtCard(positiveThought = uiState.generatedText)
        }

    }

}