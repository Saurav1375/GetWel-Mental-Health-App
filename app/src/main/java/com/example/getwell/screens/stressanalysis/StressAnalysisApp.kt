package com.example.getwell.screens.stressanalysis

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
import com.example.getwell.data.Screen
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.RelaxItem
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.ExhalationBreathing


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StressAnalysisApp(
    navController: NavHostController
) {
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
                    text = "AI-Stress Detection",
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

            StressAnalysisAI(navController = navController)
        }
    }

}

@Composable
fun StressAnalysisAI(
    viewModel: StressAnalysisViewModel = viewModel(),
    navController: NavHostController
) {
    var showImageAnalysis by remember { mutableStateOf(false) }
    var showSpeechAnalysis by remember { mutableStateOf(false) }
    val modelList = listOf(
        com.example.getwell.data.RelaxItem(
            title = "Face Analysis",
            banner = R.drawable.facedetection,
            route = "face"
        ), com.example.getwell.data.RelaxItem(
            title = "Speech Analysis",
            banner = R.drawable.speechdetetction,
            route = "speech"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        modelList.forEach { item->
            RelaxItem(item = item){
                if(item.route == "face"){
                    showImageAnalysis = true
            }

                else if(item.route == "speech"){
                    showSpeechAnalysis = true
                }
        }

    }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                if (showImageAnalysis) {
                    ImageAnalysisScreen(
                        onDismiss = { showImageAnalysis = false },
                        viewModel = viewModel
                    )
                }

                if (showSpeechAnalysis) {
                    SpeechAnalysisScreen(
                        onDismiss = { showSpeechAnalysis = false },
                        viewModel = viewModel
                    )
                }

                viewModel.analysisResult?.let { result ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(Color(31,31,37))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Analysis Results",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(result, color = Color.White)
                        }
                    }
                }
            }

        Spacer(modifier = Modifier.height(64.dp))
    }
}