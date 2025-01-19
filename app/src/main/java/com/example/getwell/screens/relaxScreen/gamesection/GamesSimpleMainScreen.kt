package com.example.getwell.screens.relaxScreen.gamesection

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.screens.customFont


data class GameItemModel(
    val id: Int,
    val name: String,
    val icon: Int,
    val desc : String
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameSimpleMainScreen(
    navController: NavHostController,
    onGameSelected: (Int) -> Unit
) {
    val gameItems = listOf(
        GameItemModel(
            id = 2,
            name = "Bubble-Pop Bliss",
            icon = R.drawable.bubbles_banner_image,
            desc = "Pop colorful bubbles in a vibrant world for an instant mood boost and stress relief."
        ),
        GameItemModel(
            id = 1,
            name = "Ethereal Flow Meditation",
            icon = R.drawable.etheral_banner, // Replace with your actual icon
            desc = "Immerse yourself in calming visuals and soothing sounds to practice mindfulness and relaxation."
        ),
        GameItemModel(
            id = 3,
            name = "Daily Reflection Journey",
            icon = R.drawable.dailyref_banner, // Replace with your actual icon
            desc = "Reflect on your day with thoughtful journaling prompts that encourage self-expression and personal growth."
        ),

    )



    val scaffoldState = rememberScaffoldState()


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
                    text = "Stress-Busting Games",
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    gameItems.forEach {
                        GameItemView(item = it, onItemClick = onGameSelected)
                    }
                }

            }
        }
    }
}

@Composable
fun GameItemView(
    item: GameItemModel,
    onItemClick: (Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .background(Color(31, 31, 37), RoundedCornerShape(16.dp))
            .clickable {
                onItemClick(item.id)
            },
        contentAlignment = Alignment.Center
    ) {
        // Background Image that scales
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .graphicsLayer { alpha = 0.4f }
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
            )

            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(31, 31, 37, 230),
                                Color(31, 31, 37, 160)
                            )
                        )
                    )
            )
        }

        // Content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.desc,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Start
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}
@Composable
fun GameItemCard(item : GameItemModel, onItemClick : (Int) -> Unit){

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .background(
            color = Color(19, 30, 37),
            shape = RoundedCornerShape(25.dp)
        )
        .clickable { onItemClick(item.id) },
         contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Box(modifier = Modifier
                .size(70.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            )
            {

                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "",
                    tint = Color.White
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.desc,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                )

            }

        }

    }

}