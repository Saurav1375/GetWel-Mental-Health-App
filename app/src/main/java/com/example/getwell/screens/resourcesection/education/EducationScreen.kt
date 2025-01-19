package com.example.getwell.screens.resourcesection.education


//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.getwell.ChatroomActivity
import com.example.getwell.R
import com.example.getwell.data.ResourceItem
import com.example.getwell.data.Screen
import com.example.getwell.data.navItemList
import com.example.getwell.data.resourceItemList
import com.example.getwell.screens.BottomNavigationBar
import com.example.getwell.screens.customFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EducationScreen(
    navController: NavHostController
){

    BackHandler {
        navController.navigate(Screen.HomeScreen.route)
    }
    val feedViewModel: FeedViewModel  = viewModel()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        feedViewModel.fetchFeeds(listOf(
            "https://www.samhsa.gov/blog/rss",
            "https://www.nimh.nih.gov/site-info/index-rss.atom",
            "https://psycnet.apa.org/journals/sah.rss",
            "https://www.nursingtimes.net/mental-health/latest-mental-health-news/feed/",
            "https://www.nursingtimes.net/mental-health/feed/",
            "https://resources.bmj.com/repository/journals-network-project/opml/bmjrssfeeds.opml"
        ))
//        feedViewModel.fetchFeed("https://www.samhsa.gov/blog/rss")
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 TopAppBar(
                     modifier = Modifier
                         .fillMaxWidth()
                         .height(160.dp),
                     backgroundColor = Color(31,31,37),
                     elevation = 5.dp,
                     contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 40.dp),
                 ) {
                     Column(
                         modifier = Modifier.fillMaxWidth()
                     ) {
                         Row(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                             verticalAlignment = Alignment.CenterVertically,
                             horizontalArrangement = Arrangement.SpaceBetween
                         ) {
                             resourceItemList.forEach{item->
                                 ResourceItemView(item = item, navController = navController) {
                                     navController.navigate(item.route)

                                 }
                             }

                         }
                         Divider()
                         Spacer(modifier = Modifier.height(16.dp))
                             Text(
                                 text = "Educational Content",
                                 style = TextStyle(
                                     fontFamily = customFont,
                                     fontWeight = FontWeight.SemiBold,
                                     fontSize = 28.sp,
                                     color = Color.White

                                 )
                             )


                     }


                 }

        },

        bottomBar = {
            BottomNavigationBar(
                list = navItemList,
                navController = navController,
                onNavClick = {
                    if(it.route == Screen.ChatroomScreen.route){
                        val intent = Intent(context, ChatroomActivity::class.java)
                        context.startActivity(intent)

                    }
                    else{
                        navController.navigate(it.route)
                    }
                }
            )

        }



    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(32, 26, 27))

        ) {

            Image(
                painter = painterResource(id = R.drawable.chatbg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            FeedScreen(viewModel = feedViewModel)

//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//                    .verticalScroll(scrollState),
//
//                ) {
//
//
//
//            }
        }
    }
}



@Composable
fun ResourceItemView(item: ResourceItem, navController: NavHostController,onResourceItemClick : () -> Unit){
    val backStackEntry = navController.currentBackStackEntryAsState()
    val selected = item.route ==  backStackEntry.value?.destination?.route
    Box(
        modifier = Modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .background(
                    color = if (selected) Color(181, 242, 255) else Color.Transparent,
                    shape = CircleShape
                )
                .size(40.dp)
                .clickable {
                    onResourceItemClick()
                },
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                tint = if(selected) Color.Black else Color.Unspecified
            )
        }

    }
    
}