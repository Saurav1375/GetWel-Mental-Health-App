package com.example.getwell.screens.profilepage

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.getwell.authSystem.UserData
import com.example.getwell.screens.customFont
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MeditationStatsScreen(
    navController: NavHostController,
    userData: UserData?
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    // State for stats
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
                    Toast.makeText(context, "Error loading stats: ${error.message}", Toast.LENGTH_SHORT).show()
                    isLoading = false
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    totalMinutesThisWeek = snapshot.getDouble("totalMinutes") ?: 0.0
                    sessionsThisWeek = snapshot.getLong("sessionCount")?.toInt() ?: 0

                    // Get daily breakdown if exists
                    @Suppress("UNCHECKED_CAST")
                    val dailyData = snapshot.get("dailyBreakdown") as? Map<String, Double> ?: emptyMap()
                    dailyStats = dailyData
                }
                isLoading = false
            }
        }
    }

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
                    text = "Meditation Stats",
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
                .background(Color(127, 132, 190))
                .padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Week range header
                    Text(
                        text = "Week of $weekRange",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    // Stats cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Total time card
                        StatCard(
                            modifier = Modifier.weight(1f),
                            title = "Total Time",
                            value = "${String.format("%.1f", totalMinutesThisWeek)} min",
                            icon = Icons.Default.Timer
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Sessions card
                        StatCard(
                            modifier = Modifier.weight(1f),
                            title = "Sessions",
                            value = sessionsThisWeek.toString(),
                            icon = Icons.Default.Check
                        )
                    }

                    // Weekly progress chart
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        backgroundColor = Color.White.copy(alpha = 0.9f),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Weekly Progress",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(150,150,150)
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Bar chart using Compose
                            WeeklyProgressChart(
                                dailyStats = dailyStats,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                    }

                    // Achievement card
                    if (totalMinutesThisWeek > 0) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color(0xFF4CAF50).copy(alpha = 0.9f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Achievement",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = getAchievementMessage(totalMinutesThisWeek),
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        backgroundColor = Color.White.copy(alpha = 0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(127, 132, 190),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(127, 132, 190)
                )
            )
        }
    }
}

@Composable
private fun WeeklyProgressChart(
    dailyStats: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    // Define the days in order
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    // Convert abbreviated day names to match Firebase data
    val dayMapping = mapOf(
        "Mon" to "Mon",
        "Tue" to "Tue",
        "Wed" to "Wed",
        "Thu" to "Thu",
        "Fri" to "Fri",
        "Sat" to "Sat",
        "Sun" to "Sun"
    )

    // Get the maximum value for scaling
    val maxMinutes = dailyStats.values.maxOrNull()?.coerceAtLeast(0.0) ?: 1.0

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Y-axis labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
//                ${String.format("%.1f", maxMinutes)}
                text = " ${String.format("%.1f", maxMinutes)} min",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        // Bar chart
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            daysOfWeek.forEach { day ->
                val mappedDay = dayMapping[day] ?: day
                val minutes = dailyStats[mappedDay] ?: 0.0
                val heightPercent = (minutes / maxMinutes).coerceIn(0.0, 1.0)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    // Show value above bar
                    if (minutes > 0) {
                        Text(
                            text = " ${String.format("%.1f", maxMinutes)}",
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    // Bar
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .fillMaxHeight(heightPercent.toFloat())
                            .background(
                                if (minutes > 0) {
                                    Color(127, 132, 190)
                                } else {
                                    Color(127, 132, 190).copy(alpha = 0.3f)
                                },
                                RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                            )
                    ) {
                        if (minutes == 0.0) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .align(Alignment.BottomCenter)
                                    .background(Color(127, 132, 190).copy(alpha = 0.3f))
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // X-axis labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                val isToday = SimpleDateFormat("EEE", Locale.getDefault())
                    .format(Calendar.getInstance().time) == day

                Text(
                    text = day,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = if (isToday) Color(127, 132, 190) else Color.Gray,
                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                    ),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
private fun getAchievementMessage(minutes: Double): String {
    return when {
        minutes >= 60 -> "Amazing! You've meditated for over an hour this week!"
        minutes >= 30 -> "Great progress! Keep up the good work!"
        else -> "You've started your meditation journey!"
    }
}