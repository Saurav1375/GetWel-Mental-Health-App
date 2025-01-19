package com.example.getwell.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.getwell.ChatroomActivity
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Emoji
import com.example.getwell.data.Screen
import com.example.getwell.data.navItemList
import com.example.getwell.di.Injection
import com.example.getwell.domain.provider.AnxietyUIState
import com.example.getwell.domain.provider.DepressionUIState
import com.example.getwell.domain.provider.FactOftheDayProvider
import com.example.getwell.domain.provider.MoodProvider
import com.example.getwell.domain.provider.StigmaUIState
import com.example.getwell.domain.provider.StressUIState
import com.example.getwell.domain.provider.StressViewModel
import com.example.getwell.screens.relaxScreen.gamesection.dailyref.ReflectionRepository
import com.example.getwell.screens.relaxScreen.gamesection.dailyref.ReflectionViewModel
import com.example.getwell.screens.utils.SetBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.max
import kotlin.math.min


val customFont = FontFamily(
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_regular, FontWeight.Normal)
)

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    navController: NavHostController,
    userData: UserData?,
    isDASSQuizenabled: MutableState<Boolean>,
    isPSSQuizenabled: MutableState<Boolean>,
    display: MutableState<Boolean>,
    onPressed: () -> Unit,
    viewModel: StressViewModel,
