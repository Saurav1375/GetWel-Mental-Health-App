package com.example.getwell.screens.relaxScreen.meditation


import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.screens.customFont
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun MeditationMainSCreen(
    navController: NavHostController,
    userData: UserData?,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    // State for meditation timer
    var selectedTime by remember { mutableStateOf(0) } // in minutes
    var isTimerRunning by remember { mutableStateOf(false) }
    var isTimerPaused by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(0L) } // in milliseconds
    var initialTime by remember { mutableStateOf(0L) } // to track original selected time
    var countDownTimer by remember { mutableStateOf<CountDownTimer?>(null) }

    // Time options for meditation
    val timeOptions = listOf(1,2, 5, 10)

    // Function to save meditation session to Firebase
    fun saveMeditationSession(totalMinutesSpent: Double) {
        val db = FirebaseFirestore.getInstance()
        val calendar = Calendar.getInstance()
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)
        val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault())
            .format(Calendar.getInstance().time)

        userData?.emailId?.let { userId ->
            val meditationRef = db.collection("meditation_sessions")
                .document(userId)
                .collection("weekly_stats")
                .document("$year-W$weekOfYear")

            db.runTransaction { transaction ->
                val snapshot = transaction.get(meditationRef)
                val currentMinutes = snapshot.getDouble("totalMinutes") ?: 0.0
                val sessionCount = snapshot.getLong("sessionCount")?.toInt() ?: 0

                // Create dailyBreakdown map
                @Suppress("UNCHECKED_CAST")
                val currentDailyBreakdown = snapshot.get("dailyBreakdown") as? Map<String, Double> ?: emptyMap()
                val updatedDailyMinutes = currentDailyBreakdown.getOrDefault(dayOfWeek, 0.0) + totalMinutesSpent

                val updatedDailyBreakdown = currentDailyBreakdown.toMutableMap().apply {
                    put(dayOfWeek, updatedDailyMinutes)
                }

                val updateData = hashMapOf(
                    "totalMinutes" to (currentMinutes + totalMinutesSpent),
                    "sessionCount" to (sessionCount + 1),
                    "lastUpdated" to FieldValue.serverTimestamp(),
                    "dailyBreakdown" to updatedDailyBreakdown
                )

                transaction.set(meditationRef, updateData, SetOptions.merge())
            }.addOnSuccessListener {
                Toast.makeText(context, "Session saved successfully!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Toast.makeText(context, "Failed to save session: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to start/resume timer
    fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                // Play completion sound
                val mediaPlayer = MediaPlayer.create(context, R.raw.stop_meditating)
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { it.release() }
                isTimerRunning = false
                isTimerPaused = false


                // Save completed session
                val totalMinutes = initialTime / (60.0 * 1000.0)
                saveMeditationSession(totalMinutes)

                // Reset timer
                selectedTime = 0
                remainingTime = 0
                initialTime = 0
            }
        }.start()
        isTimerRunning = true
        isTimerPaused = false
    }

    // Function to pause timer
    fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerPaused = true
        isTimerRunning = false
    }

    // Function to end timer early
    fun endTimer() {
        val timeSpent = (initialTime - remainingTime) / (60.0 * 1000.0) // Convert to minutes
        countDownTimer?.cancel()

        // Only save if some time was spent
        if (timeSpent > 0.1) { // Save if more than 6 seconds spent
            saveMeditationSession(timeSpent)
        }

        // Reset all states
        isTimerRunning = false
        isTimerPaused = false
        selectedTime = 0
        remainingTime = 0
        initialTime = 0
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
                IconButton(onClick = {
                    if (isTimerRunning || isTimerPaused) {
                        endTimer()
                    }
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Meditation",
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
                .background(Color(127, 132, 190)),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f), contentAlignment = Alignment.Center){
                Image(
                    painter = rememberDrawablePainter(
                        drawable = getDrawable(
                            LocalContext.current,
                            R.drawable.meditation_image
                        )
                    ),
                    contentDescription = "med1",
                    contentScale = ContentScale.Crop,
                )

            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (!isTimerRunning && !isTimerPaused) {
                    Text(
                        text = "Choose Your Meditation Time",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )

                    // Time selection buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        timeOptions.forEach { minutes ->
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (selectedTime == minutes) Color.White
                                        else Color.White.copy(alpha = 0.3f)
                                    )
                                    .clickable {
                                        selectedTime = minutes
                                        remainingTime = minutes * 60 * 1000L
                                        initialTime = remainingTime
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${minutes}min",
                                    style = TextStyle(
                                        color = if (selectedTime == minutes) Color.Black
                                        else Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                // Timer display
                if (isTimerRunning || isTimerPaused || remainingTime > 0) {
                    Text(
                        text = String.format(
                            "%02d:%02d",
                            remainingTime / 60000,
                            (remainingTime % 60000) / 1000
                        ),
                        style = TextStyle(
                            fontFamily = customFont,
                            fontSize = 48.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Control buttons
                if (selectedTime > 0 || isTimerRunning || isTimerPaused) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Play/Pause button
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    if (isTimerPaused || (!isTimerRunning && remainingTime > 0)) {
                                        startTimer()
                                    } else if (isTimerRunning) {
                                        pauseTimer()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (!isTimerRunning) Icons.Default.PlayArrow
                                else Icons.Default.Pause,
                                contentDescription = if (!isTimerRunning) "Start" else "Pause",
                                tint = Color.Black,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        // End button
                        if (isTimerRunning || isTimerPaused) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red.copy(alpha = 0.8f))
                                    .clickable { endTimer() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "End",
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .fillMaxWidth(0.5f)
                    .align(Alignment.BottomCenter)
                    .height(50.dp)
                    .background(Color.Cyan, RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(Screen.MeditationStatsScreen.route)
                    },
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Meditation Stats",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )

            }
        }
    }
}