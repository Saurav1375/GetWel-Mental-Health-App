package com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.authSystem.UserData
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.viewmodel.FeelingsViewModel
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.ui.components.SuccessDialog


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JournalScreen(
    viewModel: FeelingsViewModel,
    userData: UserData?,
    navController : NavHostController,
    onNavigateToHistory: () -> Unit
) {
    val scope = rememberCoroutineScope()
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
                    text = "Ventilate Your Feelings",
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

            Journal(viewModel = viewModel, userData = userData) {
                onNavigateToHistory()

            }
        }
    }

}

@Composable
fun Journal(
    viewModel: FeelingsViewModel,
    userData: UserData?,
    onNavigateToHistory: () -> Unit
) {
    var journalContent by remember { mutableStateOf("") }
    val isSubmitted by viewModel.isSubmitted.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Write down your feelings, following the guidelines mentioned below for a better effect.\n" +
                    "1. Write your feelings as a story, with a clear beginning and end.\n" +
                    "2. Keep the writing spontaneous and natural, as if telling someone about an event or conflict.\n" +
                    "3. Don't worry about editing—let your feelings flow without filtering or classification.\n" +
                    "4. If a thought feels silly, feel free to mention it. Transparency is key.\n" +
                    "5. Once your feelings are out, you may choose a strategy to cope, or find that writing helps you reappraise the situation.",
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color.White
        )

        OutlinedTextField(
            value = journalContent,
            onValueChange = { journalContent = it },
            textStyle = TextStyle(
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = { Text(
                text ="Express your feelings here...",
                style = TextStyle(
                    color = Color(200,200,200)

                )
            ) },
            maxLines = 10,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            enabled = journalContent.isNotEmpty(),
            border = BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)),
            colors = ButtonDefaults.buttonColors(Color(31,31,37)),
            onClick = {
                if (userData != null && journalContent.isNotEmpty()) {
                    viewModel.saveFeelingEntry(journalContent, userData.emailId)
                } // Replace with actual user ID
                journalContent = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Entry", color = Color.Cyan)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToHistory,
            colors = ButtonDefaults.buttonColors(Color.Cyan),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("View Past Entries", color = Color.White)
        }

        if (isSubmitted) {
            SuccessDialog(
                onDismiss = { viewModel.resetSubmission() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}