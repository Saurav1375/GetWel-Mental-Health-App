package com.example.getwell.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.data.SettingItem
import com.example.getwell.data.navItemList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavHostController,
    userData: UserData?,
    onSignOut : () -> Unit,
    onItemClick : (Int) -> Unit
){
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val settingsList = listOf(
        SettingItem(
            id = 1,
            title = "Account",
            icon = R.drawable.profile_setting_icon
        ),SettingItem(
            id = 2,
            title = "Privacy",
            icon = R.drawable.lock_setting_icon
        ),SettingItem(
            id = 3,
            title = "Avatar",
            icon = R.drawable.avatar_setting_icon
        ),SettingItem(
            id = 6,
            title = "Data and Storage",
            icon = R.drawable.storage_setting_icon
        ),SettingItem(
            id = 7,
            title = "About",
            icon = R.drawable.about_setting_icon
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

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(

                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack",
                        tint = Color.White
                    )

                }
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Settings",
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
//                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))
                UserInfo(
                    name = userData?.username ?: "Guest" ,
                    userPic = userData?.profilePictureUrl,
                    onSignOut = {onSignOut()}
                )

                Spacer(modifier = Modifier.height(32.dp))


                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(31, 31, 37), shape = RoundedCornerShape(20.dp)),
                ){
                    settingsList.forEach{item->
                        SettingItemView(item = item, onClickSettingItem = onItemClick)

                    }


                }
                Spacer(modifier = Modifier.height(100.dp))

            }
        }
    }
}
@Composable
fun SearchBar(){
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .background(Color(32, 26, 27), shape = RoundedCornerShape(40.dp))
            .border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(40.dp))
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.35f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )

                Text(
                    text = "Search",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.White
                )

            }

        }

    }
}

@Composable
fun UserInfo(
    name : String,
    userPic : String?,
    onSignOut: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(color = Color.Unspecified, shape = CircleShape)
            ){
                if(userPic != null){
                    AsyncImage(
                        model = userPic,
                        contentDescription = "profile picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop

                    )
                }
                else{
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.profile_icon),
                        contentDescription = name,
                        contentScale = ContentScale.FillBounds
                    )

                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            color = Color(255, 132, 86),
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.clickable { onSignOut() },
                        text = "Sign Out",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.Cyan,
                            textDecoration = TextDecoration.Underline
                        )
                    )




                }



            }

        }

    }
}



@Composable
fun SettingItemView(item : SettingItem, onClickSettingItem : (Int) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 2.dp, bottom = 2.dp)
            .clickable { onClickSettingItem(item.id) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier.padding(bottom = 8.dp).size(30.dp),
                contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = item.icon),
                    contentDescription = item.title,
                    contentScale = ContentScale.FillBounds
                )

            }

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                    
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color(77,86,76)
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color= Color(77,86,76))

            }

        }

    }

}

