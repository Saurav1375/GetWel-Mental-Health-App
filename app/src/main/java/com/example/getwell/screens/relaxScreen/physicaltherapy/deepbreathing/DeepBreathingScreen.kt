package com.example.getwell.screens.relaxScreen.physicaltherapy.deepbreathing


import android.annotation.SuppressLint
import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components.rememberSpeechInstructor
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components.rememberSleepInstructor
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeepBreathingScreen(
    navController: NavHostController,
) {



    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()







    var isBreathing by remember { mutableStateOf(true) }
    val visible = remember { mutableStateOf(false) }

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
                    text = "Deep Breathing",
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

            if (isBreathing) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {


                    Text(
                        text = "Deep Breathing",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Follow the instructions to achieve deep relaxation",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color(255, 255, 255),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        modifier = Modifier.clip(CircleShape),
                        painter = rememberDrawablePainter(
                            drawable = getDrawable(
                                LocalContext.current,
                                R.drawable.breathe
                            )
                        ),
                        contentDescription = "med1",
                        contentScale = ContentScale.FillWidth,
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = "Start Now!",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                }


            } else {
                LaunchedEffect(Unit) {
                    visible.value = true
                }
                DeepBreathingExercise(navController, visible)
            }
            Box(
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .size(60.dp)
                    .background(Color(255, 255, 255, 70), CircleShape)
                    .align(Alignment.BottomCenter)
                    .clickable(
                        indication = null, // Disable the ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        visible.value = false
                        scope.launch {
                            delay(200)
                            isBreathing = !isBreathing
                        }

                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(
                        id = if (!isBreathing) R.drawable.baseline_clear_24 else R.drawable.baseline_play_arrow_24
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(Color.White)
                )

            }

        }
    }
}



@Composable
fun DeepBreathingExercise(
    navController: NavHostController,
    visible: MutableState<Boolean>,
) {
    var isAnimating by remember { mutableStateOf(false) }
    var animationState by remember { mutableStateOf(10) }
    val context = LocalContext.current

    val sizeAnimatable = remember { Animatable(400f) }
    val rotationAnimatable = remember { Animatable(0f) }

    val speechInstructor = rememberSleepInstructor(context)



    val scope = rememberCoroutineScope()


    LaunchedEffect(animationState) {
        when (animationState) {
            4 -> speechInstructor.speak("Get ready")
            -1 -> speechInstructor.speak("Breathe in")
            0 -> speechInstructor.speak("Hold")
            1 -> speechInstructor.speak("Breathe out")
            6 -> speechInstructor.speak("Relax")
        }
    }
    LaunchedEffect(Unit) {
        delay(3000)
        animationState = 4
        delay(3000)
        isAnimating = true
    }




    // Trigger animation when isAnimating becomes true
    LaunchedEffect(isAnimating) {

        if (isAnimating) {
            while (isAnimating) {
                // Growing phase
                scope.launch {
                    while (isAnimating) {
                        rotationAnimatable.animateTo(
                            targetValue = rotationAnimatable.value + 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 4000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            )
                        )
                    }
                }
                animationState = -1
                sizeAnimatable.animateTo(
                    targetValue = 600f,
                    animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
                )

                // Pause phase
                animationState = 0
                rotationAnimatable.stop()
                delay(7000)

                // Shrinking phase with rotation
                animationState = 1
                scope.launch {
                    while (isAnimating) {
                        rotationAnimatable.animateTo(
                            targetValue = rotationAnimatable.value + 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 4000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            )
                        )
                    }
                }
                sizeAnimatable.animateTo(
                    targetValue = 400f,
                    animationSpec = tween(durationMillis = 8000, easing = LinearEasing)
                )

                // Idle state briefly before the loop repeats
                animationState = 6
                rotationAnimatable.stop()
                delay(3500)
            }
        }
    }

    BackHandler {
        visible.value = false // Set visibility to false when back is pressed
        navController.popBackStack()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        AnimatedVisibility(
            visible = visible.value,
            enter = slideInVertically(
                animationSpec = tween(durationMillis = 3000),
                initialOffsetY = { it + 800 },
            ) + scaleIn(
                animationSpec = tween(durationMillis = 3000),
                initialScale = 5f
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically(
                targetOffsetY = { it + 800 }
            ) + scaleOut(
                targetScale = 5f
            ) + fadeOut(
                targetAlpha = 0.3f
            )
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(200.dp)) {
                    rotate(rotationAnimatable.value) {
                        drawCircle(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color.Blue,
                                    Color.Cyan,
                                )
                            ),
                            radius = sizeAnimatable.value / 2, // Radius of the circle
                            center = center // Center position
                        )
                    }

                }
            }

        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            when (animationState) {
                1 -> {
                    Text(
                        text = "Breathe out",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )

                }

                0 -> {
                    Text(
                        text = "Hold...",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                (-1) -> {
                    Text(
                        text = "Breathe in",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                4 -> {
//                    speechInstructor.speak("Get Ready")
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Get Ready...",
                            style = TextStyle(
                                fontFamily = customFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Follow the instructions to achieve deep relaxation\nFocus on your breathing",
                            style = TextStyle(
                                fontFamily = customFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )

                    }

                }

                6 -> {
                    Text(
                        text = "Relax...",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )

                }
            }


        }

    }




}


@Composable
fun DisplayText() {
    while (true) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Get Ready...",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Follow the instructions to achieve deep relaxation\nFocus on your breathing",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )

        }

    }


}