package com.example.getwell.screens.settingspage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.authSystem.AuthViewModel
import com.example.getwell.authSystem.UserData
import com.example.getwell.screens.customFont
import com.example.getwell.screens.settingspage.about.AboutView
import com.example.getwell.screens.settingspage.account.AccountScreen
import com.example.getwell.screens.settingspage.avatar.AvatarChangeScreen
import com.example.getwell.screens.settingspage.avatar.AvatarViewModel
import com.example.getwell.screens.settingspage.datastorage.StorageView
import com.example.getwell.screens.settingspage.privacy.PrivacyView
import com.example.getwell.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsItemView(
    navController: NavHostController,
    userData: UserData?,
    itemId: Int,
    authUiClient: AuthUiClient,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val avatarViewModel = remember { AvatarViewModel() }

    // Add AvatarChangeScreen to your profile layout


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
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
                    text = when (itemId) {
                        1 -> "Account"
                        2 -> "Privacy"
                        3 -> "Avatar"
                        4 -> "Chats"
                        5 -> "Notifications"
                        6 -> "Data and Storage"
                        7 -> "About"

                        else -> ""
                    },

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
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (itemId) {
                    1 -> AccountScreen(
                        userData = userData,
                        authUiClient = authUiClient,
                        navController = navController,
                        viewModel = authViewModel,
                        homeViewModel = homeViewModel
                    )
                    2 -> PrivacyView()
                    3->AvatarChangeScreen(avatarViewModel)
                    6 -> StorageView()
                    7 -> AboutView()


                }
            }
        }


    }
}