//    refViewModel : ReflectionViewModel,
    onSignOutAccount: () -> Unit,
) {

    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val refViewModel = ReflectionViewModel(ReflectionRepository(Injection.instance(), userData))

    LaunchedEffect(true) {
        refViewModel.checkAndRestStreak()
    }


    BackHandler {
        if (backPressedOnce) {
            (context as? Activity)?.finishAffinity() // Close the app and remove it from recent apps
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    // Reset backPressedOnce after 2 seconds, only when it's true
    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            delay(2000)
            backPressedOnce = false
        }
    }

    // Trigger stress calculation
    if (userData != null) {
        viewModel.calculateStressForToday(userData.emailId)
        viewModel.calculateDepressionForToday(userData.emailId)
        viewModel.calculateAnxietyForToday(userData.emailId)
        viewModel.calculateStigmaForToday(userData.emailId)
    }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val showDialog = mutableStateOf(false)
    val factOftheDay by remember {
        mutableStateOf(FactOftheDayProvider.getFactOfTheDay(context))
    }


//    val userStreak by refViewModel.userStreak.collectAsState()




    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                list = navItemList,
                navController = navController,
                onNavClick = {
                    if (it.route == Screen.ChatroomScreen.route) {
                        val intent = Intent(context, ChatroomActivity::class.java)
                        context.startActivity(intent)

                    } else {
                        navController.navigate(it.route)
                    }
                }
            )
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SetBackground(id = R.drawable.home)

            val infiniteAnimation = rememberInfiniteTransition(label = "")
            val stressState by viewModel.stressState.collectAsState()
            val depressionState by viewModel.depressionState.collectAsState()
            val anxietyState by viewModel.anxietyState.collectAsState()
            val stigmaState by viewModel.stigmaState.collectAsState()

            val ratio by infiniteAnimation.animateValue(
                initialValue = 0.dp,
                targetValue = 600.dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 30000, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = " "
            )
            val ratio1 by infiniteAnimation.animateValue(
                initialValue = 0.dp,
                targetValue = 750.dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 35000, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = " "
            )
            Image(
                painter = painterResource(id = R.drawable.clouds),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(64, 18, 44)),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset((-350).dp + ratio1, (0).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.boat_),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(-ratio + 200.dp, (-50).dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Greeting(
                    name = userData?.username ?: "Guest",
                    userPic = userData?.profilePictureUrl,
                    showDialog = showDialog
                )
                AccountDialogue(
                    userData = userData,
                    onSignOutAccount = {
                        onSignOutAccount()
                        refViewModel.resetData() },
                    showDialog= showDialog
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 40.dp), contentAlignment = Alignment.Center
                ) {
                    EmojiBar(userData, display, onPressed = onPressed)

                }
                //fact of the day
                FactOfTheDay(fact = factOftheDay)
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (val state = stressState) {
                            is StressUIState.Loading -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Spacer(modifier = Modifier.width(300.dp))
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }

                            is StressUIState.Success -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    CircularProgressBar(
                                        pssScore = state.stressValues.pss.toInt(),
                                        dssScore = state.stressValues.dss.toInt(),
                                        title = "STRESS",
                                        totalProgress = (state.stressValues.stressValue.toFloat() / 100.0).toFloat() //if(finalStress(dassList, pssList, moodList, timeList) in 0.0..100.0 )(finalStress(dassList, pssList, moodList, timeList) / 100).toFloat() else 0.95f
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, start = 24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val color by remember{ mutableStateOf(Color.Green) }
                                    Text(
                                        text = when (state.stressValues.stressValue.toFloat()) {
                                            in 0.0..20.0 -> {"Low Stress"}
                                            in 21.00..60.00 -> "Moderate Stress"
                                            in 61.00..85.00 -> "Severe Stress"
                                            in 86.00..100.00 -> "Extreme Severe Stress"
                                            else -> ""
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        style = TextStyle(
                                            fontFamily = customFont,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 15.sp,
                                            color = when (state.stressValues.stressValue.toFloat()) {
                                                in 0.0..20.0 ->  Color.Cyan
                                                in 21.00..60.00 -> Color(205, 178, 37)
                                                in 61.00..85.00 -> Color(251, 29, 10)
                                                in 86.00..100.00 -> Color(255,63,37)
                                                else -> Color.White
                                            }
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, top = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        if(state.stressValues.dss.toInt() != -1){
                                            Text(
                                                text = "⦿ DASS-21(S): ${state.stressValues.dss.toInt()}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                        if(state.stressValues.pss.toInt() != -1){
                                            Text(
                                                text = "⦿ PSS: ${state.stressValues.pss.toInt()}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                        if(state.stressValues.face.toInt() != -1){
                                            Text(
                                                text = "⦿ Face: ${ String.format(
                                                "%.2f", state.stressValues.face)}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                        if(state.stressValues.speech.toInt() != -1){
                                            Text(
                                                text = "⦿ Speech: ${ String.format(
                                                    "%.2f", state.stressValues.speech)}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }

                                        if(state.stressValues.acad.toInt() != -1){
                                            Text(
                                                text = "⦿ Acad-Score: ${state.stressValues.acad}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                    }

                                }


                            }

                            is StressUIState.Error -> {
                                Text(
                                    text = state.message,
                                    color = Color(87,87,87),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        when (val state = depressionState) {
                            is DepressionUIState.Loading -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Spacer(modifier = Modifier.width(300.dp))
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }

                            is DepressionUIState.Success -> {
//                                println("FINAL: ${state.stressValues.stressValue.roundToInt()}")

                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    CircularProgressBar(
                                        pssScore = state.depressionValues.bdi.toInt(),
                                        dssScore = state.depressionValues.dssDep.toInt(),
                                        title = "DEPRESSION",
                                        totalProgress = (state.depressionValues.depressionValue.toFloat() / 100.0).toFloat(),
                                        barColors = listOf(
                                            Color(0xFF4CAF50), // Low: Green
                                            Color(0xFFFFEB3B), // Moderate: Yellow
                                            Color(0xFFFF9800), // Extreme: Orange
                                            Color(0xFFF44336)  // Severe: Red
                                        )
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 8.dp, start = 24.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val color by remember{ mutableStateOf(Color.Green) }

                                    Text(
                                        text = when (state.depressionValues.depressionValue.toFloat()) {
                                            in 0.0..20.0 -> {"Low Depression"}
                                            in 21.00..60.00 -> "Moderate Depression"
                                            in 61.00..85.00 -> "Severe Depression"
                                            in 86.00..100.00 -> "Extreme Severe Depression"
                                            else -> ""
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        style = TextStyle(
                                            fontFamily = customFont,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 15.sp,
                                            color = when (state.depressionValues.depressionValue.toFloat()) {
                                                in 0.0..20.0 ->  Color.Cyan
                                                in 21.00..60.00 -> Color(205, 178, 37)
                                                in 61.00..85.00 -> Color(251, 29, 10)
                                                in 86.00..100.00 -> Color(255,63,37)
                                                else -> Color.White
                                            }
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, top = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        if(state.depressionValues.dssDep.toInt() != -1){
                                            Text(
                                                text = "⦿ DASS-21(D): ${state.depressionValues.dssDep.toInt()}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                        if(state.depressionValues.bdi.toInt() != -1){
                                            Text(
                                                text = "⦿ BDI: ${state.depressionValues.bdi.toInt()}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }


                            }

                            is DepressionUIState.Error -> {
                                Text(
                                    text = state.message,
                                    color = Color(87,87,87),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        when (val state = anxietyState) {
                            is AnxietyUIState.Loading -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Spacer(modifier = Modifier.width(300.dp))
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }

                            is AnxietyUIState.Success -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    CircularProgressBar(
                                        pssScore = state.anxietyValues.bai.toInt(),
                                        dssScore = state.anxietyValues.dssAnx.toInt(),
                                        title = "ANXIETY",
                                        totalProgress = (state.anxietyValues.anxietyValue.toFloat() / 100.0).toFloat() ,
                                        barColors = listOf(
                                            Color(0xFFFFCDD2), // Low: Light Red (Soft Pinkish-Red)
                                            Color(0xFFEF9A9A), // Moderate: Soft Red
                                            Color(0xFFE57373), // Extreme: Vibrant Red
                                            Color(0xFFD32F2F)  // Severe: Dark Red
                                        )
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 8.dp, start = 24.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val color by remember{ mutableStateOf(Color.Green) }
                                    Text(
                                        text = when (state.anxietyValues.anxietyValue.toFloat()) {
                                            in 0.0..20.0 -> "Low Anxiety"
                                            in 21.00..60.00 -> "Moderate Anxiety"
                                            in 61.00..85.00 -> "Severe Anxiety"
                                            in 86.00..100.00 -> "Extreme Severe Anxiety"
                                            else -> ""
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        style = TextStyle(
                                            fontFamily = customFont,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 15.sp,
                                            color = when (state.anxietyValues.anxietyValue.toFloat()) {
                                                in 0.0..20.0 ->  Color.Cyan
                                                in 21.00..60.00 -> Color(205, 178, 37)
                                                in 61.00..85.00 -> Color(251, 29, 10)
                                                in 86.00..100.00 -> Color(255,63,37)
                                                else -> Color.White
                                            }
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, top = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        if(state.anxietyValues.dssAnx.toInt() != -1){
                                            Text(
                                                text = "⦿ DASS-21(A): ${state.anxietyValues.dssAnx.toInt()}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                        if(state.anxietyValues.bai.toInt() != -1){
                                            Text(
                                                text = "⦿ BAI: ${state.anxietyValues.bai.toInt()}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }


                            }

                            is AnxietyUIState.Error -> {
                                Text(
                                    text = state.message,
                                    color = Color(87,87,87),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        when (val state = stigmaState) {
                            is StigmaUIState.Loading -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Spacer(modifier = Modifier.width(300.dp))
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }

                            is StigmaUIState.Success -> {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    CircularProgressBar(
                                        pssScore = state.stigmaValues.ismi.toInt(),
                                        dssScore = state.stigmaValues.days.toInt(),
                                        title = "STIGMA",
                                        totalProgress = (state.stigmaValues.stigmaValue.toFloat() / 100.0).toFloat() ,
                                        barColors = listOf(
                                                Color(230, 230, 250), // Lavender (Region 1) or "#E6E6FA"
                                        Color(218, 112, 214), // Orchid (Region 2) or "#DA70D6"
                                        Color(147, 112, 219), // Medium Purple (Region 3) or "#9370DB"
                                        Color(153, 50, 204)   // Dark Orchid (Region 4) or "#9932CC"
                                    )
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 8.dp, start = 24.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val color by remember{ mutableStateOf(Color.Green) }
                                    Text(
                                        text = when (state.stigmaValues.stigmaValue.toFloat()) {
                                            in 0.0..20.0 -> "Low Stigma"
                                            in 21.00..60.00 -> "Moderate Stigma"
                                            in 61.00..85.00 -> "Severe Stigma"
                                            in 86.00..100.00 -> "Extreme Severe Stigma"
                                            else -> ""
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        style = TextStyle(
                                            fontFamily = customFont,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 15.sp,
                                            color = when (state.stigmaValues.stigmaValue.toFloat()) {
                                                in 0.0..20.0 ->  Color.Cyan
                                                in 21.00..60.00 -> Color(205, 178, 37)
                                                in 61.00..85.00 -> Color(251, 29, 10)
                                                in 86.00..100.00 -> Color(255,63,37)
                                                else -> Color.White
                                            }
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, top = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        if(state.stigmaValues.ismi.toInt() != -1){
                                            Text(
                                                text = "⦿ ISMI-9: ${state.stigmaValues.ismi.toInt()} / 4",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                        if(state.stigmaValues.days.toInt() != -1){
                                            Text(
                                                text = "⦿ Day's: ${state.stigmaValues.days.toInt()} / 196",
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = customFont,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }


                            }

                            is StigmaUIState.Error -> {
                                Text(
                                    text = state.message,
                                    color = Color(87,87,87),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                    }
                }





                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
                        .clickable { navController.navigate(Screen.AIStressScreen.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "mic",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
//                            .aspectRatio(1f)
                            .padding(start = 16.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.faceicon),
                        contentDescription = "face",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
//                            .aspectRatio(1f)
                            .padding(start = 16.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .animateContentSize()
                            .heightIn(min = 120.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "AI STRESS DETECTION",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontFamily = customFont,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(74, 151, 172)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Try Our AI Stress Detection using Face and Speech Analysis",
                                color = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                style = TextStyle(
                                    fontFamily = customFont,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color(74, 151, 172)
                                ),
                                textAlign = TextAlign.Center
                            )



                    }
                }
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Box(
                            modifier = Modifier
                                .width((this@BoxWithConstraints.maxWidth) / 2 - 8.dp)
                                .aspectRatio(1.1f)
                                .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(Screen.QuizBoardingScreen.route)
                                },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.question_mark),
                                contentDescription = "question_mark",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .aspectRatio(1f)
                                    .padding(start = 16.dp)
                            )

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = "Mental Tests",
                                    style = TextStyle(
                                        fontFamily = customFont,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
                                )
                                Spacer(modifier = Modifier.height(8.dp))


                                if(isDASSQuizenabled.value){
                                    Text(
                                        text = "Weekly Tests Available!",
                                        style = TextStyle(
                                            fontFamily = customFont,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 10.sp,
                                            color = Color(120,188,66),
                                        ),
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                if(isPSSQuizenabled.value){
                                    Text(
                                        text = "Monthly Tests Available!",
                                        style = TextStyle(
                                            fontFamily = customFont,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 10.sp,
                                            color = Color(120,188,66),
                                        ),
                                    )
                                }
                            }

                        }

                        Box(
                            modifier = Modifier
                                .width((this@BoxWithConstraints.maxWidth) / 2 - 8.dp)
                                .aspectRatio(1.1f)
                                .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(Screen.DailyRefScreen.route)
                                },
                        ) {
                            val userStreak by refViewModel.userStreak.collectAsState()
                            Column {
                                Text(
                                    text = "${userStreak.currentStreak} day",
                                    style = TextStyle(
                                        fontFamily = customFont,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 38.sp,
                                        color = Color.White
                                    ),
                                )
                                Text(
                                    text = "reflection Streak",
                                    style = TextStyle(
                                        fontFamily = customFont,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        color = Color(195, 84, 175)
                                    ),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
                        .clickable {
                            when(val temp = stressState){
                                is StressUIState.Success -> navController.navigate(Screen.TodaysPlanScreen.route)
                                is StressUIState.Error -> {
                                    Toast.makeText(
                                        context,
                                        "You have not measured your stress level to get personalized plan",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                else -> {}
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .animateContentSize()
                            .heightIn(min = 120.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "PERSONALIZED PLAN",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontFamily = customFont,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(74, 151, 172)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Get your Personalized daily plan based on your stress level",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                fontFamily = customFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color(74, 151, 172)
                            ),
                            textAlign = TextAlign.Center
                        )



                    }
                }
                Spacer(modifier = Modifier.height(100.dp))


            }

        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(
    name: String,
    userPic: String?,
    showDialog: MutableState<Boolean>
) {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()

        ) {
            Box(modifier = Modifier.fillMaxWidth(0.7f)){
                Text(
                    text = buildAnnotatedString {
                        append("Hi, ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(255, 132, 86)
                            )
                        ) {
                            append(name)
                        }
                    },
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp,
                        color = Color.White
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Text(
                text = "Welcome Back",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = Color.White
                )
            )
            Text(
                text = when (currentHour) {
                    in 0..11 -> "Good Morning"
                    in 12..16 -> "Good Afternoon"
                    else -> "Good Evening"
                },
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color = Color.Unspecified, shape = CircleShape)
                .clickable {
                    showDialog.value = true

                }
        ) {
            if (userPic != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userPic)
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = "profile picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,

                )

            } else {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = name,
                    contentScale = ContentScale.FillBounds
                )
            }

        }


    }


}

@Composable
fun FactOfTheDay(fact: String) {

    var showFact by remember { mutableStateOf(false) }

    // Trigger the fade-in animation when the Composable is first displayed
    LaunchedEffect(Unit) {
        delay(300)
        showFact = true //Set to true to trigger the fade-in animation
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .heightIn(min = 120.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Fact of The Day",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontFamily = customFont,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(74, 151, 172)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visible = showFact,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = fact,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(74, 151, 172)
                    ),
                    textAlign = TextAlign.Center
                )
            }


        }
    }

}


@Composable
fun EmojiBar(
    userData: UserData?,
    display: MutableState<Boolean>,
    onPressed: () -> Unit
) {

    val emojis = listOf(
        Emoji(5, icon = R.drawable.cry, "Depressed"),
        Emoji(4, icon = R.drawable.sad, "Sad"),
        Emoji(3, icon = R.drawable.neutral, "Okay"),
        Emoji(2, icon = R.drawable.happy, "Happy"),
        Emoji(1, icon = R.drawable.awesome, "Glad"),
        Emoji(0, icon = R.drawable.extremehappy, "Joyful"),
        )

    val moodProvider = MoodProvider(Injection.instance())
    val scope = rememberCoroutineScope()
    val backgroundColor = Color(161, 242, 255)
    var selectedEmoji: Emoji? by remember { mutableStateOf(null) }

    var isEnabled by remember {
        mutableStateOf(true)
    }

    if (display.value) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                val strokeWidth = 8f
                drawRoundRect(
                    color = backgroundColor,
                    size = androidx.compose.ui.geometry.Size(width = size.width, height = 125f),
                    cornerRadius = CornerRadius(x = 60f, y = 60f),
                    style = Stroke(width = strokeWidth)
                )
            }
            Text(
                modifier = Modifier
                    .background(Color(33, 16, 30))
                    .align(Alignment.TopCenter),
                text = "How are you feeling right now?",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.height(90.dp))

            // Emojis Row in the middle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 29.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                emojis.forEach { emoji ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(45.dp)
                                .background(backgroundColor, shape = CircleShape)
                                .padding(top = 10.dp)
                                .clickable(
                                    enabled = isEnabled,
                                    indication = null, // Disable the ripple effect
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    emoji.selected = true
                                    selectedEmoji = emoji
                                    if (userData != null) {
                                        scope.launch {
                                            moodProvider.saveMood(userData.emailId, selectedEmoji!!.id)

                                        }
                                    }

                                    scope.launch {
                                        isEnabled = false
                                        delay(1000)
                                        onPressed()
                                    }
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = emoji.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(45.dp)
                                    .align(Alignment.Center),
                                tint = if (emoji.id == selectedEmoji?.id) Color(
                                    255,
                                    132,
                                    86
                                ) else Color.Unspecified
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = emoji.description,
                            color = Color(161, 242, 255),
                            fontSize = 10.sp,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                        )
                    }

                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Thanks for your Response",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontFamily = customFont,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(74, 151, 172)
                    )
                )

            }
        }
    }
}


@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    pssScore: Int = 0,
    dssScore: Int = 0,
    title : String = "STRESS",
    totalProgress: Float = 0.9f,
    fullProgress1: Float = 0.20f,
    fullProgress2: Float = 0.40f,
    fullProgress3: Float = 0.25f,
    fullProgress4: Float = 0.15f,
    animationDuration: Int = 1500,
    gapAngle: Float = 4f,
    radius: Dp = 60.dp,
    strokeWidth: Float = 45f,
    fontSize: TextUnit = 28.sp,
    animationDelay: Int = 500,
    color: Color = Color(31, 31, 37),
    barColors : List<Color> = listOf(Color(194, 148, 237), Color(74, 151, 172), Color(80, 124, 200), Color(11, 78, 160))
) {

    var isAnimate by remember {
        mutableStateOf(false)
    }
    val animatedTotalProgress by animateFloatAsState(
        targetValue = if (isAnimate) totalProgress else 0f,
        animationSpec = tween(durationMillis = animationDuration, delayMillis = animationDelay),
        label = ""
    )

    val progress1 = min(animatedTotalProgress, fullProgress1)
    val progress2 = if (animatedTotalProgress > fullProgress1) {
        min(animatedTotalProgress - fullProgress1, fullProgress2)
    } else 0f
    val progress3 = if (animatedTotalProgress > fullProgress1 + fullProgress2) {
        min(animatedTotalProgress - fullProgress1 - fullProgress2, fullProgress3)
    } else 0f
    val progress4 = if (animatedTotalProgress > fullProgress1 + fullProgress2 + fullProgress3) {
        min(animatedTotalProgress - fullProgress1 - fullProgress2 - fullProgress3, fullProgress4)
    } else 0f


    LaunchedEffect(key1 = true) {
        isAnimate = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {


            drawArc(
                color = barColors[0], // Pink
                startAngle = -90f,
                sweepAngle = max(0f, progress1 * 360 - gapAngle),
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )


            drawArc(
                color = color,
                startAngle = -90f + progress1 * 360 - gapAngle,
                sweepAngle = gapAngle,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )

            drawArc(
                color = barColors[1],
                startAngle = -90f + progress1 * 360,
                sweepAngle = max(0f, progress2 * 360 - gapAngle),
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )

            drawArc(
                color = color,
                startAngle = -90f + (progress1 + progress2) * 360 - gapAngle,
                sweepAngle = gapAngle,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )

            drawArc(
                color = barColors[2],
                startAngle = -90f + (progress1 + progress2) * 360,
                sweepAngle = max(0f, progress3 * 360 - gapAngle),
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )


            drawArc(
                color = color,
                startAngle = -90f + (progress1 + progress2 + progress3) * 360 - gapAngle,
                sweepAngle = gapAngle,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )

            drawArc(
                color = barColors[3],
                startAngle = -90f + (progress1 + progress2 + progress3) * 360,
                sweepAngle = max(0f, progress4 * 360 - gapAngle),
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Butt)
            )
        }


        Column(
            modifier = Modifier.padding(top = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = customFont,
                    fontSize = 14.sp,
                    color = Color.White
                )

//
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${String.format("%.2f", animatedTotalProgress * 100)}%",
                    fontWeight = FontWeight.Bold,
                    fontFamily = customFont,
                    fontSize = 18.sp,
                    color = Color(0xFF00FFB2)
                )
            }
        }
    }
}


@Composable
fun AccountDialogue(
    userData: UserData?,
    onSignOutAccount: () -> Unit,
    showDialog: MutableState<Boolean>
) {
//    var showDialog by mutableStateOf(false)

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier
                    .size(340.dp, 320.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(Color(40, 42, 44)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(85.dp)
                            .background(color = Color.Unspecified, shape = CircleShape)
                            .clipToBounds()

                    ) {
                        if (userData?.profilePictureUrl != null) {
                            AsyncImage(
                                model = userData.profilePictureUrl,
                                contentDescription = "profile picture",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop

                            )

                        } else {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = R.drawable.profile_icon),
                                contentDescription = "profile picture",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth(0.75f), contentAlignment = Alignment.Center){
                        if (userData != null) {
                            userData.username?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        fontFamily = customFont,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(4.dp))

                    if (userData != null) {
                        Text(
                            text = userData.emailId,
                            style = TextStyle(
                                fontFamily = customFont,
                                color = Color(135, 135, 135),
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(50.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                            .border(
                                BorderStroke(1.dp, Color.White),
                                shape = RoundedCornerShape(10.dp)
                            )

                            .clickable {
                                onSignOutAccount()

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sign Out",
                            style = TextStyle(
                                fontFamily = customFont,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp
                            )
                        )

                    }


                }
            }

        }

    }


}




