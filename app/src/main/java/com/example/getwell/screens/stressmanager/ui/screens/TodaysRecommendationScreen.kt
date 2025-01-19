package com.example.getwell.screens.stressmanager.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.domain.provider.StressUIState
import com.example.getwell.domain.provider.StressViewModel
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.ExhalationBreathing
import com.example.getwell.screens.stressmanager.data.StressLevel
import com.example.getwell.screens.stressmanager.data.RecommendationRepository
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodaysRecommendationScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: StressViewModel,
    repository: RecommendationRepository
) {
    var stressLevel by remember { mutableStateOf(-1) } // Initial stress value (High)
    var recommendations by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val stressState by viewModel.stressState.collectAsState()
    val currentStressLevel = StressLevel.fromValue(stressLevel)
    when(val temp = stressState){
        is StressUIState.Success -> {
            stressLevel = temp.stressValues.stressValue.toInt()
        }
        else -> {
            ///TODO
        }
    }



    LaunchedEffect(stressLevel){
        coroutineScope.launch {
            isLoading = true
            if(stressLevel != -1){
                recommendations = repository.generateDailyPlan(currentStressLevel)

            }
            isLoading = false
        }
    }

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
                        text = "Today's Recommendation",
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
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        text = "Today's Stress Management Plan",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )


                    Text(
                        text = "Current Stress Level: ${currentStressLevel.label}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Color.Cyan,
                    )

                    when (currentStressLevel) {
                        StressLevel.HIGH, StressLevel.EXTREME -> {
                            Text(
                                text = "Your stress levels are concerning. Let's work on reducing them.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(192, 32, 71),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                        else -> {
                            Text(
                                text = "Your stress levels are manageable. Let's maintain or improve them.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 16.dp),
                                color = Color.Green
                            )
                        }
                    }

                    if (isLoading) {
                        Column(modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Generating personalized plan for you...",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.padding(16.dp)
                            )

                        }

                    } else if (recommendations != null) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31,31,37), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = recommendations!!,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }

                    }

                }

            }
        }




}