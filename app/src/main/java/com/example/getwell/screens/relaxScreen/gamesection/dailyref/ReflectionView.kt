package com.example.getwell.screens.relaxScreen.gamesection.dailyref

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.ResetTv
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.di.Injection
import com.example.getwell.screens.customFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun ReflectionScreen(
    navController: NavHostController,
    userData: UserData?

) {
    val viewModel = ReflectionViewModel(ReflectionRepository(Injection.instance(), userData))

    BackHandler {
        navController.popBackStack()
        viewModel.resetData()

    }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
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

                androidx.compose.material3.IconButton(onClick = { navController.popBackStack() }) {
                    androidx.compose.material3.Icon(

                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack",
                        tint = Color.White
                    )

                }
                Spacer(modifier = Modifier.width(16.dp))

                androidx.compose.material3.Text(
                    text = "Daily Reflection",
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
                .background(Color(49, 38, 45))

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
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .clickable(
                        indication = null, // Disable click indication (ripple effect)
                        interactionSource = remember { MutableInteractionSource() }
                    ) { focusManager.clearFocus() },
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Daily Reflection",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    MainRefScreen(viewModel = viewModel)
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Past Reflections",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    PastReflectionsScreen(viewModel = viewModel)
                }


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Reflection Statistics",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    StreakInfoScreen(viewModel = viewModel)
                }
            }


        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainRefScreen(viewModel: ReflectionViewModel) {
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val todayReflection by viewModel.todayReflection.observeAsState()
    var answer by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        if (todayReflection == null) {
            currentQuestion?.let { question ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(31, 31, 37))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Text(
                            text = question,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White

                        )

                        TextField(
                            value = answer,
                            onValueChange = { answer = it },
                            textStyle = TextStyle(
                                color = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Type your answer...",
                                    color = Color.White
                                )
                            },
                            minLines = 3,


                            )

                        Button(
                            colors = androidx.compose.material.ButtonDefaults.buttonColors(Color.Cyan),
                            onClick = {
                                viewModel.submitReflection(answer, question)
                                answer = ""
                            },
                            modifier = Modifier.align(Alignment.End),
                            enabled = answer.isNotBlank()
                        ) {
                            Text("Save Reflection")
                        }
                    }
                }
            }
        } else {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(Color(31, 31, 37))

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Today's Reflection",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Cyan
                    )
                    Text(
                        text = todayReflection?.question ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Text(
                        text = todayReflection?.answer ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

        }

    }
}


// Past Reflections Screen
@Composable
fun PastReflectionsScreen(viewModel: ReflectionViewModel) {
    val pastReflections by viewModel.pastReflections.collectAsState()

    if (pastReflections.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 210.dp),
        ) {
            items(pastReflections) { reflection ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(Color(31, 31, 37))

                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = reflection.question,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = reflection.answer,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = reflection.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Cyan
                        )
                    }
                }
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(Color(31, 31, 37)),
            elevation = CardDefaults.cardElevation(4.dp)


        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    text = "No Past Reflections Yet...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }


        }

    }


}

@Composable
fun StreakInfoScreen(viewModel: ReflectionViewModel) {
    val userStreak by viewModel.userStreak.collectAsState()
    val pastReflections by viewModel.pastReflections.collectAsState()

    // Define theme colors to match the image
    val darkBackground = Color(0xFF121212)
    val cardBackground = Color(0xFF2D1B3D)  // Deep purple for cards
    val accentColor = Color(0xFF9C27B0)     // Bright purple accent
    val textColor = Color.White
    val secondaryTextColor = Color.White.copy(alpha = 0.7f)

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        // Streak Stats Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(cardBackground)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Streak Statistics",
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Current Streak",
                            style = MaterialTheme.typography.titleMedium,
                            color = secondaryTextColor
                        )
                        Text(
                            text = "${userStreak.currentStreak} days",
                            style = MaterialTheme.typography.titleMedium,
                            color = accentColor
                        )
                    }

                    Column {
                        Text(
                            text = "Longest Streak",
                            style = MaterialTheme.typography.titleMedium,
                            color = secondaryTextColor
                        )
                        Text(
                            text = "${userStreak.longestStreak} days",
                            style = MaterialTheme.typography.titleMedium,
                            color = accentColor
                        )
                    }
                }
            }
        }


    }
}