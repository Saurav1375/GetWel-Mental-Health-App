package com.example.getwell.screens.settingspage.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getwell.R
import com.example.getwell.screens.customFont

data class ItemData(
    val title: String,
    val value: String,
)

@Composable
fun AboutView(){

    val listItems = listOf(
        ItemData(
            title = "Version",
            value = "1.0.0"
        ),
        ItemData(
            title = "Download size",
            value = "135 MB"
        ),
        ItemData(
            title = "Required OS",
            value = "Android 8.0 and up"
        ),
        ItemData(
            title = "Developed by",
            value = "Team August"
        ),
        ItemData(
            title = "Released on",
            value = "10 Nov 2024"
        ),
    )
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp)
            .verticalScroll(scrollState)
    ) {

       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(
               modifier = Modifier.padding(top = 32.dp),
               text = "About this app\n",
               style = TextStyle(
                   fontWeight = FontWeight.SemiBold,
                   fontSize = 20.sp,
                   color = Color.White
               )
           )

           Image(
               painter = painterResource(id = R.drawable.iitwhite),
               contentDescription =null,
               modifier = Modifier.size(70.dp)
           )

       }
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text =
                    "In our increasingly aware society, stigma around mental health still poses a significant barrier to care.\n\nTo address this, we’ve created a mobile app dedicated to fostering understanding and support for those facing mental health challenges.\n\n" +
                    "Mental health stigma leads to discrimination and isolation, making it hard for individuals to seek help. Our app empowers users by providing accessible resources, encouraging open discussions, and dispelling myths about mental health.\n\nWe aim to create an inclusive space where everyone can prioritise their wellbeing without fear of judgement.\n\n" +
                    "Key Features:\n" +
                    "Tailored Support: Users complete surveys to identify their mental health needs, and the Mental Health Tracker helps monitor their journey.\n\n" +
                    "Community Engagement: The Chat Room fosters connections and shared experiences, reducing feelings of isolation.\n\n" +
                    "Educational Resources: A curated library of content and daily mental health facts promotes understanding and awareness.\n\n" +
                    "Community Building: Users can join peer support groups, cultivating a sense of belonging.\n\n" +
                    "User-Centric Design: Built from user feedback, the app evolves to meet community needs and includes offline accessibility.\n\n" +
                    "This app aims to transform the mental health conversation and create a supportive environment. Together, we strive to encourage individuals to seek help and embrace their mental wellbeing without fear or shame.",
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = Color.White
            )

        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color(111,111,111))
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "More info",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
                ){

                Box(modifier = Modifier
                    .size(60.dp, 40.dp)
                    .background(Color.White),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = "3+",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.Black
                        )
                    )

                }



                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Rated for 3+",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "User Interacts",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color(200,200,200)
                        )
                    )

                }


            }


        }
        Spacer(modifier = Modifier.height(32.dp))

        Divider(modifier = Modifier.fillMaxWidth(), color = Color(111,111,111))

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "App info",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )

            listItems.forEach {item->
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.body1.fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color(200,200,200)
                        )
                    )
                    Text(
                        text = item.value,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(32.dp))






    }
}