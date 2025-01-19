package com.example.getwell.screens.profilepage


import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.getwell.ChatroomActivity
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.data.navItemList
import com.example.getwell.domain.provider.AnxietyUIState
import com.example.getwell.domain.provider.DepressionUIState
import com.example.getwell.domain.provider.StigmaUIState
import com.example.getwell.domain.provider.StressUIState
import com.example.getwell.domain.provider.StressViewModel
import com.example.getwell.screens.BottomNavigationBar
import com.example.getwell.screens.CircularProgressBar
import com.example.getwell.screens.customFont
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    userData: UserData?,
    viewModel: StressViewModel,
    onProfileClick: () -> Unit
) {
    BackHandler {
        navController.navigate(Screen.HomeScreen.route)
    }
    if (userData != null) {
        viewModel.calculateStressForToday(userData.emailId)
    }

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current


    var totalMinutesThisWeek by remember { mutableStateOf(0.0) }
    var sessionsThisWeek by remember { mutableStateOf(0) }
    var dailyStats by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    var isLoading by remember { mutableStateOf(true) }

    // Get current week range
    val calendar = Calendar.getInstance()
    val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
    val year = calendar.get(Calendar.YEAR)

    // Calculate week date range
    val weekRange = remember {
        calendar.apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }.time.let { startDate ->
            calendar.apply {
                add(Calendar.DAY_OF_WEEK, 6)
            }.time.let { endDate ->
                SimpleDateFormat("MMM dd", Locale.getDefault()).let { formatter ->
                    "${formatter.format(startDate)} - ${formatter.format(endDate)}"
                }
            }
        }
    }

    // Effect to fetch stats when screen loads
    LaunchedEffect(userData) {
        userData?.emailId?.let { userId ->
            val db = FirebaseFirestore.getInstance()
            val meditationRef = db.collection("meditation_sessions")
                .document(userId)
                .collection("weekly_stats")
                .document("$year-W$weekOfYear")

            meditationRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(
                        context,
                        "Error loading stats: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    isLoading = false
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    totalMinutesThisWeek = snapshot.getDouble("totalMinutes") ?: 0.0
                    sessionsThisWeek = snapshot.getLong("sessionCount")?.toInt() ?: 0

                    // Get daily breakdown if exists
                    @Suppress("UNCHECKED_CAST")
                    val dailyData =
                        snapshot.get("dailyBreakdown") as? Map<String, Double> ?: emptyMap()
                    dailyStats = dailyData
                }
                isLoading = false
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,

        bottomBar = {
            BottomNavigationBar(
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
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .verticalScroll(scrollState),

                ) {
                UserSection(
                    name = userData?.username ?: "Guest",
                    userPic = userData?.profilePictureUrl,
                    onProfilePicCLick = onProfileClick,
                    onEditProfileCLick = {}) {
                    navController.navigate(Screen.SettingsScreen.route)

                }
                Spacer(modifier = Modifier.height(48.dp))

                StressCircularBar(viewModel)
                Spacer(modifier = Modifier.height(32.dp))

                var selectedPeriod by remember { mutableStateOf(ChartPeriod.WEEK) }
                var isDropdownExpanded by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    // Animated chart with dropdown on the top right corner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31, 31, 37), RoundedCornerShape(20.dp))
                            .heightIn(min = 210.dp) // Set a fixed height for the chart container
                    ) {
                        // Dropdown menu icon
                        IconButton(
                            onClick = { isDropdownExpanded = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                            // Padding for spacing from the edge
                        ) {
                            Icon(
                                painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.mtrl_ic_arrow_drop_down), // Replace with actual icon
                                contentDescription = "Select Period",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White// Smaller icon size
                            )
                        }

                        // Dropdown menu for selecting period
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            offset = DpOffset(180.dp, 100.dp),
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriod = ChartPeriod.WEEK
                                    isDropdownExpanded = false
                                },
                                text = { Text("Weekly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriod = ChartPeriod.MONTH
                                    isDropdownExpanded = false
                                },
                                text = { Text("Monthly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriod = ChartPeriod.THREE_MONTHS
                                    isDropdownExpanded = false
                                },
                                text = { Text("3-Months") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriod = ChartPeriod.SIX_MONTHS
                                    isDropdownExpanded = false
                                },
                                text = { Text("6-Months") }
                            )

                        }

                        // MoodChart displayed under the dropdown
                        if (userData != null) {
                            MoodChart(
                                userId = userData.emailId,
                                period = selectedPeriod,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 8.dp)

                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                var selectedPeriodQuiz by remember { mutableStateOf(ChartPeriod.WEEK) }
                var isDropdownExpandedQuiz by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    // Animated chart with dropdown on the top right corner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31, 31, 37), RoundedCornerShape(20.dp))
                            .heightIn(min = 210.dp) // Set a fixed height for the chart container
                    ) {
                        // Dropdown menu icon
                        IconButton(
                            onClick = { isDropdownExpandedQuiz = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                            // Padding for spacing from the edge
                        ) {
                            Icon(
                                painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.mtrl_ic_arrow_drop_down), // Replace with actual icon
                                contentDescription = "Select Period",
                                modifier = Modifier.size(24.dp),// Smaller icon size
                                tint = Color.White
                            )
                        }

                        // Dropdown menu for selecting period
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            offset = DpOffset(180.dp, 100.dp),
                            expanded = isDropdownExpandedQuiz,
                            onDismissRequest = { isDropdownExpandedQuiz = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuiz = ChartPeriod.WEEK
                                    isDropdownExpandedQuiz = false
                                },
                                text = { Text("Weekly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuiz = ChartPeriod.MONTH
                                    isDropdownExpandedQuiz = false
                                },
                                text = { Text("Monthly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuiz = ChartPeriod.THREE_MONTHS
                                    isDropdownExpandedQuiz = false
                                },
                                text = { Text("3-Months") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuiz = ChartPeriod.SIX_MONTHS
                                    isDropdownExpandedQuiz = false
                                },
                                text = { Text("6-Months") }
                            )
                        }

                        // MoodChart displayed under the dropdown
                        if (userData != null) {
                            QuizChart(
                                id = "DASS21",
                                userId = userData.emailId,
                                period = selectedPeriodQuiz,
                                graphColor = Color(238, 12, 94),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 8.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))


                var selectedPeriodQuizPss by remember { mutableStateOf(ChartPeriod.WEEK) }
                var isDropdownExpandedQuizPss by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    // Animated chart with dropdown on the top right corner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31, 31, 37), RoundedCornerShape(20.dp))
                            .heightIn(min = 210.dp) // Set a fixed height for the chart container
                    ) {
                        // Dropdown menu icon
                        IconButton(
                            onClick = { isDropdownExpandedQuizPss = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                            // Padding for spacing from the edge
                        ) {
                            Icon(
                                painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.mtrl_ic_arrow_drop_down), // Replace with actual icon
                                contentDescription = "Select Period",
                                modifier = Modifier.size(24.dp), // Smaller icon size
                                tint = Color.White
                            )
                        }

                        // Dropdown menu for selecting period
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            offset = DpOffset(180.dp, 100.dp),
                            expanded = isDropdownExpandedQuizPss,
                            onDismissRequest = { isDropdownExpandedQuizPss = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizPss = ChartPeriod.WEEK
                                    isDropdownExpandedQuizPss = false
                                },
                                text = { Text("Weekly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizPss = ChartPeriod.MONTH
                                    isDropdownExpandedQuizPss = false
                                },
                                text = { Text("Monthly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizPss = ChartPeriod.THREE_MONTHS
                                    isDropdownExpandedQuizPss = false
                                },
                                text = { Text("3-Months") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizPss = ChartPeriod.SIX_MONTHS
                                    isDropdownExpandedQuizPss = false
                                },
                                text = { Text("6-Months") }
                            )
                        }

                        // MoodChart displayed under the dropdown
                        if (userData != null) {
                            QuizChart(
                                id = "PSS",
                                gridLines = listOf(0, 14, 28, 42),
                                userId = userData.emailId,
                                period = selectedPeriodQuizPss,
                                graphColor = Color(12, 238, 21),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 8.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                var selectedPeriodQuizBdi by remember { mutableStateOf(ChartPeriod.WEEK) }
                var isDropdownExpandedQuizBdi by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    // Animated chart with dropdown on the top right corner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31, 31, 37), RoundedCornerShape(20.dp))
                            .heightIn(min = 210.dp) // Set a fixed height for the chart container
                    ) {
                        // Dropdown menu icon
                        IconButton(
                            onClick = { isDropdownExpandedQuizBdi = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                            // Padding for spacing from the edge
                        ) {
                            Icon(
                                painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.mtrl_ic_arrow_drop_down), // Replace with actual icon
                                contentDescription = "Select Period",
                                modifier = Modifier.size(24.dp), // Smaller icon size
                                tint = Color.White
                            )
                        }

                        // Dropdown menu for selecting period
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            offset = DpOffset(180.dp, 100.dp),
                            expanded = isDropdownExpandedQuizBdi,
                            onDismissRequest = { isDropdownExpandedQuizBdi = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBdi = ChartPeriod.WEEK
                                    isDropdownExpandedQuizBdi = false
                                },
                                text = { Text("Weekly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBdi = ChartPeriod.MONTH
                                    isDropdownExpandedQuizBdi = false
                                },
                                text = { Text("Monthly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBdi = ChartPeriod.THREE_MONTHS
                                    isDropdownExpandedQuizBdi = false
                                },
                                text = { Text("3-Months") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBdi = ChartPeriod.SIX_MONTHS
                                    isDropdownExpandedQuizBdi = false
                                },
                                text = { Text("6-Months") }
                            )
                        }

                        // MoodChart displayed under the dropdown
                        if (userData != null) {
                            QuizChart(
                                id = "BDI",
                                gridLines = listOf(0, 16, 32, 47, 63),
                                userId = userData.emailId,
                                period = selectedPeriodQuizBdi,
                                graphColor = Color(12, 238, 21),
                                maxScore  = 63.0,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 8.dp),
                            )
                        }
                    }
                }



                Spacer(modifier = Modifier.height(16.dp))

                var selectedPeriodQuizBai by remember { mutableStateOf(ChartPeriod.WEEK) }
                var isDropdownExpandedQuizBai by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    // Animated chart with dropdown on the top right corner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31, 31, 37), RoundedCornerShape(20.dp))
                            .heightIn(min = 210.dp) // Set a fixed height for the chart container
                    ) {
                        // Dropdown menu icon
                        IconButton(
                            onClick = { isDropdownExpandedQuizBai = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                            // Padding for spacing from the edge
                        ) {
                            Icon(
                                painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.mtrl_ic_arrow_drop_down), // Replace with actual icon
                                contentDescription = "Select Period",
                                modifier = Modifier.size(24.dp), // Smaller icon size
                                tint = Color.White
                            )
                        }

                        // Dropdown menu for selecting period
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            offset = DpOffset(180.dp, 100.dp),
                            expanded = isDropdownExpandedQuizBai,
                            onDismissRequest = { isDropdownExpandedQuizBai = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBai = ChartPeriod.WEEK
                                    isDropdownExpandedQuizBai = false
                                },
                                text = { Text("Weekly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBai = ChartPeriod.MONTH
                                    isDropdownExpandedQuizBai = false
                                },
                                text = { Text("Monthly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBai = ChartPeriod.THREE_MONTHS
                                    isDropdownExpandedQuizBai = false
                                },
                                text = { Text("3-Months") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizBai = ChartPeriod.SIX_MONTHS
                                    isDropdownExpandedQuizBai = false
                                },
                                text = { Text("6-Months") }
                            )
                        }

                        // MoodChart displayed under the dropdown
                        if (userData != null) {
                            QuizChart(
                                id = "BAI",
                                gridLines = listOf(0, 16, 32, 47, 63),
                                userId = userData.emailId,
                                period = selectedPeriodQuizBai,
                                graphColor = Color(12, 238, 21),
                                maxScore = 63.0,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 8.dp),
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                var selectedPeriodQuizAss by remember { mutableStateOf(ChartPeriod.WEEK) }
                var isDropdownExpandedQuizAss by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    // Animated chart with dropdown on the top right corner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(31, 31, 37), RoundedCornerShape(20.dp))
                            .heightIn(min = 210.dp) // Set a fixed height for the chart container
                    ) {
                        // Dropdown menu icon
                        IconButton(
                            onClick = { isDropdownExpandedQuizAss = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                            // Padding for spacing from the edge
                        ) {
                            Icon(
                                painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.mtrl_ic_arrow_drop_down), // Replace with actual icon
                                contentDescription = "Select Period",
                                modifier = Modifier.size(24.dp), // Smaller icon size
                                tint = Color.White
                            )
                        }

                        // Dropdown menu for selecting period
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            offset = DpOffset(180.dp, 100.dp),
                            expanded = isDropdownExpandedQuizAss,
                            onDismissRequest = { isDropdownExpandedQuizAss = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizAss = ChartPeriod.WEEK
                                    isDropdownExpandedQuizAss = false
                                },
                                text = { Text("Weekly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizAss = ChartPeriod.MONTH
                                    isDropdownExpandedQuizAss = false
                                },
                                text = { Text("Monthly") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizAss = ChartPeriod.THREE_MONTHS
                                    isDropdownExpandedQuizAss = false
                                },
                                text = { Text("3-Months") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedPeriodQuizAss = ChartPeriod.SIX_MONTHS
                                    isDropdownExpandedQuizAss = false
                                },
                                text = { Text("6-Months") }
                            )
                        }

                        // MoodChart displayed under the dropdown
                        if (userData != null) {
                            QuizChart(
                                id = "Acad-Stress",
                                gridLines = listOf(0, 1, 2, 3, 4),
                                maxScore = 4.0,
                                userId = userData.emailId,
                                period = selectedPeriodQuizAss,
                                graphColor = Color(12, 238, 21),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 8.dp),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


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
                                .padding(2.dp)
                                .clickable {
                                    navController.navigate(Screen.MeditationStatsScreen.route)
                                },
                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier.size(50.dp),
                                    imageVector = Icons.Default.QueryStats,
                                    contentDescription = null,
                                    tint = Color(13, 210, 254)
                                )
                                Text(
                                    text = "Meditation Stats",
                                    style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontFamily = customFont,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 19.sp,
                                        color = Color.White
                                    ),
                                )

                            }


                        }

                        Box(
                            modifier = Modifier
                                .width((this@BoxWithConstraints.maxWidth) / 2 - 8.dp)
                                .aspectRatio(1.1f)
                                .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
                                .padding(2.dp)
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color(13, 210, 254),
                                                fontFamily = customFont,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 48.sp
                                            )
                                        ) {
                                            append(String.format("%.1f", totalMinutesThisWeek))
                                        }
                                        append(" min")
                                    },
                                    style = TextStyle(
                                        fontFamily = customFont,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 24.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
                                )

                                Text(
                                    text = "of Meditation this Week",
                                    style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontFamily = customFont,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    ),
                                )

                            }


                        }


                    }


                }
                Spacer(modifier = Modifier.height(100.dp))

            }
        }
    }
}


