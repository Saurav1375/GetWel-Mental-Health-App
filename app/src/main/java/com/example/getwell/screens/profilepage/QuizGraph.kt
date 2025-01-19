package com.example.getwell.screens.profilepage


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.getwell.screens.stressQuiz.QuizItem
import com.example.getwell.screens.stressQuiz.calculateAcadScore
import com.example.getwell.screens.stressQuiz.calculateBAIScore
import com.example.getwell.screens.stressQuiz.calculateBDIScore
import com.example.getwell.screens.stressQuiz.calculateDASSStressScore
import com.example.getwell.screens.stressQuiz.calculatePSSScore
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.time.times

// Constants for layout

data class QuizRes(
    val timestamp: Timestamp,
    val score : Double
)

@Composable
fun QuizChart(
    userId: String,
    modifier: Modifier = Modifier,
    period: ChartPeriod = ChartPeriod.WEEK,
    id : String ,
    graphColor: Color,
    maxScore : Double = 42.0,
    gridLines : List<Int> = listOf(0, 14, 28, 42)

) {
    var quizData by remember { mutableStateOf<List<QuizRes>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    // Animation state for period change
    val transitionState = remember { MutableTransitionState(period) }
    val transition = updateTransition(transitionState, label = "periodTransition")

    val animatedProgress by transition.animateFloat(
        label = "chartProgress",
        transitionSpec = { tween(durationMillis = 500, easing = FastOutSlowInEasing) }
    ) { _ -> 1f }

    LaunchedEffect(userId, period) {
        isLoading = true
        isError = false
        transitionState.targetState = period
        try {
            quizData = fetchQuizScores(userId, id, period)
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = when (period) {
                ChartPeriod.WEEK -> "Weekly $id"
                ChartPeriod.MONTH -> "Monthly $id"
                ChartPeriod.THREE_MONTHS -> "3-Month $id"
                ChartPeriod.SIX_MONTHS -> "6-Month $id"
            },
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            ShimmerGraphItem()
//            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        } else if (isError) {
            Text("Error loading data. Please try again.", color = Color.Red)
        } else {
            AnimatedQuizChart(
                quizEntries = quizData,
                period = period,
                progress = animatedProgress,
                graphColor = graphColor,
                gridLines = gridLines,
                maxScore = maxScore,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 20.dp, bottom = 16.dp, start = 8.dp)
            )
        }
    }
}

