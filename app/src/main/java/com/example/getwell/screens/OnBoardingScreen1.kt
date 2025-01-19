package com.example.getwell.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getwell.R

@Composable
fun OnBoardingScreen1(
    navigateToBoarding2: () -> Unit
){
    val context = LocalContext.current
    BackHandler {
        (context as? Activity)?.finishAffinity()
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.iitwhite),
                contentDescription =null,
                modifier = Modifier.size(110.dp)
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = " Welcome To \n     GetWel+",
                color = Color.White,
                style = TextStyle(
                    fontFamily = customFont,
                    fontSize = 35.sp,

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
                        painter = painterResource(id = R.drawable.hands),
                        contentDescription =null,
                        modifier = Modifier.fillMaxSize()
                    )

                }
                Text(
                    modifier = Modifier,
                    text = "Next",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontSize = 30.sp,

                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp),
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Icon(
                        modifier = Modifier
                            .size(15.dp)
                            .border(3.dp, Color.White, shape = CircleShape),
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        modifier = Modifier
                            .size(15.dp)
                            .border(3.dp, Color.White, shape = CircleShape)
                            .clickable {
                                navigateToBoarding2()
                            },
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = null,
                        tint = Color(31, 31, 27)
                    )

                }

            }

        }
    }

}