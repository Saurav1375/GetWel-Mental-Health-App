package com.example.getwell.screens.stressQuiz

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getwell.R
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.domain.provider.StressQuizProvider
import com.example.getwell.screens.customFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuizBoardingScreen(
    navController: NavHostController,
    isDASSQuizenabled: MutableState<Boolean>,
    isPSSQuizenabled: MutableState<Boolean>,
    isBDIQuizenabled: MutableState<Boolean>,
    isBAIQuizenabled: MutableState<Boolean>,
    isASSQuizenabled: MutableState<Boolean>,
    isISMIQuizenabled: MutableState<Boolean>,
    isDaysQuizenabled: MutableState<Boolean>,

) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()


    val enabledQuiz = remember{ mutableStateOf(true) }


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
                    text = "Stress Quizzes",
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
                    .verticalScroll(scrollState)
            ) {
                    QuizModelCard(
                        duration = "Week",
                        title = "DASS-21 SCALE",
                        instructions = "Please read each statement and select a number 0, 1, 2 or 3 which indicates how much the statement applied to you over.\n\nThe rating scale is as follows:\n" +
                                "0 - Did not apply to me at all\n" +
                                "1 - Applied to me to some degree, or some of the time\n" +
                                "2 - Applied to me to a considerable degree or a good part of time\n" +
                                "3 - Applied to me very much or most of the time\n\nNOTE: The DASS-21 is not a replacement for a clinical interview; consult a GP for professional help if needed.",
                        description = "The DASS-21 measures depression, anxiety, and stress through 7-item scales each, assessing emotional states like hopelessness, anxiety, and chronic arousal. Scores are summed for each state, reflecting distress on a dimensional scale. It doesn't categorize disorders but measures degrees of distress.",
                        bannerImage = R.drawable.quizbanner1,
                        questionsSIze = "21",
                        certification = "Stress Scale",
                        onStartQuizClick = {
                            navController.navigate(Screen.DSSQuizScreen.route)
                        },
                        isEnabled = isDASSQuizenabled,
                    )

                Spacer(modifier = Modifier.height(20.dp))
                    QuizModelCard(
                        duration = "Month",
                        title = "PSS SCALE",
                        description = "The Perceived Stress Scale (PSS), developed in 1983, measures individual stress levels based on perceptions of recent experiences. It prompts quick responses about feelings over the past month, highlighting that similar experiences can lead to vastly different stress scores depending on personal perception.",
                        bannerImage = R.drawable.relaxing_games,
                        questionsSIze = "10",
                        certification = "Stress Scale",
                        onStartQuizClick = {
                            navController.navigate(Screen.PSSQuizScreen.route)
                        },
                        instructions = "Respond quickly without tallying how often you felt a certain way; instead, select the option that best represents your general estimate.\n\nFor each question choose from the following alternatives:\n" +
                                " 0 - never\n 1 - almost never\n 2 - sometimes\n 3 - fairly often\n 4 - very often\n\nDisclaimer: These scores are not diagnostic and only for stress evaluation, contact EAP for confidential support.",
                        isEnabled = isPSSQuizenabled,
                    )



                Spacer(modifier = Modifier.height(20.dp))
                QuizModelCard(
                    duration = "Week",
                    title = "Beck's Depression Inventory (BDI)",
                    description = "The Beck Depression Inventory (BDI) is a 21-item, self-report rating inventory that measures characteristic attitudes and symptoms of depression.  Each item is scored 0 to 3 points for a total score range of 0 to 63.",
                    bannerImage = R.drawable.bdi,
                    questionsSIze = "21",
                    certification = "Depression Scale",
                    onStartQuizClick = {
                        navController.navigate(Screen.BDIQuizScreen.route)
                    },
                    instructions = "For each question choose from the following alternatives:\n" +
                            "An example of a question and rating scale can be found below:\n" +
                            "\n" +
                            "0. I do not feel sad\n" +
                            "1. I feel sad\n" +
                            "2. I am sad all the time, and I can’t snap out of it\n" +
                            "3. I am so sad and unhappy that I can’t stand it\n\nDisclaimer: These scores are not diagnostic and only for depression evaluation, contact EAP for confidential support.",
                    isEnabled = isBDIQuizenabled,
                )


                Spacer(modifier = Modifier.height(20.dp))
                QuizModelCard(
                    duration = "Week",
                    title = "Beck Anxiety Inventory (BAI)",
                    description = "The Beck Anxiety Inventory (BAI) is a self-report assessment tool for rating anxiety levels. It was developed by Aaron T. Beck in 1988 and is composed of a 21-item questionnaire that may help determine how severe your anxiety is.",
                    bannerImage = R.drawable.bai,
                    questionsSIze = "21",
                    certification = "Anxiety Scale",
                    onStartQuizClick = {
                        navController.navigate(Screen.BAIQuizScreen.route)
                    },
                    instructions = "Please carefully read each item in the list. Indicate how much you have been bothered by that symptom during the past month, including today\n\nFor each question choose from the following alternatives:\n" +
                            " 0 -  Not at all\n 1 -  Mildly, but it didn’t bother me much\n 2 -  Moderately – it wasn’t pleasant at times\n 3 - Severely – it bothered me a lot\n\nDisclaimer: These scores are not diagnostic and only for stress evaluation, contact EAP for confidential support.",
                    isEnabled = isBAIQuizenabled,
                )

                Spacer(modifier = Modifier.height(20.dp))
                QuizModelCard(
                    duration = "Week",
                    title = "Academic Stress Scale",
                    description = "The Academic Stress Scale (ASS) helps you assess the level of stress you are experiencing in your academic life. By answering a series of questions, you can identify stressors, track your stress levels, and gain insight into ways to manage it. ",
                    bannerImage = R.drawable.academicprressure,
                    questionsSIze = "15",
                    certification = "Academic Scale",
                    onStartQuizClick = {
                        navController.navigate(Screen.AcadQuizScreen.route)
                    },
                    instructions = "Each question will be scored on a scale from 0 to 4\n\nFor each question choose from the following alternatives:\n" +
                            " 0 - Never\n 1 - Rarely\n 2 - Sometimes\n 3 - Often\n 4 - Always",
                    isEnabled = isASSQuizenabled,
                )

                Spacer(modifier = Modifier.height(20.dp))
                QuizModelCard(
                    duration = "Day",
                    title = "Internalized Stigma of Mental Illness Inventory ",
                    description = "The ISMI-9 is a self-report instrument designed to measure the overall strength of respondents’ internalized stigma of mental illness (i.e., self-stigma of mental illness) among persons with psychiatric disorders. A higher score indicates more severe internalized stigma of mental illness.",
                    bannerImage = R.drawable.bdi,
                    questionsSIze = "9",
                    certification = "Stigma Scale",
                    onStartQuizClick = {
                        navController.navigate(Screen.ISMIQuizScreen.route)
                    },
                    instructions = "We are going to use the term “mental illness” in the rest of this questionnaire, but please think of it as whatever you feel is the best term for it.  \n\nFor each question, please mark whether you \n    strongly disagree (1), \n   disagree (2), \n    agree (3), or \n    strongly agree (4)" ,
                    isEnabled = isISMIQuizenabled
                )


                Spacer(modifier = Modifier.height(20.dp))
                QuizModelCard(
                    duration = "Day",
                    title = "Day’s Mental Illness Stigma Scale",
                    description = "This self-report measure of stigmatizing attitudes toward mental illness consists of 28 items and contains seven subscales: interpersonal anxiety, relationship disruption, hygiene, visibility, treatability, professional efficacy and recovery.",
                    bannerImage = R.drawable.bai,
                    questionsSIze = "28",
                    certification = "Stigma Scale",
                    onStartQuizClick = {
                        navController.navigate(Screen.DaysStigmaQuizScreen.route)
                    },
                    instructions = " Factors are noted at the end of each item. Brackets indicate where illness names can be interchanged to present various mental illness conditions.\n" +
                            " Please indicate the extent to which you agree or disagree with the statements using the following scale: \n" +
                            "   1. Completely Disagree\n" +
                            "   2. Mostly Disagree\n" +
                            "   3. Somewhat Disagree\n" +
                            "   4. Neutral\n" +
                            "   5. Somewhat Agree\n" +
                            "   6. Mostly Agree\n" +
                            "   7. Completely Agree",
                    isEnabled = isDaysQuizenabled,
                )
            }
        }
    }
}


