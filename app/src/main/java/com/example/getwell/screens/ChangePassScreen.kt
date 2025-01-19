package com.example.getwell.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getwell.R


@Composable
fun ResetPassScreen(
    onChangePass : () -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),

            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = painterResource(id = R.drawable.cat__1_),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(1.2f),
                contentScale = ContentScale.Fit // Fit the image within the width
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier,
                text = "Reset\nPassword?",
                color = Color.White,
                style = TextStyle(
                    fontFamily = displayLargeFontFamily,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//
//                Text(
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .clickable { onRegister() },
//                    text = "Don’t Worry it happens. Please Enter an email  or Mobile associated with your account",
//                    color = Color.White,
//                    style = TextStyle(
//                        fontFamily = displayLargeFontFamily,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Normal,
//
//                        )
//                )
//            }
            Spacer(modifier = Modifier.height(16.dp))
            val newPass = mutableStateOf("")
            val confirmPass = mutableStateOf("")

            CustomTextField(title = "New Password", icon = Icons.Default.Lock, value = newPass , onValueChange = {
                newPass.value = it
            })

            CustomTextField(title = "Confirm Password", icon = Icons.Default.Lock, value = confirmPass , onValueChange = {
                confirmPass.value = it
            })


            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(Color(0, 50, 87), shape = RoundedCornerShape(10.dp))
                    .clickable { onChangePass() },

                ){
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Change Password",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = displayLargeFontFamily,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )
            }

        }
    }
}


