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
fun BAIQuizMainScreen(
    navController: NavHostController,
    userData: UserData?,
    onSubmit: () -> Unit
){
    val BAIQuizItemList = listOf(
        QuizItem(
            id = 1,
            desc = "Numbness or tingling",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "Feeling hot",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "Wobbliness in legs",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "Unable to relax",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "Fear of worst happening",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "Dizzy or lightheaded",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "Heart pounding / racing",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "Unsteady",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "Terrified or afraid",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 10,
            desc = "Nervous",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 11,
            desc = "Feeling of choking",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 12,
            desc = "Hands trembling",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 13,
            desc = "Shaky / unsteady",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 14,
            desc = "Fear of losing control",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 15,
            desc = "Difficulty in breathing",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 16,
            desc = "Fear of dying",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 17,
            desc = "Scared",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 18,
            desc = "Indigestion",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 19,
            desc = "Faint / lightheaded",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 20,
            desc = "Face flushed",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 21,
            desc = "Hot / cold sweats",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        )
    )



    var finalBAIQuizList = listOf(
        QuizItem(
            id = 1,
            desc = "Numbness or tingling",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "Feeling hot",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "Wobbliness in legs",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "Unable to relax",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "Fear of worst happening",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "Dizzy or lightheaded",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "Heart pounding / racing",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "Unsteady",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "Terrified or afraid",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 10,
            desc = "Nervous",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 11,
            desc = "Feeling of choking",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 12,
            desc = "Hands trembling",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 13,
            desc = "Shaky / unsteady",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 14,
            desc = "Fear of losing control",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 15,
            desc = "Difficulty in breathing",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 16,
            desc = "Fear of dying",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 17,
            desc = "Scared",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 18,
            desc = "Indigestion",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 19,
            desc = "Faint / lightheaded",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 20,
            desc = "Face flushed",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
            selectedOption = -1
        ),
        QuizItem(
            id = 21,
            desc = "Hot / cold sweats",
            answers = listOf("Not at all", "Mildly, but it didn’t bother me much", "Moderately – it wasn’t pleasant at times", "Severely – it bothered me a lot"),
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
                    text = "Beck-Anxiety Inventory",
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
                val currentQuizItem = BAIQuizItemList[currentId]
                QUizTopProgress(questionIndex = currentQuizItem.id, totalQuestionCount = 21)
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

                QuizItemOptions(selectedIndex = selectedIndex, n = 3, answers = BAIQuizItemList[currentId].answers){option->
                    finalBAIQuizList[currentId].selectedOption = option
                    println(finalBAIQuizList[currentId])
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
                            selectedIndex.value = finalBAIQuizList[currentId].selectedOption
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
                        text = "${currentQuizItem.id} of 21",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    )

                    IconButton(onClick = {
                        if(currentId < finalBAIQuizList.size - 1){ // Ensure we don't go out of bounds

                            if(selectedIndex.value == -1){
                                Toast.makeText(
                                    context,
                                    "Please select an option before moving forward",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                currentId++
                                selectedIndex.value = finalBAIQuizList[currentId].selectedOption
                            }
                        } else {
                            if (userData != null) {
                                Toast.makeText(
                                    context,
                                    "Thanks for taking the Quiz",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onSubmit()
                                navController.navigate(Screen.HomeScreen.route)
                                stressQuizProvider.addQuizDataToFirestore(
                                    id = "BAI",
                                    list = finalBAIQuizList,
                                    userEmail = userData.emailId
                                )
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
