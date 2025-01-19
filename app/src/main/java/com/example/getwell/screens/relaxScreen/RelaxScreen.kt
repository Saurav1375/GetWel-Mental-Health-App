package com.example.getwell.screens.relaxScreen


import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.ChatroomActivity
import com.example.getwell.R
import com.example.getwell.data.RelaxItem
import com.example.getwell.data.Screen
import com.example.getwell.data.navItemList
import com.example.getwell.screens.BottomNavigationBar
import com.example.getwell.screens.customFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RelaxScreen(
    navController: NavHostController
){
    BackHandler {
        navController.navigate(Screen.HomeScreen.route)
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val relaxList = listOf(
        RelaxItem(
            title = "Relaxing Music",
            banner = R.drawable.relaxing_music,
            route = Screen.ListenScreen.route
        ), RelaxItem(
            title = "Stress Busting Games",
            banner = R.drawable.relaxing_games,
            route = Screen.GamesMainScreen.route
        ), RelaxItem(
            title = "Break the Stigma",
            banner = R.drawable.relax_quiz,
            route = Screen.StigmaQuizMainScreen.route
        ), RelaxItem(
            title = "Physical Therapy",
            banner = R.drawable.physicaltherapy,
            route = Screen.BreathingMainScreen.route
        ),RelaxItem(
            title = "Mental Therapy",
            banner = R.drawable.mentaltherapy,
            route = Screen.MentalTherapyMainScreen.route
        ),RelaxItem(
            title = "Appraisal of Stressor",
            banner = R.drawable.irrationalthought,
            route = Screen.AppraisalScreen.route
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



                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Relax...",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.White

                    )
                )

            }

        },
        bottomBar = {
            BottomNavigationBar(
                list = navItemList,
                navController = navController,
                onNavClick = {if(it.route == Screen.ChatroomScreen.route){
                    val intent = Intent(context, ChatroomActivity::class.java)
                    context.startActivity(intent)

                }
                else{
                    navController.navigate(it.route)
                }}
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
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {

                relaxList.forEach { item->
                    RelaxItem(item = item){
                        navController.navigate(item.route)

                    }
                }

                Spacer(modifier = Modifier.height(100.dp))

            }
        }
    }
}


@Composable
fun RelaxItem(
    item : RelaxItem,
    onRelaxItemCLick : () -> Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 16.dp, bottom = 16.dp)
        .background(
            color = Color(19,30,37),
            shape = RoundedCornerShape(25.dp)
        )
        .clickable { onRelaxItemCLick() }){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.80f)
                .clipToBounds()
                .background(color = Color.Transparent,shape = RoundedCornerShape(25.dp))

            )

            {

                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .fillMaxSize(),
                    painter = painterResource(id = item.banner),
                    contentDescription = item.title,
                    contentScale = ContentScale.FillBounds
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }



        }

    }

}

