package com.example.getwell.screens.relaxScreen.listen




import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont
import com.example.getwell.viewmodel.ListenViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

@androidx.annotation.OptIn(UnstableApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListenScreen(
    navController: NavHostController,
    onClickAction: (ListenItem) -> Unit,
){
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


        val listenList1 = listOf(
        ListenItem(
            title = "Summer Seashore",
            imageResId = R.drawable.sea,
            videoUri = "seawaves.mp4", // Store only the file name
            musicUri = "summer_seashore_music.ogg"
        ),
        ListenItem(
            title = "Space Travel",
            imageResId = R.drawable.space_cruse_bg,
            videoUri = "space_cruise.mp4", // Store only the file name
            musicUri = "space_cruise_music.ogg"
        ),
        ListenItem(
            title = "Riverfall Rhapsody",
            imageResId = R.drawable.waterfall5_image,
            videoUri = "waterfall5_video.mp4", // Store only the file name
            musicUri = "waterfall1_sound.mp3"
        ),
        ListenItem(
            title = "Whispering Mists",
            imageResId = R.drawable.clouds1_image,
            videoUri = "clouds1_video.mp4", // Store only the file name
            musicUri = "clouds2_sound.mp3"
        ),
        ListenItem(
            title = "Woodwind Serenade",
            imageResId = R.drawable.forest1_image,
            videoUri = "forest1_video.mp4", // Store only the file name
            musicUri = "forest5_sound.mp3"
        ),
        ListenItem(
            title = "Frosted Silence",
            imageResId = R.drawable.snow2_image,
            videoUri = "snow2_video.mp4", // Store only the file name
            musicUri = "snow4_sound.mp3"
        )
    )

    val listenList2 = listOf(
        ListenItem(
            title = "Coastal Serenity",
            imageResId = R.drawable.beach1_image,
            videoUri = "beach_video1.mp4", // Store only the file name
            musicUri = "beach_sound1.mp3"
        ),
        ListenItem(
            title = "Crackling Glow",
            imageResId = R.drawable.fire1_image,
            videoUri = "fire1_video.mp4", // Store only the file name
            musicUri = "fire3_sound.mp3",
        ),
        ListenItem(
            title = "Woodland Whisper",
            imageResId = R.drawable.forest2_image,
            videoUri = "forest2_video.mp4", // Store only the file name
            musicUri = "forest2_sound.mp3"
        ),
        ListenItem(
            title = "Cosmic Drift",
            imageResId = R.drawable.night4_image,
            videoUri = "space1_video.mp4", // Store only the file name
            musicUri = "space1_sound.mp3"
        ),
        ListenItem(
            title = "Snowfall Serenade",
            imageResId = R.drawable.snow6_image,
            videoUri = "snow6_video.mp4", // Store only the file name
            musicUri = "snow3_sound.mp3",

        ),
        ListenItem(
            title = "Misty Drizzle",
            imageResId = R.drawable.storm1_image,
            videoUri = "strorm1_video.mp4", // Store only the file name
            musicUri = "storm2_sound.mp3"
        ),
        ListenItem(
            title = "Grove Whispers",
            imageResId = R.drawable.forest5_image,
            videoUri = "forest5_video.mp4", // Store only the file name
            musicUri = "forest3_sound.mp3"
        ),
        ListenItem(
            title = "Mystic Plunge",
            imageResId = R.drawable.waterfall2_image,
            videoUri = "waterfall2_image.mp4", // Store only the file name
            musicUri = "waterfall4_sound.mp3"
        )
    )

    val listenList3 = listOf(
        ListenItem(
            title = "White Noise",
            imageResId = R.drawable.white_image,
            videoUri = "white_video.mp4", // Store only the file name
            musicUri = "white_sound.mp3"
        ),
        ListenItem(
            title = "Violet Noise",
            imageResId = R.drawable.violet_image,
            videoUri = "violet_video.mp4", // Store only the file name
            musicUri = "violet_sound.mp3"
        ),
        ListenItem(
            title = "Pink Noise",
            imageResId = R.drawable.pink_image,
            videoUri = "pink_video.mp4", // Store only the file name
            musicUri = "pink_sound.mp3"
        ),
        ListenItem(
            title = "Grey Noise",
            imageResId = R.drawable.grey_image,
            videoUri = "grey_video.mp4", // Store only the file name
            musicUri = "grey_sound.mp3"
        )
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color(31,31,37),
                elevation = 5.dp,
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 40.dp),
                contentColor = Color.White


            ) {

                IconButton(onClick = {navController.popBackStack()  }) {
                    Icon(

                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack",
                        tint = Color.White
                    )

                }
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Relaxing Music",
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                MusicSectionView(
                    title = "Ambient Sounds",
                    desc = "Immersive nature sounds",
                    listenList = listenList1,
                    onClickAction = onClickAction,

                )
                Spacer(modifier = Modifier.height(16.dp))
                MusicSectionView(
                    title = "Nature",
                    desc = "Melodies that touch the soul",
                    listenList = listenList2,
                    onClickAction = onClickAction,
                )

                Spacer(modifier = Modifier.height(16.dp))
                MusicSectionView(
                    title = "Colored Noise",
                    desc = "World of Colored noise",
                    listenList = listenList3,
                    onClickAction = onClickAction,
                    height = 100.dp,
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun MusicItemCard(
    modifier: Modifier = Modifier,
    item : ListenItem,
    onClickAction: (ListenItem) -> Unit,
    width : Dp = 150.dp,
    height: Dp = 200.dp,
    viewModel: ListenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isDownloaded = rememberSaveable { mutableStateOf(checkIfDownloaded(context, item)) }
    var downloadStatus  = rememberSaveable { mutableStateOf(false)}
    Box(
        modifier
            .size(width, height)
            .background(Color.Transparent, RoundedCornerShape(15.dp))
            .padding(end = 16.dp)
            .clickable {
                if (isDownloaded.value) {
                    onClickAction(item)
                } else {
                        viewModel.downloadItemFiles(context, item, downloadStatus, isDownloaded)

                }
            }

    ){
        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopEnd)

        ){
            if(!isDownloaded.value && !downloadStatus.value){
                Icon(
                    modifier = Modifier.padding(4.dp),
                    painter = painterResource(id = R.drawable.outline_download_for_offline_24),
                    contentDescription = null,
                    tint = Color(180,180,180)
                )
            }
            if (downloadStatus.value) {
                CircularProgressIndicator(
                    color = Color(180,180,180),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black
                    ),
                    tileMode = TileMode.Repeated
                ),
                shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
            )
            .padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier,
                text = item.title,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )
        }

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicSectionView(
    title: String,
    desc: String,
    listenList: List<ListenItem>,
    onClickAction: (ListenItem) -> Unit,
    width: Dp = 150.dp,
    height: Dp = 200.dp,
){
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(modifier = Modifier.fillMaxWidth()){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = desc,
                        style = TextStyle(
                            color = Color(111,111,111),
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    )


                }


            }
            
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow{

                items(listenList){item ->
                    MusicItemCard(item = item, onClickAction = onClickAction, width = width, height = height)
                }
            }

        }

    }
}


fun checkIfDownloaded(context: Context, item: ListenItem): Boolean {
    val filesDir = context.filesDir
    val videoFile = File(filesDir, item.videoUri)
    val audioFile = File(filesDir, item.musicUri)
    return videoFile.exists() && audioFile.exists()
}

// Download and Save Files
suspend fun downloadItemFiles(context: Context, item: ListenItem) {
    val storage = Firebase.storage
    val filesDir = context.filesDir

    // Construct paths for video and audio files
    val videoPath = "video/${item.videoUri}" // Assuming item.videoUri contains the file name
    val audioPath = "audio/${item.musicUri}" // Assuming item.musicUri contains the file name

    // Download Video
    try {
        val videoRef = storage.reference.child(videoPath)
        val videoFile = File(filesDir, item.videoUri) // Save video with the same name
        videoRef.getFile(videoFile).await()

        // Download Audio
        val audioRef = storage.reference.child(audioPath)
        val audioFile = File(filesDir, item.musicUri) // Save audio with the same name
        audioRef.getFile(audioFile).await()

        Toast.makeText(context, "${item.title} downloaded", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        println(e.message)
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

