package com.example.getwell.screens

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.data.ChatroomItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatroomScreen(
    navController: NavHostController,
    userData: com.example.getwell.authSystem.UserData?
){


    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val roomList = listOf(
        ChatroomItem(
            title = "Calmness Corner",
            description = "A serene space to unwind and share stress-relief techniques.",
            icon = R.drawable._icon__google_,

        ),ChatroomItem(
            title = "Stress-Free Zone",
            description = "A supportive environment for discussing stress management tips.",
            icon = R.drawable._icon__google_,

        ),ChatroomItem(
            title = "Serenity Space",
            description = "A cozy room for guided breathing, meditation, and relaxation.",
            icon = R.drawable._icon__google_,

        ),ChatroomItem(
            title = "Peaceful Heaven",
            description = "A refuge for escaping stress and finding inner peace.",
            icon = R.drawable._icon__google_,

        ),ChatroomItem(
            title = "Peaceful Heaven",
            description = "A refuge for escaping stress and finding inner peace.",
            icon = R.drawable._icon__google_,

        )
    )

//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//                 TopAppBar(
//                     backgroundColor = Color(31,31,37),
//                     elevation = 5.dp,
//                     contentPadding = PaddingValues(8.dp),
//                     contentColor = Color.White
//
//
//                 ) {
//
//                     IconButton(onClick = {  }) {
//                         Icon(
//
//                             imageVector = Icons.Default.ArrowBack,
//                             contentDescription = "arrowBack",
//                             tint = Color.White
//                         )
//
//                     }
//                     Spacer(modifier = Modifier.width(16.dp))
//
//                     Text(
//                         text = "Join A Chatroom",
//                         style = TextStyle(
//                             fontFamily = customFont,
//                             fontWeight = FontWeight.SemiBold,
//                             fontSize = 20.sp,
//                             color = Color.White
//
//                         )
//                     )
//
//                 }
//
//        },
//        bottomBar = {
//            BottomNavigationBar(
//                list = navItemList,
//                navController = navController,
//                onNavClick = {
//                     if(it.route == Screen.ChatroomScreen.route){
//                        val intent = Intent(context, ChatroomActivity::class.java)
//                        context.startActivity(intent)
//
//                    }
//                    else{
//                        navController.navigate(it.route)
//                    }}
//            )
//
//
//        }
//
//
//
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(49, 38, 45))
//        ) {
//
//            Image(
//                painter = painterResource(id = R.drawable.chatbg),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.FillBounds
//            )
//
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp)
//                    .verticalScroll(scrollState)
//            ) {
//
//                Box(modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//                    .height(45.dp)
//                    .background(Color.White, shape = RoundedCornerShape(6.dp)),
//
//                    contentAlignment = Alignment.Center
//
//
//                ){
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 16.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Icon(
//                            modifier = Modifier.size(30.dp),
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "search",
//                            tint = Color(111,111,111)
//                        )
//                        Spacer(modifier = Modifier.width(16.dp))
//                        Text(
//                            text = "Search",
//                            style = TextStyle(
//                                fontFamily = customFont,
//                                fontWeight = FontWeight.Normal,
//                                fontSize = 20.sp,
//                                color = Color(111,111,111)
//                            )
//                        )
//                    }
//
//
//
//                }
//                roomList.forEach { item->
//                    ChatroomItem(item = item){
//
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(100.dp))
//
//            }
//        }
//    }
}


@Composable
fun ChatroomItem(
    item : ChatroomItem,
    onRoomCLick : () -> Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onRoomCLick() }){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .size(50.dp)
                    .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                    ) {
                        Text(
                            text = item.title,
                            style = TextStyle(
                                fontFamily = customFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = Color.White

                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.description,
                            style = TextStyle(
                                fontFamily = customFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color(111,111,111)

                            )
                        )

                    }
                }


            }

            Divider()
        }

    }

}