@Composable
fun UserSection(
    name: String,
    userPic: String?,
    onEditProfileCLick: () -> Unit,
    onProfilePicCLick: () -> Unit,
    onSettingsClick: () -> Unit

) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotateAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing)), label = ""
    )


    val density = LocalDensity.current
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally {
                    // Slide in from 40 dp from the top.
                    with(density) {
                        -80.dp.roundToPx()
                    }
                } + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = Color.Unspecified, shape = CircleShape)
                        .clickable { onProfilePicCLick() }
                ) {
                    if (userPic != null) {
                        AsyncImage(
                            model = userPic,
                            contentDescription = "profile  picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .drawBehind {
                                    rotate(rotateAnimation.value) {
                                        drawCircle(
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    Color.Red,
                                                    Color(255, 165, 0), // Orange
                                                    Color.Yellow,
                                                    Color.Green,
                                                    Color(255, 192, 203),
                                                    Color(75, 0, 130), // Indigo
                                                    Color(127, 0, 255)
                                                )
                                            ),
                                            style = Stroke(3.dp.toPx())
                                        )
                                    }

                                }
                                .padding(3.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop

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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically {
                        with(density) { -70.dp.roundToPx() }
                    } + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.75f)
                    ) {
                        Text(
                            text = name,
                            style = TextStyle(
                                fontFamily = customFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 22.sp,
                                color = Color(255, 132, 86),

                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                    }
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = slideInHorizontally(
                        // Slide in from 40 dp from the right.
                        initialOffsetX = { with(density) { 40.dp.roundToPx() } }
                    ) + fadeIn(
                        // Fade in with the initial alpha of 0.3f.
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    IconButton(onClick = { onSettingsClick() }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.settings_icon),
                            contentDescription = "settings",
                            tint = Color.Unspecified
                        )
                    }
                }


            }

        }

    }

}


