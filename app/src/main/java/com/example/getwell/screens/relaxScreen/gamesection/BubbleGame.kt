package com.example.getwell.screens.relaxScreen.gamesection

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class GameItem(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val color: Color,
    var isVisible: Boolean = true
)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BubblePopGame(
    navController: NavHostController,
    onGameSelected: (Int) -> Unit
){
    val bubbles = remember {
        mutableStateListOf(
            GameItem(1, "", Icons.Default.Circle, Color(0xFFEC4899)),
            GameItem(2, "", Icons.Default.Circle, Color(0xFF60A5FA)),
            GameItem(3, "", Icons.Default.Circle, Color(0xFF4ADE80)),
            GameItem(5, "", Icons.Default.Circle, Color(0xFFA78BFA)),
            GameItem(6, "", Icons.Default.Circle, Color(0xFFA78BFA)),
            GameItem(7, "", Icons.Default.Circle, Color(0xFFA78BFA)),
            GameItem(8, "", Icons.Default.Circle, Color(0xFFA78BFA)),
            GameItem(9, "", Icons.Default.Circle, Color(0xFFA78BFA)),
        )


    }
    val particles = remember { mutableStateListOf<Particle>() }
    val scaffoldState = rememberScaffoldState()

    // Track number of visible bubbles
    var visibleBubbles by  remember { mutableStateOf( bubbles.count { it.isVisible })}
    println(bubbles.size)

    // Regenerate bubbles if all are popped
    LaunchedEffect(visibleBubbles) {
        bubbles.add(GameItem(1, "", Icons.Default.Circle, Color(0xFFEC4899)))
        bubbles.add(GameItem(2, "", Icons.Default.Circle, Color(0xFFEC4899)))
    }



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
                    text = "Bubble Pop",
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
                .background(Color(49, 38, 45))
        ) {
            Image(
                painter = painterResource(id = R.drawable.chatbg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Box(modifier = Modifier.fillMaxSize()) {
                // Floating particles
                particles.forEach { particle ->
                    ParticleEffect(particle) {
                        particles.remove(particle)
                    }
                }

                // Game bubbles
                bubbles.forEachIndexed { index, game ->
                    if (game.isVisible) {
                        FloatingBubble2(
                            game = game,
                            index = index,
                            onBubbleClick = { offset, size ->  // Added size parameter
                                game.isVisible = false
                                visibleBubbles--
                                // Generate particles around the bubble's center
                                repeat(20) {
                                    val angle = Random.nextFloat() * 360f
                                    val radius = size.width / 4  // Use 1/4 of bubble size for particle spread
                                    val particleX = offset.x + (radius * cos(Math.toRadians(angle.toDouble()))).toFloat()
                                    val particleY = offset.y + (radius * sin(Math.toRadians(angle.toDouble()))).toFloat()

                                    particles.add(
                                        Particle(
                                            position = Offset(particleX, particleY),
                                            color = game.color,
                                            angle = angle,
                                            speed = Random.nextFloat() * 500f + 300f
                                        )
                                    )
                                }

                                onGameSelected(game.id)
                            }
                        )
                    }
                }
            }
        }
    }



}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UseOfNonLambdaOffsetOverload", "ServiceCast")
@Composable
fun FloatingBubble2(
    game: GameItem,
    index: Int,
    onBubbleClick: (Offset, androidx.compose.ui.geometry.Size) -> Unit  // Modified to include size
) {
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    val bubbleList = listOf(
        R.drawable.balloon_sphere0_3,
        R.drawable.balloon_sphere0_5,
        R.drawable.balloon_sphere0_7,
        R.drawable.balloon_sphere0_2
    )

    // Animation properties
    val yOffset = remember { Animatable(600f) }
    val xOffset = remember { Animatable(Random.nextFloat() * 300f) }
    val scale = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }

    var bubbleSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            while (true){
                yOffset.animateTo(
                    targetValue  = (Random.nextDouble(0.0, 0.7)).toFloat() * 700f,
                    animationSpec = tween(
                        durationMillis = 3000,
                        easing = LinearEasing
                    )
                )
            }

        }

        scope.launch {
            while (true) {
                xOffset.animateTo(
                    targetValue = Random.nextFloat() * 300f,
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = LinearEasing
                    )
                )
            }
        }

        scope.launch {
            while (true) {
                rotation.animateTo(
                    targetValue = 10f,
                    animationSpec = tween(1500, easing = EaseInOutQuad)
                )
                rotation.animateTo(
                    targetValue = -10f,
                    animationSpec = tween(1500, easing = EaseInOutQuad)
                )
            }
        }

        scope.launch {
            while (true) {
                scale.animateTo(
                    targetValue = 1.05f,
                    animationSpec = tween(1000, easing = EaseInOutQuad)
                )
                scale.animateTo(
                    targetValue = 0.95f,
                    animationSpec = tween(1000, easing = EaseInOutQuad)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .offset(
                x = xOffset.value.dp,
                y = yOffset.value.dp
            )
            .scale(scale.value)
            .graphicsLayer(
                rotationZ = rotation.value
            )
            .size(210.dp)
            .clip(CircleShape)
            .clickable {
                scope.launch {
                    scale.animateTo(
                        targetValue = 1.2f,
                        animationSpec = tween(100)
                    )
                    scale.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(200)
                    )
                }

                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        50,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )

                onBubbleClick(
                    Offset(xOffset.value, yOffset.value),
                    bubbleSize
                )
            }
            .onSizeChanged { size ->
                bubbleSize = androidx.compose.ui.geometry.Size(
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .background(Color.Transparent)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(210.dp),
            painter = painterResource(id = bubbleList[index % bubbleList.size]),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}


// Particle effect data and composable
data class Particle(
    val position: Offset,
    val color: Color,
    val angle: Float,
    val speed: Float,
    var alpha: Float = 1f,
    var scale: Float = 1f
)

@Composable
fun ParticleEffect(
    particle: Particle,
    onFinished: () -> Unit
) {
    var position by remember { mutableStateOf(particle.position) }
    var alpha by remember { mutableStateOf(1f) }
    var scale by remember { mutableStateOf(1f) }

    LaunchedEffect(key1 = Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(1000)
        ) { value, _ ->
            val distance = particle.speed * value
            position = Offset(
                position.x + (distance * cos(Math.toRadians(particle.angle.toDouble()))).toFloat(),
                position.y + (distance * sin(Math.toRadians(particle.angle.toDouble()))).toFloat()
            )
            alpha = 1f - value
            scale = 1f - value
        }
        onFinished()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawCircle(
            color = particle.color.copy(alpha = alpha),
            radius = 8.dp.toPx() * scale,
            center = position
        )
    }
}

// Custom animations for more visual interest
@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 0.3f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset.Zero,
                    end = Offset(x = translateAnim.value, y = translateAnim.value)
                )
            )
    )
}