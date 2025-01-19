package com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.WellnessCentersUiState
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.WellnessCentersViewModel
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.components.WellnessCenterCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WellnessCentersScreen(
    navController: NavHostController,
    locationViewModel: WellnessCentersViewModel
) {
    locationViewModel.loadWellnessCenters()
    BackHandler {
        navController.navigate(Screen.EducationScreen.route)
    }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

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
                        text = "Nearby Wellness Centers",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
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
            WellnessCenters(viewModel = locationViewModel)

        }
    }
}
@Composable
fun WellnessCenters(
    viewModel: WellnessCentersViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

            when (val state = uiState) {
                is WellnessCentersUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is WellnessCentersUiState.Success -> {
                    if (state.centers.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "No wellness centers found nearby",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadWellnessCenters() }) {
                                Text("Search Again")
                            }
                        }


                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(bottom = 100.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            items(state.centers) { center ->
                                WellnessCenterCard(wellnessCenter = center)
                            }
                        }
                    }
                }
                is WellnessCentersUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadWellnessCenters() }) {
                            Text("Retry")
                        }
                    }
                }
            }



    }
}