@Composable
private fun AnimatedQuizChart(
    quizEntries: List<QuizRes>,
    period: ChartPeriod,
    progress: Float,
    modifier: Modifier = Modifier,
    graphColor: Color,
    gridLines : List<Int> = listOf(0, 14, 28, 42),
    maxScore : Double = 42.0
) {
    var selectedPoint by remember { mutableStateOf<Pair<Int, QuizRes>?>(null) }
    var touchPosition by remember { mutableStateOf<Offset?>(null) }

    // Fixed max score for DASS stress scale (0-42)
    val points = quizEntries.mapIndexed { index, entry -> index.toFloat() to entry.score.toFloat() }

    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = { offset ->
                    touchPosition = offset
                    val canvasWidth = size.width - 2 * ChartConstants.padding
                    val xScale = canvasWidth / (points.size - 1).coerceAtLeast(1)

                    val nearestPoint = points.mapIndexed { index, point ->
                        val pixelX = ChartConstants.padding + point.first * xScale
                        index to abs(pixelX - offset.x)
                    }.minByOrNull { it.second }?.first

                    selectedPoint = nearestPoint?.let { it to quizEntries[it] }
                },
                onTap = {
                    touchPosition = null
                    selectedPoint = null
                }
            )
        }
    ) {
        val width = size.width
        val height = size.height
        val xScale = (width - 2 * ChartConstants.padding) / (points.size - 1).coerceAtLeast(1)
        val yScale = (height - 2 * ChartConstants.padding) / maxScore

        // Draw horizontal grid lines with animation
        gridLines.forEach { score ->
            val y = height - ChartConstants.padding - (score * yScale).toFloat()
            drawLine(
                color = Color.Gray.copy(alpha = 0.2f * progress),
                start = Offset(ChartConstants.padding, y),
                end = Offset(width - ChartConstants.padding, y),
                strokeWidth = 1f
            )

            // Draw score labels
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    score.toString(),
                    ChartConstants.padding - 25f,
                    y + 5f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.GRAY
                        textSize = 32f
                        textAlign = android.graphics.Paint.Align.RIGHT
                        alpha = (255 * progress).toInt()
                    }
                )
            }
        }

        // Animated line path
        val animatedPoints = points.map { it.first * progress to it.second }
        val linePath = Path().apply {
            animatedPoints.forEachIndexed { index, (x, y) ->
                val pixelX = ChartConstants.padding + x * xScale
                val pixelY = height - ChartConstants.padding - y * yScale
                if (index == 0) moveTo(pixelX, pixelY.toFloat())
                else {
                    val prevX = ChartConstants.padding + animatedPoints[index - 1].first * xScale
                    val prevY = height - ChartConstants.padding - animatedPoints[index - 1].second * yScale
                    cubicTo(
                        prevX + (pixelX - prevX) / 2, prevY.toFloat(),
                        prevX + (pixelX - prevX) / 2, pixelY.toFloat(),
                        pixelX, pixelY.toFloat()
                    )
                }
            }
        }

        // Gradient fill under the line
        drawPath(
            path = Path().apply {
                addPath(linePath)
                lineTo(width - ChartConstants.padding, height - ChartConstants.padding)
                lineTo(ChartConstants.padding, height - ChartConstants.padding)
                close()
            },
            brush = Brush.verticalGradient(
                colors = listOf(
                   graphColor.copy(alpha = 0.3f * progress),
                    Color.Transparent
                ),
                startY = 0f,
                endY = height - ChartConstants.padding
            )
        )

        // Draw the line path
        drawPath(
            path = linePath,
            color = graphColor.copy(alpha = progress),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        val labelSpacing = (points.size / 7).coerceAtLeast(1)

        points.forEachIndexed { index, (x, _) ->
            if (index % labelSpacing == 0) {
                val pixelX = ChartConstants.padding + x * xScale
                quizEntries.getOrNull(index)?.timestamp?.toDate()?.let { date ->
                    val label = SimpleDateFormat(
                        when (period) {
                            ChartPeriod.WEEK -> "EEE"
                            ChartPeriod.MONTH -> "dd"
                            ChartPeriod.THREE_MONTHS -> "MMM"
                            ChartPeriod.SIX_MONTHS -> "MMM"
                        },
                        Locale.getDefault()
                    ).format(date)

                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            label,
                            pixelX,
                            height - ChartConstants.padding + 65f,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.GRAY
                                textSize = 32f
                                textAlign = android.graphics.Paint.Align.CENTER
                                alpha = (255 * progress).toInt()
                            }
                        )
                    }
                }
            }
        }

        // Draw points
        points.forEach { (x, y) ->
            val pixelX = ChartConstants.padding + x * xScale
            val pixelY = height - ChartConstants.padding - y * yScale
            drawCircle(
                color = Color.White.copy(alpha = progress),
                radius = ChartConstants.pointRadius.dp.toPx(),
                center = Offset(pixelX, pixelY.toFloat())
            )
            drawCircle(
                color = graphColor.copy(alpha = progress),
                radius = ChartConstants.pointRadius.dp.toPx() - 2,
                center = Offset(pixelX, pixelY.toFloat())
            )
        }

        // Tooltip for selected point
        selectedPoint?.let { (index, entry) ->
            val x = ChartConstants.padding + points[index].first * xScale
            val y = height - ChartConstants.padding - points[index].second * yScale
            val tooltipY = y - ChartConstants.tooltipHeight - 10f
            drawRoundRect(
                color = graphColor,
                topLeft = Offset(x - ChartConstants.tooltipWidth / 2, tooltipY.toFloat()),
                size = Size(ChartConstants.tooltipWidth, ChartConstants.tooltipHeight),
                cornerRadius = CornerRadius(5f, 5f)
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${entry.score}",
                    x,
                    (tooltipY + ChartConstants.tooltipHeight - 8f).toFloat(),
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 32f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}
suspend fun fetchQuizScores(userEmail: String, id : String, period: ChartPeriod): List<QuizRes> {
    val scoresList = mutableListOf<QuizRes>()
    val firestore = FirebaseFirestore.getInstance()

    val calendar = Calendar.getInstance()

    calendar.time = Date()
    when (period) {
        ChartPeriod.WEEK -> calendar.add(Calendar.DAY_OF_YEAR, -7)
        ChartPeriod.MONTH -> calendar.add(Calendar.MONTH, -1)
        ChartPeriod.THREE_MONTHS -> calendar.add(Calendar.MONTH, -3)
        ChartPeriod.SIX_MONTHS -> calendar.add(Calendar.MONTH, -6)
    }
    val startDate = calendar.time

    // Access Firestore collection path: /users/{userEmail}/StressQuiz/DASS/response
    val responseCollection = firestore.collection("users")
        .document(userEmail)
        .collection("StressQuiz")
        .document(id)
        .collection("response")
        .whereGreaterThan("timestamp", Timestamp(startDate))
        .orderBy("timestamp")

    try {
        // Retrieve all documents in the "response" collection
        val documents = responseCollection.get().await()

        for (document in documents) {
            val timestamp = document.getTimestamp("timestamp")
            @Suppress("UNCHECKED_CAST")
            val quizList = document.get("quizList") as? List<Map<String, Any>>

            // Parse the quizList to List<QuizItem>
            val quizItems = quizList?.map { map ->
                QuizItem(
                    id = (map["id"] as? Long)?.toInt() ?: 0,
                    desc = map["description"] as? String ?: "",
                    selectedOption = (map["selectedOption"] as? Long)?.toInt() ?: -1
                )
            } ?: emptyList()

            // Calculate score
            val score = when (id) {
                "DASS21" -> calculateDASSStressScore(quizItems)
                "PSS" -> calculatePSSScore(quizItems)
                "BDI" -> calculateBDIScore(quizItems)
                "BAI" -> calculateBAIScore(quizItems)
                "Acad-Stress" -> calculateAcadScore(quizItems)
                else -> 0.0
            }

//            val score = if(id == "DASS21") calculateDASSStressScore(quizItems) else calculatePSSScore(quizItems)

            if (score >= 0) {  // Only add if the test was completed
                timestamp?.let {
                    scoresList.add(QuizRes(it, score))
                }
            }
        }
    } catch (e: Exception) {
        println("Error fetching quiz data: $e")
        return emptyList()
    }

    return scoresList.sortedBy { it.timestamp.toDate() }
}