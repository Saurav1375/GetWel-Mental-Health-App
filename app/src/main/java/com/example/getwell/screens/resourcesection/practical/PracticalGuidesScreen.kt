package com.example.getwell.screens.resourcesection.practical


import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import com.example.getwell.ChatroomActivity
import com.example.getwell.R
import com.example.getwell.data.Screen
import com.example.getwell.data.navItemList
import com.example.getwell.data.resourceItemList
import com.example.getwell.screens.BottomNavigationBar
import com.example.getwell.screens.customFont
import com.example.getwell.screens.resourcesection.education.ResourceItemView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PracticalGuidesScreen(
    navController: NavHostController
) {
    BackHandler {
        navController.navigate(Screen.EducationScreen.route)
    }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val viewModel = remember { VideoFeedViewModel() }

    LaunchedEffect(Unit) {
        // Add sample videos
        val sampleVideos = listOf(
            "https://youtu.be/bUlBZuQ2c0Q?si=a_KEE_9WYkUBXYOY", // Stigma About Mental Health
            "https://youtu.be/VQoiz4wfV_c?si=VvIXa5V_nHZJ4hLO",
            "https://youtu.be/vYrkcZTFllk?si=kGMVlacrHoJsfkMN",
            "https://youtu.be/bQl-n2cLTKs?si=m4vC7wjldIrP83CQ",
            "https://youtu.be/wJjwAMr_BEE?si=wi2iyqN7kCkj41b9",
            "https://youtu.be/l4V31iXtrWo?si=DC1B9rZUVobUeRF_",
            "https://youtu.be/48s8dGCgSzw?si=XFY-IbvrQZOepNt3",
            "https://youtu.be/Nz9eAaXRzGg?si=OQ_l1bx6KPyjOFI8", // Removing Stress
            "https://youtu.be/ZUoT89ceuCs?si=ar_LmXnHmQeEfk9_",
            "https://youtu.be/tTb3d5cjSFI?si=Z7fVs3i8zSu6HuXM",
            "https://youtu.be/WWloIAQpMcQ?si=r8wncV-CqiEu_n9a",
            "https://youtu.be/8jPQjjsBbIc?si=G2xHlYrxigWYs0hh",
            "https://youtu.be/BfdvbZFXbNA?si=LbvSt3obeIHwxQTR",
            "https://youtu.be/7DKXLasU4Kg?si=Pw8QSSzB3xeTgYUt",
            "https://youtu.be/tYddPTEfS_8?si=xzw_Hu9Fl-tamhuP", // Exercises to Remove Stress
            "https://youtu.be/6ijg6tpyxXg?si=YnClj_qqEwrdRlKf",
            "https://youtu.be/yqeirBfn2j4?si=ysDqS7uQft30zkgD",
            "https://youtu.be/odADwWzHR24?si=a9XgU3Og1UAtLc0I",
            "https://youtu.be/75PUjUsGsQQ?si=l9X0f1Y1_x-IjyJs",
            "https://youtu.be/7cqzSNgNo1M?si=oNaSYslcZmia-gOv",
            "https://youtu.be/8TuRYV71Rgo?si=HrGryvTQzpZIOnjz",
            "https://youtu.be/i-pazYyLSWQ?si=P7oQFbVO6hY8lb6P", // Stress Management Techniques
            "https://youtu.be/0fL-pn80s-c?si=aTSrp8nwU0ae1Dna",
            "https://youtu.be/HB1snh5ArVw?si=8t4bLFrKPMdLuecG",
            "https://youtu.be/Wh5HyJ1rxzk?si=f7d-WaCdfJhO0lTk",
            "https://youtu.be/15GaKTP0gFE?si=DYeSWEqq6F8C3LgR",
            "https://youtu.be/IqpCCnmwNVY?si=0w2t5RoaVYL84tm2",
            "https://youtu.be/HAUAd-gUDIU?si=y9TSwyB6gSNfDxgv",
            "https://youtu.be/-S4zKZV_dLE?si=h3uWxLdASkUcYs_9", // Life Stories
            "https://youtu.be/2EPnNOlxF8M?si=-nF-BFt7I3w_2adF",
            "https://youtu.be/DtPRg_Ibn-c?si=pebBFw3qsl7dO2z6",
            "https://youtu.be/JDDBetGR-PA?si=VKoNd5aNqhZWo9xb",
            "https://youtu.be/ssKSfQmIoLE?si=_6h3Egw6kkSfv1uf",
            "https://youtu.be/SVLNiL9SexU?si=GkbRnByD594-_oTR",
            "https://youtu.be/8g_kA2soWOo?si=awykozxEHE68gQ9O",
            // STIGMA ABOUT MENTAL HEALTH
            "https://youtu.be/bUlBZuQ2c0Q?si=a_KEE_9WYkUBXYOY",
            "https://youtu.be/VQoiz4wfV_c?si=VvIXa5V_nHZJ4hLO",
            "https://youtu.be/vYrkcZTFllk?si=kGMVlacrHoJsfkMN",
            "https://youtu.be/bQl-n2cLTKs?si=m4vC7wjldIrP83CQ",
            "https://youtu.be/wJjwAMr_BEE?si=wi2iyqN7kCkj41b9",
            "https://youtu.be/l4V31iXtrWo?si=DC1B9rZUVobUeRF_",
            "https://youtu.be/48s8dGCgSzw?si=XFY-IbvrQZOepNt3",
            "https://youtu.be/gy1iH_Gxn0Q?si=3sFRoJkGUFjp4Kz1",
            "https://youtu.be/WrbTbB9tTtA?si=SBzH1krhZGjw8GTw",
            "https://youtu.be/pcBbZxXh8Cc?si=lGiPcgCQVTGQUG5e",
            "https://youtu.be/ZdUz0tlKZ78?si=c5xamba93-dRv2rA",

            // REMOVING STRESS
            "https://youtu.be/Nz9eAaXRzGg?si=OQ_l1bx6KPyjOFI8",
            "https://youtu.be/ZUoT89ceuCs?si=ar_LmXnHmQeEfk9_",
            "https://youtu.be/tTb3d5cjSFI?si=Z7fVs3i8zSu6HuXM",
            "https://youtu.be/WWloIAQpMcQ?si=r8wncV-CqiEu_n9a",
            "https://youtu.be/8jPQjjsBbIc?si=G2xHlYrxigWYs0hh",
            "https://youtu.be/BfdvbZFXbNA?si=LbvSt3obeIHwxQTR",
            "https://youtu.be/7DKXLasU4Kg?si=Pw8QSSzB3xeTgYUt",
            "https://youtu.be/McCDfP5M878?si=ouOZ5rfGsPDT6BES",
            "https://youtu.be/PYHn37MO3WM?si=8RRHcHLluiBaUDsf",
            "https://youtu.be/R_yFAMtfv9c?si=hCoTCE-UbPzSaJeA",
            "https://youtu.be/X2zx6oimT_4?si=wKBYj-1-14SOr29O",

            // EXERCISES TO REMOVE STRESS
            "https://youtu.be/tYddPTEfS_8?si=xzw_Hu9Fl-tamhuP",
            "https://youtu.be/6ijg6tpyxXg?si=YnClj_qqEwrdRlKf",
            "https://youtu.be/yqeirBfn2j4?si=ysDqS7uQft30zkgD",
            "https://youtu.be/odADwWzHR24?si=a9XgU3Og1UAtLc0I",
            "https://youtu.be/75PUjUsGsQQ?si=l9X0f1Y1_x-IjyJs",
            "https://youtu.be/7cqzSNgNo1M?si=oNaSYslcZmia-gOv",
            "https://youtu.be/8TuRYV71Rgo?si=HrGryvTQzpZIOnjz",
            "https://youtu.be/kSZKIupBUuc?si=JBS1fXiRAF2PC0vu",
            "https://youtu.be/s7o2GUGwCI8?si=wGKK2utotHDZ_oN8",
            "https://youtu.be/m3-O7gPsQK0?si=A_FHLtnvc1b_5DXt",
            "https://youtu.be/S1zIHYS13yQ?si=Iic486aIq61bYeSM",

            // STRESS MANAGEMENT TECHNIQUES
            "https://youtu.be/i-pazYyLSWQ?si=P7oQFbVO6hY8lb6P",
            "https://youtu.be/0fL-pn80s-c?si=aTSrp8nwU0ae1Dna",
            "https://youtu.be/HB1snh5ArVw?si=8t4bLFrKPMdLuecG",
            "https://youtu.be/Wh5HyJ1rxzk?si=f7d-WaCdfJhO0lTk",
            "https://youtu.be/15GaKTP0gFE?si=DYeSWEqq6F8C3LgR",
            "https://youtu.be/IqpCCnmwNVY?si=0w2t5RoaVYL84tm2",
            "https://youtu.be/HAUAd-gUDIU?si=y9TSwyB6gSNfDxgv",
            "https://youtu.be/V3vhXQy48jo?si=MTEvfLftrywoKGdN",
            "https://youtu.be/wXiWaZHhX6s?si=5urJ6ZFr0GPPyLHG",
            "https://youtu.be/4_uty2-Y6aQ?si=U11xRAHANshElm-P",
            "https://youtu.be/ybnzd4zu8xs?si=hx1XJDd20TplYzxz",

            // LIFE STORIES
            "https://youtu.be/-S4zKZV_dLE?si=h3uWxLdASkUcYs_9",
            "https://youtu.be/2EPnNOlxF8M?si=-nF-BFt7I3w_2adF",
            "https://youtu.be/DtPRg_Ibn-c?si=pebBFw3qsl7dO2z6",
            "https://youtu.be/JDDBetGR-PA?si=VKoNd5aNqhZWo9xb",
            "https://youtu.be/ssKSfQmIoLE?si=_6h3Egw6kkSfv1uf",
            "https://youtu.be/SVLNiL9SexU?si=GkbRnByD594-_oTR",
            "https://youtu.be/8g_kA2soWOo?si=awykozxEHE68gQ9O",
            "https://youtu.be/PYbuB-Ateus?si=FsBaipx3plrt7deo",
            "https://youtu.be/Izy1TgMe-tI?si=1IiOp7DT0BTg2K2c",
            "https://youtu.be/-alCFVrKdzw?si=Ba0IqtNYg-Zoq1vP",
            "https://youtu.be/CBx4LFgARaY?si=2N9prXoGDgaHXztn"
        )

        sampleVideos.forEach { link ->
            viewModel.addVideoLink(link)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                backgroundColor = Color(31, 31, 37),
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
                        resourceItemList.forEach { item ->
                            ResourceItemView(item = item, navController = navController) {
                                navController.navigate(item.route)

                            }
                        }

                    }
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Practical Guides",
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
                    if (it.route == Screen.ChatroomScreen.route) {
                        val intent = Intent(context, ChatroomActivity::class.java)
                        context.startActivity(intent)

                    } else {
                        navController.navigate(it.route)
                    }
                }
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
            Column {

                    VideoFeed(viewModel = viewModel)



            }
        }
    }
}


