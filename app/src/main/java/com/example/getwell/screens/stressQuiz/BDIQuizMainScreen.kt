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
fun BDIQuizMainScreen(
    navController: NavHostController,
    userData: UserData?,
    onSubmit: () -> Unit
){
    val BDIQuizItemList = listOf(
        QuizItem(
            id = 1,
            desc = "I do not feel sad.",
            answers = listOf("I do not feel sad", "I feel sad", "I am sad all the time and I can't snap out of it.", "I am so sad and unhappy that I can't stand it."),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "I feel discouraged about the future.",
            answers = listOf("I am not particularly discouraged about the future", "I feel discouraged about the future", "I feel I have nothing to look forward to", "I feel the future is hopeless and that things cannot improve."),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "I feel I have failed more than the average person.",
            answers = listOf("I do not feel like a failure", "I feel I have failed more than the average person", "As I look back on my life, all I can see is a lot of failures", "I feel I am a complete failure as a person"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "I get as much satisfaction out of things as I used to.",
            answers = listOf("I get as much satisfaction out of things as I used to", "I don't enjoy things the way I used to", "I don't get real satisfaction out of anything anymore", "I am dissatisfied or bored with everything"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "I don't feel particularly guilty.",
            answers = listOf("I don't feel particularly guilty", "I feel guilty a good part of the time", "I feel quite guilty most of the time", "I feel guilty all of the time"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "I don't feel I am being punished.",
            answers = listOf("I don't feel I am being punished", "I feel I may be punished", "I expect to be punished", "I feel I am being punished"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "I don't feel disappointed in myself.",
            answers = listOf("I don't feel disappointed in myself", "I am disappointed in myself", "I am disgusted with myself", "I hate myself"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "I don't feel I am any worse than anybody else.",
            answers = listOf("I don't feel I am any worse than anybody else", "I am critical of myself for my weaknesses or mistakes", "I blame myself all the time for my faults", "I blame myself for everything bad that happens"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "I don't have any thoughts of killing myself.",
            answers = listOf("I don't have any thoughts of killing myself", "I have thoughts of killing myself, but I would not carry them out", "I would like to kill myself", "I would kill myself if I had the chance"),
            selectedOption = -1
        ),
        QuizItem(
            id = 10,
            desc = "I don't cry any more than usual.",
            answers = listOf("I don't cry any more than usual", "I cry more now than I used to", "I cry all the time now", "I used to be able to cry, but now I can't cry even though I want to"),
            selectedOption = -1
        ),
        QuizItem(
            id = 11,
            desc = "I am no more irritated by things than I ever was.",
            answers = listOf("I am no more irritated by things than I ever was", "I am slightly more irritated now than usual", "I am quite annoyed or irritated a good deal of the time", "I feel irritated all the time"),
            selectedOption = -1
        ),
        QuizItem(
            id = 12,
            desc = "I have not lost interest in other people.",
            answers = listOf("I have not lost interest in other people", "I am less interested in other people than I used to be", "I have lost most of my interest in other people", "I have lost all of my interest in other people"),
            selectedOption = -1
        ),
        QuizItem(
            id = 13,
            desc = "I make decisions about as well as I ever could.",
            answers = listOf("I make decisions about as well as I ever could", "I put off making decisions more than I used to", "I have greater difficulty in making decisions more than I used to", "I can't make decisions at all anymore"),
            selectedOption = -1
        ),
        QuizItem(
            id = 14,
            desc = "I don't feel that I look any worse than I used to.",
            answers = listOf("I don't feel that I look any worse than I used to", "I am worried that I am looking old or unattractive", "I feel there are permanent changes in my appearance that make me look unattractive", "I believe that I look ugly"),
            selectedOption = -1
        ),
        QuizItem(
            id = 15,
            desc = "I can work about as well as before.",
            answers = listOf("I can work about as well as before", "It takes an extra effort to get started at doing something", "I have to push myself very hard to do anything", "I can't do any work at all"),
            selectedOption = -1
        ),
        QuizItem(
            id = 16,
            desc = "I can sleep as well as usual.",
            answers = listOf("I can sleep as well as usual", "I don't sleep as well as I used to", "I wake up 1-2 hours earlier than usual and find it hard to get back to sleep", "I wake up several hours earlier than I used to and cannot get back to sleep"),
            selectedOption = -1
        ),
        QuizItem(
            id = 17,
            desc = "I don't get more tired than usual.",
            answers = listOf("I don't get more tired than usual", "I get tired more easily than I used to", "I get tired from doing almost anything", "I am too tired to do anything"),
            selectedOption = -1
        ),
        QuizItem(
            id = 18,
            desc = "My appetite is no worse than usual.",
            answers = listOf("My appetite is no worse than usual", "My appetite is not as good as it used to be", "My appetite is much worse now", "I have no appetite at all anymore"),
            selectedOption = -1
        ),
        QuizItem(
            id = 19,
            desc = "I haven't lost much weight, if any, lately.",
            answers = listOf("I haven't lost much weight, if any, lately", "I have lost more than five pounds", "I have lost more than ten pounds", "I have lost more than fifteen pounds"),
            selectedOption = -1
        ),
        QuizItem(
            id = 20,
            desc = "I am no more worried about my health than usual.",
            answers = listOf("I am no more worried about my health than usual", "I am worried about physical problems like aches, pains, upset stomach, or constipation", "I am very worried about physical problems and it's hard to think of much else", "I am so worried about my physical problems that I cannot think of anything else"),
            selectedOption = -1
        ),
        QuizItem(
            id = 21,
            desc = "I have not noticed any recent change in my interest in sex.",
            answers = listOf("I have not noticed any recent change in my interest in sex", "I am less interested in sex than I used to be", "I have almost no interest in sex", "I have lost interest in sex completely"),
            selectedOption = -1
        )
    )



    var finalBDIQuizList = listOf(
        QuizItem(
            id = 1,
            desc = "I do not feel sad.",
            answers = listOf("I do not feel sad", "I feel sad", "I am sad all the time and I can't snap out of it.", "I am so sad and unhappy that I can't stand it."),
            selectedOption = -1
        ),
        QuizItem(
            id = 2,
            desc = "I feel discouraged about the future.",
            answers = listOf("I am not particularly discouraged about the future", "I feel discouraged about the future", "I feel I have nothing to look forward to", "I feel the future is hopeless and that things cannot improve."),
            selectedOption = -1
        ),
        QuizItem(
            id = 3,
            desc = "I feel I have failed more than the average person.",
            answers = listOf("I do not feel like a failure", "I feel I have failed more than the average person", "As I look back on my life, all I can see is a lot of failures", "I feel I am a complete failure as a person"),
            selectedOption = -1
        ),
        QuizItem(
            id = 4,
            desc = "I get as much satisfaction out of things as I used to.",
            answers = listOf("I get as much satisfaction out of things as I used to", "I don't enjoy things the way I used to", "I don't get real satisfaction out of anything anymore", "I am dissatisfied or bored with everything"),
            selectedOption = -1
        ),
        QuizItem(
            id = 5,
            desc = "I don't feel particularly guilty.",
            answers = listOf("I don't feel particularly guilty", "I feel guilty a good part of the time", "I feel quite guilty most of the time", "I feel guilty all of the time"),
            selectedOption = -1
        ),
        QuizItem(
            id = 6,
            desc = "I don't feel I am being punished.",
            answers = listOf("I don't feel I am being punished", "I feel I may be punished", "I expect to be punished", "I feel I am being punished"),
            selectedOption = -1
        ),
        QuizItem(
            id = 7,
            desc = "I don't feel disappointed in myself.",
            answers = listOf("I don't feel disappointed in myself", "I am disappointed in myself", "I am disgusted with myself", "I hate myself"),
            selectedOption = -1
        ),
        QuizItem(
            id = 8,
            desc = "I don't feel I am any worse than anybody else.",
            answers = listOf("I don't feel I am any worse than anybody else", "I am critical of myself for my weaknesses or mistakes", "I blame myself all the time for my faults", "I blame myself for everything bad that happens"),
            selectedOption = -1
        ),
        QuizItem(
            id = 9,
            desc = "I don't have any thoughts of killing myself.",
            answers = listOf("I don't have any thoughts of killing myself", "I have thoughts of killing myself, but I would not carry them out", "I would like to kill myself", "I would kill myself if I had the chance"),
            selectedOption = -1
        ),
        QuizItem(
            id = 10,
            desc = "I don't cry any more than usual.",
            answers = listOf("I don't cry any more than usual", "I cry more now than I used to", "I cry all the time now", "I used to be able to cry, but now I can't cry even though I want to"),
            selectedOption = -1
        ),
        QuizItem(
            id = 11,
            desc = "I am no more irritated by things than I ever was.",
            answers = listOf("I am no more irritated by things than I ever was", "I am slightly more irritated now than usual", "I am quite annoyed or irritated a good deal of the time", "I feel irritated all the time"),
            selectedOption = -1
        ),
        QuizItem(
            id = 12,
            desc = "I have not lost interest in other people.",
            answers = listOf("I have not lost interest in other people", "I am less interested in other people than I used to be", "I have lost most of my interest in other people", "I have lost all of my interest in other people"),
            selectedOption = -1
        ),
        QuizItem(
            id = 13,
            desc = "I make decisions about as well as I ever could.",
            answers = listOf("I make decisions about as well as I ever could", "I put off making decisions more than I used to", "I have greater difficulty in making decisions more than I used to", "I can't make decisions at all anymore"),
            selectedOption = -1
        ),
        QuizItem(
            id = 14,
            desc = "I don't feel that I look any worse than I used to.",
            answers = listOf("I don't feel that I look any worse than I used to", "I am worried that I am looking old or unattractive", "I feel there are permanent changes in my appearance that make me look unattractive", "I believe that I look ugly"),
            selectedOption = -1
        ),
        QuizItem(
            id = 15,
            desc = "I can work about as well as before.",
            answers = listOf("I can work about as well as before", "It takes an extra effort to get started at doing something", "I have to push myself very hard to do anything", "I can't do any work at all"),
            selectedOption = -1
        ),
        QuizItem(
            id = 16,
            desc = "I can sleep as well as usual.",
            answers = listOf("I can sleep as well as usual", "I don't sleep as well as I used to", "I wake up 1-2 hours earlier than usual and find it hard to get back to sleep", "I wake up several hours earlier than I used to and cannot get back to sleep"),
            selectedOption = -1
        ),
        QuizItem(
            id = 17,
            desc = "I don't get more tired than usual.",
            answers = listOf("I don't get more tired than usual", "I get tired more easily than I used to", "I get tired from doing almost anything", "I am too tired to do anything"),
            selectedOption = -1
        ),
        QuizItem(
            id = 18,
            desc = "My appetite is no worse than usual.",
            answers = listOf("My appetite is no worse than usual", "My appetite is not as good as it used to be", "My appetite is much worse now", "I have no appetite at all anymore"),
            selectedOption = -1
        ),
        QuizItem(
            id = 19,
            desc = "I haven't lost much weight, if any, lately.",
            answers = listOf("I haven't lost much weight, if any, lately", "I have lost more than five pounds", "I have lost more than ten pounds", "I have lost more than fifteen pounds"),
            selectedOption = -1
        ),
        QuizItem(
            id = 20,
            desc = "I am no more worried about my health than usual.",
            answers = listOf("I am no more worried about my health than usual", "I am worried about physical problems like aches, pains, upset stomach, or constipation", "I am very worried about physical problems and it's hard to think of much else", "I am so worried about my physical problems that I cannot think of anything else"),
            selectedOption = -1
        ),
        QuizItem(
            id = 21,
            desc = "I have not noticed any recent change in my interest in sex.",
            answers = listOf("I have not noticed any recent change in my interest in sex", "I am less interested in sex than I used to be", "I have almost no interest in sex", "I have lost interest in sex completely"),
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
                    text = "Beck-Depression Inventory",
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
                val currentQuizItem = BDIQuizItemList[currentId]
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

                QuizItemOptions(selectedIndex = selectedIndex, n = 3, answers = BDIQuizItemList[currentId].answers){option->
                    finalBDIQuizList[currentId].selectedOption = option
                    println(finalBDIQuizList[currentId])
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
                            selectedIndex.value = finalBDIQuizList[currentId].selectedOption
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
                        if(currentId < finalBDIQuizList.size - 1){ // Ensure we don't go out of bounds

                            if(selectedIndex.value == -1){
                                Toast.makeText(
                                    context,
                                    "Please select an option before moving forward",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                currentId++
                                selectedIndex.value = finalBDIQuizList[currentId].selectedOption
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
                                    id = "BDI",
                                    list = finalBDIQuizList,
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
