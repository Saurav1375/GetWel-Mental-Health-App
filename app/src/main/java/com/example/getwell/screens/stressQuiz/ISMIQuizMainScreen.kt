package com.example.getwell.screens.stressQuiz


import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.di.Injection
import com.example.getwell.domain.provider.StressQuizProvider
import com.example.getwell.screens.customFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun ISMIQuizMainScreen(
    navController: NavHostController,
    userData: UserData?,
    onSubmit: () -> Unit
){
    val ISMIQuizItemList = listOf(
        QuizItem(
            id = 1,
            desc = "Stereotypes about the mentally ill apply to me.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "In general, I am able to live my life the way I want to.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "Negative stereotypes about mental illness keep me isolated from the ‘normal’ world.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "I feel out of place in the world because I have a mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "Being around people who don’t have a mental illness makes me feel out of place or inadequate.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "People without mental illness could not possibly understand me.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "Nobody would be interested in getting close to me because I have a mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "I can’t contribute anything to society because I have a mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "I can have a good, fulfilling life, despite my mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        )
    )



    var finalISMIQuizList =listOf(
        QuizItem(
            id = 1,
            desc = "Stereotypes about the mentally ill apply to me.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "In general, I am able to live my life the way I want to.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "Negative stereotypes about mental illness keep me isolated from the ‘normal’ world.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "I feel out of place in the world because I have a mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "Being around people who don’t have a mental illness makes me feel out of place or inadequate.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "People without mental illness could not possibly understand me.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "Nobody would be interested in getting close to me because I have a mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "I can’t contribute anything to society because I have a mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "I can have a good, fulfilling life, despite my mental illness.",
            answers = listOf("Strongly disagree", "Disagree", "Agree", "Strongly agree"),
            selectedOption = -1
        )
    )

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    var currentId by remember{ mutableStateOf(0)}
    val stressQuizProvider  = StressQuizProvider(Injection.instance())
    val selectedIndex = remember{ mutableStateOf(-1) }

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
                    text = "ISMI-9 Stigma Test",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
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
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                val currentQuizItem = ISMIQuizItemList[currentId]
                QUizTopProgress(questionIndex = currentQuizItem.id, totalQuestionCount = 9)
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(400.dp, 210.dp)
                        .background(
                            Color(31, 31, 37), RoundedCornerShape(15.dp)
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {

                        Text(
                            text = "Q${currentQuizItem.id}.",
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = customFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = currentQuizItem.desc,
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = customFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                            )
                        )
                    }


                }

                QuizItemOptions(selectedIndex = selectedIndex, n = 3, answers = ISMIQuizItemList[currentId].answers){option->
                    finalISMIQuizList[currentId].selectedOption = option
                    println(finalISMIQuizList[currentId])
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if(currentId > 0){
                            currentId--
                            selectedIndex.value = finalISMIQuizList[currentId].selectedOption
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            imageVector = Icons.Default.KeyboardDoubleArrowLeft,
                            contentDescription = "previousArrow",
                            tint = Color.White
                        )

                    }
                    Text(
                        text = "${currentQuizItem.id} of 9",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    )

                    IconButton(onClick = {
                        if(currentId < finalISMIQuizList.size - 1){ // Ensure we don't go out of bounds
                                currentId++
                                selectedIndex.value = finalISMIQuizList[currentId].selectedOption

                        } else {
                            val answeredCount = finalISMIQuizList.count { it.selectedOption != -1 }
                            if (userData != null && answeredCount != 0) {
                                Toast.makeText(
                                    context,
                                    "Thanks for taking the Test",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onSubmit()
                                navController.navigate(Screen.HomeScreen.route)
                                stressQuizProvider.addQuizDataToFirestore(
                                    id = "ISMI",
                                    list = finalISMIQuizList,
                                    userEmail = userData.emailId
                                )
                            }

                            else{
                                Toast.makeText(
                                    context,
                                    "You have not answered any question, test will not be considered",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(Screen.HomeScreen.route)
                            }
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            imageVector = Icons.Default.KeyboardDoubleArrowRight,
                            contentDescription = "nextArrow",
                            tint = Color.White
                        )

                    }

                }

            }
        }
    }
}
