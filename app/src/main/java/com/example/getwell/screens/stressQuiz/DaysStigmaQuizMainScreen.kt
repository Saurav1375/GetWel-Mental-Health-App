package com.example.getwell.screens.stressQuiz

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.di.Injection
import com.example.getwell.domain.provider.StressQuizProvider
import com.example.getwell.screens.customFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun DaysStigmaQuizMainScreen(
    navController: NavHostController,
    userData: UserData?,
    onSubmit: () -> Unit
){
    val DaysQuizItemList =  listOf(
        QuizItem(
            id = 1,
            desc = "There are effective medications for [mental illnesses] that allow people to return to normal and productive lives. (Treatability)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "I don’t think that it is possible to have a normal relationship with someone with [a mental illness]. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "I would find it difficult to trust someone with [a mental illness]. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "People with [mental illnesses] tend to neglect their appearance. (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "It would be difficult to have a close meaningful relationship with someone with [a mental illness]. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "I feel anxious and uncomfortable when I’m around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "It is easy for me to recognize the symptoms of [mental illnesses]. (Visibility)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "There are no effective treatments for [mental illnesses]. (Treatability; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "I probably wouldn’t know that someone has [a mental illness] unless I was told. (Visibility; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 10,
            desc = "A close relationship with someone with [a mental illness] would be like living on an emotional roller coaster. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 11,
            desc = "There is little that can be done to control the symptoms of [mental illness]. (Treatability; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 12,
            desc = "I think that a personal relationship with someone with [a mental illness] would be too demanding. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 13,
            desc = "Once someone develops [a mental illness], he or she will never be able to fully recover from it. (Recovery; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 14,
            desc = "People with [mental illnesses] ignore their hygiene, such as bathing and using deodorant. (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 15,
            desc = "[Mental illnesses] prevent people from having normal relationships with others. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 16,
            desc = "I tend to feel anxious and nervous when I am around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 17,
            desc = "When talking with someone with [a mental illness], I worry that I might say something that will upset him or her. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 18,
            desc = "I can tell that someone has [a mental illness] by the way he or she acts. (Visibility)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 19,
            desc = "People with [mental illnesses] do not groom themselves properly. (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 20,
            desc = "People with [mental illnesses] will remain ill for the rest of their lives. (Recovery; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 21,
            desc = "I don’t think that I can really relax and be myself when I’m around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 22,
            desc = "When I am around someone with [a mental illness] I worry that he or she might harm me physically. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 23,
            desc = "Psychiatrists and psychologists have the knowledge and skills needed to effectively treat [mental illnesses]. (Professional Efficacy)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 24,
            desc = "I would feel unsure about what to say or do if I were around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 25,
            desc = "I feel nervous and uneasy when I’m near someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 26,
            desc = "I can tell that someone has [a mental illness] by the way he or she talks. (Visibility)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 27,
            desc = "People with [mental illnesses] need to take better care of their grooming (bathe, clean teeth, use deodorant). (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 28,
            desc = "Mental health professionals, such as psychiatrists and psychologists, can provide effective treatments for [mental illnesses]. (Professional Efficacy)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        )
    )



    var finalDaysQuizList = listOf(
        QuizItem(
            id = 1,
            desc = "There are effective medications for [mental illnesses] that allow people to return to normal and productive lives. (Treatability)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "I don’t think that it is possible to have a normal relationship with someone with [a mental illness]. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "I would find it difficult to trust someone with [a mental illness]. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "People with [mental illnesses] tend to neglect their appearance. (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "It would be difficult to have a close meaningful relationship with someone with [a mental illness]. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "I feel anxious and uncomfortable when I’m around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "It is easy for me to recognize the symptoms of [mental illnesses]. (Visibility)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "There are no effective treatments for [mental illnesses]. (Treatability; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "I probably wouldn’t know that someone has [a mental illness] unless I was told. (Visibility; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 10,
            desc = "A close relationship with someone with [a mental illness] would be like living on an emotional roller coaster. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 11,
            desc = "There is little that can be done to control the symptoms of [mental illness]. (Treatability; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 12,
            desc = "I think that a personal relationship with someone with [a mental illness] would be too demanding. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 13,
            desc = "Once someone develops [a mental illness], he or she will never be able to fully recover from it. (Recovery; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 14,
            desc = "People with [mental illnesses] ignore their hygiene, such as bathing and using deodorant. (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 15,
            desc = "[Mental illnesses] prevent people from having normal relationships with others. (Relationship Disruption)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 16,
            desc = "I tend to feel anxious and nervous when I am around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 17,
            desc = "When talking with someone with [a mental illness], I worry that I might say something that will upset him or her. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 18,
            desc = "I can tell that someone has [a mental illness] by the way he or she acts. (Visibility)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 19,
            desc = "People with [mental illnesses] do not groom themselves properly. (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 20,
            desc = "People with [mental illnesses] will remain ill for the rest of their lives. (Recovery; reverse-scored)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 21,
            desc = "I don’t think that I can really relax and be myself when I’m around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 22,
            desc = "When I am around someone with [a mental illness] I worry that he or she might harm me physically. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 23,
            desc = "Psychiatrists and psychologists have the knowledge and skills needed to effectively treat [mental illnesses]. (Professional Efficacy)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 24,
            desc = "I would feel unsure about what to say or do if I were around someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 25,
            desc = "I feel nervous and uneasy when I’m near someone with [a mental illness]. (Anxiety)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 26,
            desc = "I can tell that someone has [a mental illness] by the way he or she talks. (Visibility)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 27,
            desc = "People with [mental illnesses] need to take better care of their grooming (bathe, clean teeth, use deodorant). (Hygiene)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
            selectedOption = -1
        ),
        QuizItem(
            id = 28,
            desc = "Mental health professionals, such as psychiatrists and psychologists, can provide effective treatments for [mental illnesses]. (Professional Efficacy)",
            answers = listOf("Completely Disagree", "Mostly Disagree", "Somewhat Disagree", "Neutral", "Somewhat Agree", "Mostly Agree", "Completely Agree"),
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
                    text = "Day's Mental Stigma Scale",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
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
                val currentQuizItem = DaysQuizItemList[currentId]
                QUizTopProgress(questionIndex = currentQuizItem.id, totalQuestionCount = 28)
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

                QuizItemOptions(selectedIndex = selectedIndex, n = 6, answers = DaysQuizItemList[currentId].answers){option->
                    finalDaysQuizList[currentId].selectedOption = option
                    println(finalDaysQuizList[currentId])
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
                            selectedIndex.value = finalDaysQuizList[currentId].selectedOption
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
                        text = "${currentQuizItem.id} of 28",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    )

                    IconButton(onClick = {
                        if(currentId < finalDaysQuizList.size - 1){ // Ensure we don't go out of bounds

                            if(selectedIndex.value == -1){
                                Toast.makeText(
                                    context,
                                    "Please select an option before moving forward",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                currentId++
                                selectedIndex.value = finalDaysQuizList[currentId].selectedOption
                            }
                        } else {
                            if (userData != null) {
                                Toast.makeText(
                                    context,
                                    "Thanks for taking the Test",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onSubmit()
                                navController.navigate(Screen.HomeScreen.route)
                                stressQuizProvider.addQuizDataToFirestore(
                                    id = "Days",
                                    list = finalDaysQuizList,
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
