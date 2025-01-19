package com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import coil.compose.AsyncImage
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.model.YogaPose
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.ui.components.VoiceControlButton
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.util.VoiceGuidance

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun YogaDetailScreen(
    navController : NavHostController,
    pose: YogaPose,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var voiceGuidance by remember { mutableStateOf<VoiceGuidance?>(null) }
    val isPlaying by voiceGuidance?.isPlaying?.collectAsState(initial = false) ?: remember { mutableStateOf(false) }
    
    DisposableEffect(Unit) {
        val guidance = VoiceGuidance(context)
        voiceGuidance = guidance
        
        onDispose {
            guidance.shutdown()
        }
    }

    val scope = rememberCoroutineScope()
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
                    text = pose.name,
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
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = pose.imageUrl,
                    contentDescription = pose.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = pose.name,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = pose.sanskritName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Cyan
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Benefits:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                    pose.benefits.forEach { benefit ->
                        Text("• $benefit", color = Color.White )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                    pose.instructions.forEachIndexed { index, instruction ->
                        Text("${index + 1}. $instruction", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    VoiceControlButton(
                        isPlaying = isPlaying,
                        onPlayPause = {
                            voiceGuidance?.let { guidance ->
                                if (isPlaying) {
                                    guidance.pause()
                                } else {
                                    if (guidance.isPlaying.value) {
                                        guidance.resume()
                                    } else {
                                        guidance.speak(pose.instructions.joinToString(".   "))
                                    }
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                }
            }

        }
    }
}