package com.example.getwell.screens.relaxScreen.listen


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.data.Screen
import com.example.getwell.domain.provider.ListenProvider
import com.example.getwell.screens.customFont
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File

@OptIn(UnstableApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListenItemScreen(
    navController: NavHostController,
    item: ListenItem
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isPlaying by remember { mutableStateOf(true) } // Track play/pause state
    var isLoading by remember { mutableStateOf(true) } // Track loading state
    // Setup ExoPlayer for Video
    val videoPlayer = remember { ExoPlayer.Builder(context).build() }
    val audioPlayer = remember { ExoPlayer.Builder(context).build() }

    val videoFilePath = getDownloadedFilePath(context, item.videoUri)
    val audioFilePath = getDownloadedFilePath(context, item.musicUri)

    println("video : $videoFilePath, music : $audioFilePath")

    val videoUri = remember { mutableStateOf<Uri?>(null) } // To hold the fetched URI

    // Use LaunchedEffect to fetch the video URI
    LaunchedEffect(Unit) {
        if (videoFilePath != null && audioFilePath != null) {
            // Set up video
            videoPlayer.setMediaItem(MediaItem.fromUri(Uri.fromFile(videoFilePath)))
            videoPlayer.prepare()
            videoPlayer.playWhenReady = isPlaying
            videoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
            videoPlayer.volume = item.volVideo

            // Set up audio
            audioPlayer.setMediaItem(MediaItem.fromUri(Uri.fromFile(audioFilePath)))
            audioPlayer.prepare()
            audioPlayer.playWhenReady = isPlaying
            audioPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
            audioPlayer.volume = item.volAudio

            isLoading = false
        }
    }


    // When play state changes, update both players
    LaunchedEffect(isPlaying) {
        videoPlayer.playWhenReady = isPlaying
        audioPlayer.playWhenReady = isPlaying
        if (isPlaying) {
            videoPlayer.prepare()
            audioPlayer.prepare()
            videoPlayer.play()
            audioPlayer.play()
        } else {
            videoPlayer.pause()
            audioPlayer.pause()
        }
    }

    // Clean up players when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            videoPlayer.release()
            audioPlayer.release()
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(39, 32, 37))

        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if(videoUri.value != null){
                    Image(
                        painter = painterResource(id = item.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                else{
                    if (videoFilePath != null) {
                        AndroidView(
                            factory = { context ->
                                PlayerView(context).apply {
                                    player = videoPlayer
                                    useController = false
                                    layoutParams = FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT,
                                        FrameLayout.LayoutParams.MATCH_PARENT
                                    )
                                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                }

            }

            Box(
                modifier = Modifier
                    .padding(top = 40.dp, start = 16.dp)
                    .clickable { navController.popBackStack() }
                    .zIndex(1f)
            ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack",
                        tint = Color.White
                    )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    modifier = Modifier,
                    text = "listen",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier,
                    text = item.title,
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(128.dp))
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color(255, 255, 255, 70), CircleShape)
                        .clickable(
                            indication = null, // Disable the ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isPlaying = !isPlaying
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(
                            id = if (isPlaying) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                }


            }

        }

    }
}




fun getDownloadedFilePath(context: Context, filename: String): File? {
    val file = File(context.filesDir, filename)
    return if (file.exists()) file else null
}