@Composable
fun StressCircularBar(viewModel: StressViewModel) {
    val stressState by viewModel.stressState.collectAsState()
    val depressionState by viewModel.depressionState.collectAsState()
    val anxietyState by viewModel.anxietyState.collectAsState()
    val stigmaState by viewModel.stigmaState.collectAsState()
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
//                                println("FINAL: ${state.stressValues.stressValue.roundToInt()}")

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
//                                    Text(
//                                        text = "Avg Stress level : ${
//                                            String.format(
//                                                "%.2f",
//                                                state.stressValues.stressValue.toFloat()
//                                            )
//                                        }%",
//                                        color = Color.White,
//                                        modifier = Modifier.align(Alignment.CenterHorizontally),
//                                        style = TextStyle(
//                                            fontFamily = customFont,
//                                            fontWeight = FontWeight.SemiBold,
//                                            fontSize = 15.sp,
//                                            color = Color(74, 151, 172)
//                                        ),
//                                        textAlign = TextAlign.Center
//                                    )
                        androidx.compose.material.Text(
                            text = when (state.stressValues.stressValue.toFloat()) {
                                in 0.0..20.0 -> {
                                    "Low Stress"
                                }

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
                                    in 0.0..20.0 -> Color.Cyan
                                    in 21.00..60.00 -> Color(205, 178, 37)
                                    in 61.00..85.00 -> Color(251, 29, 10)
                                    in 86.00..100.00 -> Color(255, 63, 37)
                                    else -> Color.White
                                }
                            ),
                            textAlign = TextAlign.Center
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if(state.stressValues.dss.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ DASS-21(S): ${state.stressValues.dss.toInt()}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if(state.stressValues.pss.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ PSS: ${state.stressValues.pss.toInt()}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if(state.stressValues.face.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ Face: ${
                                        String.format(
                                            "%.2f", state.stressValues.face
                                        )
                                    }",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if(state.stressValues.speech.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ Speech: ${
                                        String.format(
                                            "%.2f", state.stressValues.speech
                                        )
                                    }",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }

                            if(state.stressValues.acad.toInt() != -1){
                                androidx.compose.material.Text(
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
                    androidx.compose.material.Text(
                        text = state.message,
                        color = Color(87, 87, 87),
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

                        androidx.compose.material.Text(
                            text = when (state.depressionValues.depressionValue.toFloat()) {
                                in 0.0..20.0 -> {
                                    "Low Depression"
                                }

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
                                    in 0.0..20.0 -> Color.Cyan
                                    in 21.00..60.00 -> Color(205, 178, 37)
                                    in 61.00..85.00 -> Color(251, 29, 10)
                                    in 86.00..100.00 -> Color(255, 63, 37)
                                    else -> Color.White
                                }
                            ),
                            textAlign = TextAlign.Center
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if(state.depressionValues.dssDep.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ DASS-21(D): ${state.depressionValues.dssDep.toInt()}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if(state.depressionValues.bdi.toInt() != -1){
                                androidx.compose.material.Text(
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
                    androidx.compose.material.Text(
                        text = state.message,
                        color = Color(87, 87, 87),
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
                        androidx.compose.material.Text(
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
                                    in 0.0..20.0 -> Color.Cyan
                                    in 21.00..60.00 -> Color(205, 178, 37)
                                    in 61.00..85.00 -> Color(251, 29, 10)
                                    in 86.00..100.00 -> Color(255, 63, 37)
                                    else -> Color.White
                                }
                            ),
                            textAlign = TextAlign.Center
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if(state.anxietyValues.dssAnx.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ DASS-21(A): ${state.anxietyValues.dssAnx.toInt()}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if(state.anxietyValues.bai.toInt() != -1){
                                androidx.compose.material.Text(
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
                    androidx.compose.material.Text(
                        text = state.message,
                        color = Color(87, 87, 87),
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
//                                    Text(
//                                        text = "Avg Stress level : ${
//                                            String.format(
//                                                "%.2f",
//                                                state.anxietyValues.anxietyValue.toFloat()
//                                            )
//                                        }%",
//                                        color = Color.White,
//                                        modifier = Modifier.align(Alignment.CenterHorizontally),
//                                        style = TextStyle(
//                                            fontFamily = customFont,
//                                            fontWeight = FontWeight.SemiBold,
//                                            fontSize = 15.sp,
//                                            color = Color(74, 151, 172)
//                                        ),
//                                        textAlign = TextAlign.Center
//                                    )
                        androidx.compose.material.Text(
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
                                    in 0.0..20.0 -> Color.Cyan
                                    in 21.00..60.00 -> Color(205, 178, 37)
                                    in 61.00..85.00 -> Color(251, 29, 10)
                                    in 86.00..100.00 -> Color(255, 63, 37)
                                    else -> Color.White
                                }
                            ),
                            textAlign = TextAlign.Center
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if(state.stigmaValues.ismi.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ ISMI-9: ${state.stigmaValues.ismi.toInt()}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = customFont,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if(state.stigmaValues.days.toInt() != -1){
                                androidx.compose.material.Text(
                                    text = "⦿ Day's: ${state.stigmaValues.days.toInt()}",
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
                    androidx.compose.material.Text(
                        text = state.message,
                        color = Color(87, 87, 87),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }

}





