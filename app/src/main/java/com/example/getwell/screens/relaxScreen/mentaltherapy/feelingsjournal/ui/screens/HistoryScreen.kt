package com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.ui.screens

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.getwell.authSystem.UserData
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.viewmodel.FeelingsViewModel
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.ui.GuidedImagery
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.ui.GuidedImageryViewModel
import java.text.SimpleDateFormat
import java.util.*
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    viewModel: FeelingsViewModel,
    userData: UserData?,
    navController : NavHostController
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
                    text = "Feelings History",
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

            History(viewModel = viewModel, userData = userData)
        }
    }

}
@Composable
fun History(viewModel: FeelingsViewModel, userData: UserData?) {
    val entries by viewModel.entries.collectAsState()
    viewModel.loadEntries(userData!!.emailId)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(entries) { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(Color(31,31,37))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                            .format(entry.timestamp.toDate()),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Cyan
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = entry.content, color = Color.White)
                }
            }
        }
    }
}