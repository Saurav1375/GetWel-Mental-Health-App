package com.example.getwell.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.data.Screen

@Composable
fun OnBoardingScreen2(
    navigateToBoarding1 : () -> Unit,
    navController : NavHostController,
    onNext : () -> Unit
){
    BackHandler {
        navController.navigate(Screen.OnBoarding1.route)
    }
    val customFont = FontFamily(
        Font(R.font.poppins_semibold, FontWeight.SemiBold)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "  Are You Ready To \n Start Your Journey \n  Of Mental Health?",
                color = Color.White,
                style = TextStyle(
                    fontFamily = customFont,
                    fontSize = 30.sp,

                    )
            )
        }


        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.62f)
            .background(
                Color(31, 31, 27),
                shape = RoundedCornerShape(topStart = 70.dp, topEnd = 70.dp)
            )
            .align(Alignment.BottomCenter)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ){

                Box(modifier = Modifier
                    .fillMaxSize(0.75f)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.mountain),
                        contentDescription =null,
                        modifier = Modifier.fillMaxSize()
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, start = 8.dp)
                        .clickable { onNext() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Let's begin",
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = customFont,
                            fontSize = 24.sp,

                            )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                    
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp),
                    horizontalArrangement = Arrangement.Center,

                    ) {

                    Icon(
                        modifier = Modifier
                            .size(15.dp)
                            .border(3.dp, Color.White, shape = CircleShape)
                            .clickable {
                                navigateToBoarding1()
                            },
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = null,
                        tint = Color(31, 31, 27)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        modifier = Modifier
                            .size(15.dp)
                            .border(3.dp, Color.White, shape = CircleShape),
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = null,
                        tint = Color.White
                    )


                }

            }

        }
    }

}