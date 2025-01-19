package com.example.getwell.screens.profilepage

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

// Constants for layout
object ChartConstants {
    const val padding = 30f
    const val tooltipWidth = 60f
    const val tooltipHeight = 50f
    const val pointRadius = 4f
    const val highlightRadius = 6f
}

data class MoodEntry(
    val timestamp: Timestamp,
    val moodId: Int
)

@Composable
fun MoodChart(
    userId: String,
    modifier: Modifier = Modifier,
    period: ChartPeriod = ChartPeriod.WEEK
) {
    var moodData by remember { mutableStateOf<List<MoodEntry>>(emptyList()) }
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
            moodData = fetchMoodData(userId, period)
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
                ChartPeriod.WEEK -> "Weekly Mood"
                ChartPeriod.MONTH -> "Monthly Mood"
                ChartPeriod.THREE_MONTHS -> "3-Month Mood"
                ChartPeriod.SIX_MONTHS -> "6-Month Mood"
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
            AnimatedMoodChart(
                moodEntries = moodData,
                period = period,
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 16.dp, bottom = 20.dp, start = 8.dp)
            )
        }
    }
}
@Composable
private fun AnimatedMoodChart(
    moodEntries: List<MoodEntry>,
    period: ChartPeriod,
    progress: Float,
    modifier: Modifier = Modifier
) {
    var selectedPoint by remember { mutableStateOf<Pair<Int, MoodEntry>?>(null) }
    var touchPosition by remember { mutableStateOf<Offset?>(null) }

    // Set fixed scale from 1 to 5 for mood values
    val minMoodId = 0
    val maxMoodId = 5
    val points = moodEntries.mapIndexed { index, entry -> index.toFloat() to entry.moodId.toFloat() }

    val moodEmojis = listOf("😀","😁", "😊","😐", "😟","😢" )

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

                    selectedPoint = nearestPoint?.let { it to moodEntries[it] }
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
        val yScale = (height - 2 * ChartConstants.padding) / (maxMoodId - minMoodId)

        // Draw horizontal grid lines with animation for values 1 to 5
        for (i in minMoodId..maxMoodId) {
            val y = height - ChartConstants.padding - ((i - minMoodId) * yScale)

            drawLine(
                color = Color.Gray.copy(alpha = 0.2f * progress),
                start = Offset(ChartConstants.padding, y),
                end = Offset(width - ChartConstants.padding, y),
                strokeWidth = 1f
            )

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${moodEmojis.getOrElse(i ) { "😐" }}  $i",
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
                val pixelY = height - ChartConstants.padding - ((y - minMoodId) * yScale)
                if (index == 0) moveTo(pixelX, pixelY)
                else {
                    val prevX = ChartConstants.padding + animatedPoints[index - 1].first * xScale
                    val prevY = height - ChartConstants.padding - ((animatedPoints[index - 1].second - minMoodId) * yScale)
                    cubicTo(
                        prevX + (pixelX - prevX) / 2, prevY,
                        prevX + (pixelX - prevX) / 2, pixelY,
                        pixelX, pixelY
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
                    Color(0xFF2196F3).copy(alpha = 0.3f * progress),
                    Color.Transparent
                ),
                startY = 0f,
                endY = height - ChartConstants.padding
            )
        )

        // Draw the line path
        drawPath(
            path = linePath,
            color = Color(0xFF2196F3).copy(alpha = progress),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        val labelSpacing = (points.size / 7).coerceAtLeast(1)

        points.forEachIndexed { index, (x, _) ->
            if (index % labelSpacing == 0) {
                val pixelX = ChartConstants.padding + x * xScale
                moodEntries.getOrNull(index)?.timestamp?.toDate()?.let { date ->
                    val label = SimpleDateFormat(
                        getDateLabelFormat(period),
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
            val pixelY = height - ChartConstants.padding - ((y - minMoodId) * yScale)
            drawCircle(
                color = Color.White.copy(alpha = progress),
                radius = ChartConstants.pointRadius.dp.toPx(),
                center = Offset(pixelX, pixelY)
            )
            drawCircle(
                color = Color(0xFF2196F3).copy(alpha = progress),
                radius = ChartConstants.pointRadius.dp.toPx() - 2,
                center = Offset(pixelX, pixelY)
            )
        }

        // Tooltip for selected point
        selectedPoint?.let { (index, entry) ->
            val x = ChartConstants.padding + points[index].first * xScale
            val y = height - ChartConstants.padding - ((entry.moodId - minMoodId) * yScale)
            val tooltipY = y - ChartConstants.tooltipHeight - 10f
            drawRoundRect(
                color = Color(0xFF2196F3),
                topLeft = Offset(x - ChartConstants.tooltipWidth / 2, tooltipY),
                size = Size(ChartConstants.tooltipWidth, ChartConstants.tooltipHeight),
                cornerRadius = CornerRadius(5f, 5f)
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${entry.moodId}",
                    x,
                    tooltipY + ChartConstants.tooltipHeight - 8f,
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
enum class ChartPeriod {
    WEEK,
    MONTH,
    THREE_MONTHS,
    SIX_MONTHS
}

suspend fun fetchMoodData(userId: String, period: ChartPeriod): List<MoodEntry> {
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

    return try {
        firestore
            .collection("users")
            .document(userId)
            .collection("moods")
            .whereGreaterThan("timestamp", Timestamp(startDate))
            .orderBy("timestamp")
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                val timestamp = document.getTimestamp("timestamp")
                val moodId = document.getLong("id")?.toInt()
                if (timestamp != null && moodId != null) {
                    MoodEntry(timestamp, moodId)
                } else null
            }
            .sortedBy { it.timestamp.toDate() }
    } catch (e: Exception) {
        println("Error fetching mood data: ${e.message}")
        emptyList()
    }
}




@Composable
fun ShimmerGraphItem() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(33,37,46))
                .padding(16.dp)
                .shimmer(shimmer) ,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(24.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
            )

        }
    }
}
private fun getDateLabelFormat(period: ChartPeriod): String {
    return when (period) {
        ChartPeriod.WEEK -> "EEE"
        ChartPeriod.MONTH -> "dd"
        ChartPeriod.THREE_MONTHS -> "MMM"
        ChartPeriod.SIX_MONTHS -> "MMM"
    }
}
