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
fun DSSQuizMainScreen(
    navController: NavHostController,
    userData: UserData?,
    onSubmit: () -> Unit
) {
    val DSSQuizItemList = listOf(
        QuizItem(id = 1, desc = "I found it hard to wind down"),
        QuizItem(id = 2, desc = "I was aware of dryness of my mouth"),
        QuizItem(id = 3, desc = "I couldn’t seem to experience any positive feeling at all"),
        QuizItem(
            id = 4,
            desc = "I experienced breathing difficulty (e.g. excessively rapid breathing, breathlessness in the absence of physical exertion)"
        ),
        QuizItem(id = 5, desc = "I found it difficult to work up the initiative to do things"),
        QuizItem(id = 6, desc = "I tended to over-react to situations"),
        QuizItem(id = 7, desc = "I experienced trembling (e.g. in the hands)"),
        QuizItem(id = 8, desc = "I felt that I was using a lot of nervous energy"),
        QuizItem(
            id = 9,
            desc = "I was worried about situations in which I might panic and make a fool of myself"
        ),
        QuizItem(id = 10, desc = "I felt that I had nothing to look forward to"),
        QuizItem(id = 11, desc = "I found myself getting agitated"),
        QuizItem(id = 12, desc = "I found it difficult to relax"),
        QuizItem(id = 13, desc = "I felt down-hearted and blue"),
        QuizItem(
            id = 14,
            desc = "I was intolerant of anything that kept me from getting on with what I was doing"
        ),
        QuizItem(id = 15, desc = "I felt I was close to panic"),
        QuizItem(id = 16, desc = "I was unable to become enthusiastic about anything"),
        QuizItem(id = 17, desc = "I felt I wasn’t worth much as a person"),
        QuizItem(id = 18, desc = "I felt that I was rather touchy"),
        QuizItem(
            id = 19,
            desc = "I was aware of the action of my heart in the absence of physical exertion (e.g. sense of heart rate increase, heart missing a beat)"
        ),
        QuizItem(id = 20, desc = "I felt scared without any good reason"),
        QuizItem(id = 21, desc = "I felt that life was meaningless")
    )

    var finalDSSQuizList = listOf(
        QuizItem(id = 1, desc = "I found it hard to wind down"),
        QuizItem(id = 2, desc = "I was aware of dryness of my mouth"),
        QuizItem(id = 3, desc = "I couldn’t seem to experience any positive feeling at all"),
        QuizItem(
            id = 4,
            desc = "I experienced breathing difficulty (e.g. excessively rapid breathing, breathlessness in the absence of physical exertion)"
        ),
        QuizItem(id = 5, desc = "I found it difficult to work up the initiative to do things"),
        QuizItem(id = 6, desc = "I tended to over-react to situations"),
        QuizItem(id = 7, desc = "I experienced trembling (e.g. in the hands)"),
        QuizItem(id = 8, desc = "I felt that I was using a lot of nervous energy"),
        QuizItem(
            id = 9,
            desc = "I was worried about situations in which I might panic and make a fool of myself"
        ),
        QuizItem(id = 10, desc = "I felt that I had nothing to look forward to"),
        QuizItem(id = 11, desc = "I found myself getting agitated"),
        QuizItem(id = 12, desc = "I found it difficult to relax"),
        QuizItem(id = 13, desc = "I felt down-hearted and blue"),
        QuizItem(
            id = 14,
            desc = "I was intolerant of anything that kept me from getting on with what I was doing"
        ),
        QuizItem(id = 15, desc = "I felt I was close to panic"),
        QuizItem(id = 16, desc = "I was unable to become enthusiastic about anything"),
        QuizItem(id = 17, desc = "I felt I wasn’t worth much as a person"),
        QuizItem(id = 18, desc = "I felt that I was rather touchy"),
        QuizItem(
            id = 19,
            desc = "I was aware of the action of my heart in the absence of physical exertion (e.g. sense of heart rate increase, heart missing a beat)"
        ),
        QuizItem(id = 20, desc = "I felt scared without any good reason"),
        QuizItem(id = 21, desc = "I felt that life was meaningless")
    )

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    var currentId by remember { mutableStateOf(0) }
    val stressQuizProvider = StressQuizProvider(Injection.instance())
    val selectedIndex = remember { mutableStateOf(-1) }

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
                    text = "DASS QUIZ",
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


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                val currentQuizItem = DSSQuizItemList[currentId]
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
                QuizItemOptions(selectedIndex = selectedIndex, n = 3) { option ->
                    finalDSSQuizList[currentId].selectedOption = option
                    println(finalDSSQuizList[currentId])
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (currentId > 0) {
                            currentId--
                            selectedIndex.value = finalDSSQuizList[currentId].selectedOption
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
                        if (currentId < finalDSSQuizList.size - 1) { // Ensure we don't go out of bounds

                            if (selectedIndex.value == -1) {
                                Toast.makeText(
                                    context,
                                    "Please select an option before moving forward",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                currentId++
                                selectedIndex.value = finalDSSQuizList[currentId].selectedOption
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
                                    id = "DASS21",
                                    list = finalDSSQuizList,
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

@Composable
fun QuizItemOptions(selectedIndex: MutableState<Int>, answers: List<String> = emptyList(),
                    n: Int, onItemClick: (Int) -> Unit ) {
    val options = (0..n).toList()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            options.forEachIndexed { index, it ->
            Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(8.dp)
                        .border(
                            BorderStroke(
                                2.dp,
                                color = Color(143,189,200)
                            ),
                            RoundedCornerShape(16.dp)
                        )
                        .background(
                            if (it == selectedIndex.value) Color(143,189,200) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            onItemClick(it)
                            selectedIndex.value = it
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(answers.isEmpty()) it.toString() else answers[index],
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = TextStyle(
                            color = if (it == selectedIndex.value) Color.Black
                            else Color(143, 189, 200),
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )

                    )

                }
            }
        }
    }

}