@Composable
fun QuizModelCard(
    duration: String,
    title: String,
    instructions : String,
    description: String,
    bannerImage: Int,
    questionsSIze: String,
    certification: String,
    onStartQuizClick: () -> Unit,
    isEnabled: MutableState<Boolean>,
    ) {
    var showInst by remember { mutableStateOf(false) }
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

            if(!showInst){
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Quiz of the $duration",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

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
                                text = "${questionsSIze}\nQuestions",
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


                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Note: ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color(255, 87, 51 )

                                )

                            ){
                                append(" You can take this quiz once a $duration only")
                            }
                        },
                        style = TextStyle(
                            color = Color.Cyan,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                        )
                    )



                Spacer(modifier = Modifier.height(16.dp))

            }

            else{
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



            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(if(showInst) Color.Transparent else Color(63, 69, 83), shape = RoundedCornerShape(15.dp))
                        .border(BorderStroke(if(showInst) 2.dp else 0.dp, Color(63, 69, 83)), RoundedCornerShape(15.dp))
                        .height(45.dp)
                        .fillMaxWidth(0.5f)
                        .clickable { showInst = !showInst },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(!showInst) "View Instructions" else "View Summary",
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
                        text = if (isEnabled.value) "Start Test" else "Completed",
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


fun CheckEnabled(
    flag: MutableState<Boolean>,
    parameter: Long,
    userData: UserData?,
    stressQuizProvider: StressQuizProvider,
    id: String
) {

    val currentTime = System.currentTimeMillis()

    if (userData != null) {
        stressQuizProvider.getLatestResponseTime(
            userEmail = userData.emailId,
            id = id
        ) {
            flag.value = (currentTime - it) > parameter
        }
    }

}

