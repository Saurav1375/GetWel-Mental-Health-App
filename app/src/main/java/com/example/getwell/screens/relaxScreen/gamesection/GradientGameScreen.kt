package com.example.getwell.screens.relaxScreen.gamesection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.getwell.screens.customFont
import kotlin.random.Random

data class LightPoint(
    val id: Int,
    var position: Offset,
    var radius: Float,
    var alpha: Float,
    var color: Color,
    var velocity: Offset = Offset(0f, 0f),
    var startTime: Long = System.currentTimeMillis()
)

@Composable
fun EtheralFlowScreen(
    navController: NavHostController
) {
    var lightPoints by remember { mutableStateOf(listOf<LightPoint>()) }
    var nextId by remember { mutableStateOf(0) }

    // Vibrant but slightly muted colors to prevent over-brightness
    val lightColors = listOf(
        Color(0xFF8B3D91), // Muted Purple
        Color(0xFF3D917B), // Muted Teal
        Color(0xFF914B3D), // Muted Orange-Red
        Color(0xFF3D5691), // Muted Blue
        Color(0xFF919B3D), // Muted Yellow-Green
        Color(0xFF913D86)  // Muted Pink
    )

    // Darker background for better contrast
    val backgroundColors = listOf(
        Color(0xFF000819),
        Color(0xFF001229),
        Color(0xFF001833)
    )

    // Animation logic
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { _ ->
                val currentTime = System.currentTimeMillis()
                lightPoints = lightPoints.mapNotNull { point ->
                    val age = (currentTime - point.startTime) / 1000f
                    val newRadius = point.radius + 0.5f  // Even slower expansion
                    val newAlpha = point.alpha * 0.97f

                    if (newAlpha > 0.05f) {
                        point.copy(
                            radius = newRadius,
                            alpha = newAlpha,
                            position = point.position + point.velocity,
                            velocity = point.velocity + Offset(
                                Random.nextFloat() * 0.1f - 0.05f,
                                Random.nextFloat() * 0.1f - 0.05f
                            )
                        )
                    } else null
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(backgroundColors))
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()

                    val touchPosition = change.position
                    val newPoints = (0..3).map { offset ->
                        val randomOffset = Offset(
                            Random.nextFloat() * 20 - 10,  // Reduced spread
                            Random.nextFloat() * 20 - 10
                        )
                        LightPoint(
                            id = nextId++,
                            position = touchPosition + randomOffset,
                            radius = Random.nextFloat() * 28f + 38f,  // Much smaller initial radius
                            alpha = Random.nextFloat() * 0.3f + 0.4f, // Lower initial alpha
                            color = lightColors[Random.nextInt(lightColors.size)],
                            velocity = Offset(
                                Random.nextFloat() * 2f - 1f,
                                Random.nextFloat() * 2f - 1f
                            )
                        )
                    }
                    lightPoints = lightPoints + newPoints
                }
            }
    ) {
        Box(
            modifier = Modifier
                .padding(top = 40.dp, start = 16.dp)
                .zIndex(1f)
        ) {
            Spacer(modifier =Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable { navController.popBackStack() },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "arrowBack",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Ethereal Flow",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                )
            }


        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            lightPoints.forEach { point ->
                // Draw outer glow - thinner and more transparent
                drawCircle(
                    color = point.color.copy(alpha = point.alpha * 0.15f),
                    radius = point.radius * 1.8f,  // Reduced multiplier
                    center = point.position,
                    blendMode = BlendMode.Plus
                )

                // Draw middle glow - thinner
                drawCircle(
                    color = point.color.copy(alpha = point.alpha * 0.3f),
                    radius = point.radius * 1.3f,  // Reduced multiplier
                    center = point.position,
                    blendMode = BlendMode.Plus
                )

                // Draw core - pure color, no white
                drawCircle(
                    color = point.color.copy(alpha = point.alpha * 0.5f),
                    radius = point.radius,
                    center = point.position,
                    blendMode = BlendMode.Plus
                )
            }
        }
    }
}