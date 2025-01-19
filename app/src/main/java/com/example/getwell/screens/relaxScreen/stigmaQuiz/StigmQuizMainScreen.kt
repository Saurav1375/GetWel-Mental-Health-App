package com.example.getwell.screens.relaxScreen.stigmaQuiz

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.data.Screen
import com.example.getwell.screens.customFont

data class QuizModel(
    val title: String = "Quiz 1",
    val description: String = LoremIpsum(23).toString(),
    val bannerImage: Int = R.drawable.quizbanner1,
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StigmaQuizMainScreen(
    navController: NavHostController,
    ) {
    BackHandler {
        navController.navigate(Screen.RelaxScreen.route)
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val listQuizModels = listOf(
        QuizModel(
            title = "Quiz 1: Mental Health Basics",
            description = "Test your knowledge on the fundamental concepts of mental health, including common disorders and their symptoms."
        ),
        QuizModel(
            title = "Quiz 2: Understanding Stigma",
            description = "Explore the stigma surrounding mental health issues and how it affects individuals seeking help."
        ),
        QuizModel(
            title = "Quiz 3: Coping Strategies",
            description = "Learn about effective coping strategies and techniques to manage stress and improve mental well-being."
        ),
        QuizModel(
            title = "Quiz 4: Mental Health Myths",
            description = "Identify common myths and misconceptions about mental health and discover the truths behind them."
        ),
        QuizModel(
            title = "Quiz 5: Stress Management",
            description = "Evaluate your understanding of stress, its effects, and various methods for managing it effectively."
        ),
        QuizModel(
            title = "Quiz 6: Emotional Resilience",
            description = "Assess your knowledge on emotional resilience and how it can help individuals cope with life's challenges."
        ),
        QuizModel(
            title = "Quiz 7: Mental Health Resources",
            description = "Familiarize yourself with available mental health resources, support systems, and how to access them."
        )
    )

    Questions.quizzes = generateQuizzes(
        Questions.questionBank, 7, 10
    )
    val quizzes =  Questions.quizzes

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
                    text = "Break the Stigma",
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
            Image(
                painter = painterResource(id = R.drawable.chatbg9),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),

                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {

                quizzes.forEachIndexed { index, quiz ->
                    QuizModelCard(
                        title =  listQuizModels[index].title,
                        description = listQuizModels[index].description,
                        bannerImage = listQuizModels[index].bannerImage,

                    ){
                        navController.navigate("${Screen.QuizQuestionsScreen.route}/${quiz.id}")
                    }
                }

            }
        }
    }
}


@Composable
fun QuizModelCard(
    title: String,
    description: String,
    bannerImage: Int,
    questionsSize: String = "10",
    certification: String = "Break Stigma",
    onStartQuizClick: () -> Unit,
) {
    var viewInstructions by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (!viewInstructions) {
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                        .clipToBounds()
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        painter = painterResource(id = bannerImage),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,

                        )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(63, 69, 83), shape = RoundedCornerShape(20.dp))
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.clipToBounds(),
                                painter = painterResource(id = R.drawable.quizmark),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "${questionsSize}\nQuestions",
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = customFont,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            )

                        }
                    }
                    Spacer(modifier = Modifier.width(24.dp))

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(63, 69, 83), shape = RoundedCornerShape(20.dp))
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.clipToBounds(),
                                painter = painterResource(id = R.drawable.certifications),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = certification,
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = customFont,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            )

                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = TextStyle(
                        color = Color(181, 244, 255),
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                )
            }

            else{
                Text(
                    text = "Quiz Instructions",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp,

                        )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Quiz Instructions\n" +
                            "Welcome to the Mental Health Quiz Series!\n" +
                            "\n" +
                            " 1. Read Each Question: Understand what’s being asked before answering.\n" +
                            " 2. Select the Best Answer: Choose the option you think is correct. Only one answer is right for each question.\n" +
                            " 3. Take Your Time: Complete the quiz at your own pace—there's no time limit.\n" +
                            " 4. Review Your Answers: After finishing, check which answers were correct.\n" +
                            " 5. Reflect and Learn: Use this opportunity to deepen your understanding of mental health.\n" +
                            "Good luck!",
                    style = TextStyle(
                        color = Color(181, 244, 255),
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                    )
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Box(
//                    modifier = Modifier
//                        .background(Color.Transparent, shape = RoundedCornerShape(15.dp))
//                        .border(BorderStroke(2.dp, Color(63, 69, 83)), RoundedCornerShape(15.dp))
//                        .height(45.dp)
//                        .fillMaxWidth(0.5f)
//                        .clickable { onViewSummaryClick() },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "View Summary",
//                        style = TextStyle(
//                            color = Color.White,
//                            fontFamily = customFont,
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 15.sp,
//
//                            )
//                    )
//
//
//                }
                Box(
                    modifier = Modifier
                        .background(
                            if (!viewInstructions) Color(63, 69, 83) else Color.Transparent,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .border(
                            BorderStroke(if (viewInstructions) 2.dp else 0.dp, Color(63, 69, 83)),
                            RoundedCornerShape(15.dp)
                        )
                        .height(45.dp)
                        .fillMaxWidth(0.5f)
                        .clickable { viewInstructions = !viewInstructions },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = if(!viewInstructions) "View Instructions" else "View Summary",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,

                            )
                    )


                }
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Color.Green,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .height(45.dp)
                        .fillMaxWidth()
                        .clickable { onStartQuizClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Start Quiz",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,

                            )
                    )


                }

            }

        }
    }

}


@Composable
fun InstructionCard(
    instructions: String,
    isEnabled: MutableState<Boolean>,
    onStartQuizClick: () -> Unit,
    onViewSummaryClick: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(510.dp)
            .background(Color(31, 31, 37), shape = RoundedCornerShape(20.dp))
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Instructions",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 23.sp,

                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = instructions,
                style = TextStyle(
                    color = Color(181, 244, 255),
                    fontFamily = customFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                )
            )
            Spacer(modifier = Modifier.height(32.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(15.dp))
                        .border(BorderStroke(2.dp, Color(63, 69, 83)), RoundedCornerShape(15.dp))
                        .height(45.dp)
                        .fillMaxWidth(0.5f)
                        .clickable { onViewSummaryClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "View Summary",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,

                            )
                    )


                }
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .background(
                            if (isEnabled.value) Color.Green else Color.Transparent,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .height(45.dp)
                        .fillMaxWidth()
                        .clickable(
                            enabled = isEnabled.value
                        ) { onStartQuizClick() }
                        .border(
                            BorderStroke(
                                if (isEnabled.value) 0.dp else 3.dp,
                                Color(63, 69, 83)
                            ),
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isEnabled.value) "Start Quiz" else "Completed",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,

                            )
                    )

                }

            }
        }

    }

}

