package com.example.getwell.screens.relaxScreen.stigmaQuiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.example.getwell.screens.customFont
import com.example.getwell.screens.stressQuiz.QUizTopProgress
import kotlin.math.min

data class QuizState(
    val currentIndex: Int = 0,
    val selectedAnswers: MutableList<Int> = mutableListOf(),
    val isSubmitEnabled: Boolean = false,
    val showScoreDialog: Boolean = false,
    val score: Int = 0
)
@Composable
fun QuizQuestionsScreen(
    navController: NavHostController,
    quizId : Int,
    userData: UserData?,
) {
    val quiz = Questions.quizzes[quizId]
    var quizState by remember { mutableStateOf(QuizState()) }
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    if (quizState.showScoreDialog) {
        ScoreDialog(
            score = quizState.score,
            onDismiss = {
                quizState = quizState.copy(showScoreDialog = false)
                navController.navigate(Screen.StigmaQuizMainScreen.route)
            }
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { QuizTopBar(navController = navController, quizId = quizId) }
    ) { paddingValues ->
        QuizContent(
            modifier = Modifier.padding(paddingValues),
            quizState = quizState,
            quiz = quiz,
            onAnswerSelected = { selectedAnswer ->
                val newAnswers = quizState.selectedAnswers.toMutableList()
                if (quizState.currentIndex < newAnswers.size) {
                    newAnswers[quizState.currentIndex] = selectedAnswer
                } else {
                    newAnswers.add(selectedAnswer)
                }
                quizState = quizState.copy(
                    selectedAnswers = newAnswers,
                    isSubmitEnabled = newAnswers.size == quiz.questions.size
                )
            },
            onNavigate = { direction ->
                when (direction) {
                    NavigationDirection.PREVIOUS -> {
                        if (quizState.currentIndex > 0) {
                            quizState = quizState.copy(currentIndex = quizState.currentIndex - 1)
                        }
                    }
                    NavigationDirection.NEXT -> {
                        if (quizState.currentIndex < quiz.questions.size - 1) {
                            if (quizState.currentIndex < quizState.selectedAnswers.size) {
                                quizState = quizState.copy(currentIndex = quizState.currentIndex + 1)
                            }
                        } else if (quizState.isSubmitEnabled) {
                            val score = calculateScore(quizState.selectedAnswers, quiz)
                            quizState = quizState.copy(
                                showScoreDialog = true,
                                score = score
                            )
                        }
                    }
                }
            },
            scrollState = scrollState
        )
    }
}

@Composable
private fun QuizTopBar(
    navController: NavHostController,
    quizId: Int
) {
    TopAppBar(
        backgroundColor = Color(31, 31, 37),
        elevation = 5.dp,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 40.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Quiz",
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White
            )
        )
    }
}

@Composable
private fun QuizContent(
    modifier: Modifier = Modifier,
    quizState: QuizState,
    quiz: Quiz,
    onAnswerSelected: (Int) -> Unit,
    onNavigate: (NavigationDirection) -> Unit,
    scrollState: ScrollState
) {
    Box(
        modifier = modifier
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
            QUizTopProgress(
                quizState.currentIndex,
                quiz.questions.size
            )

            QuestionCard(question = quiz.questions[quizState.currentIndex], quizState)

            AnswerOptions(
                options = quiz.questions[quizState.currentIndex].options,
                selectedAnswer = if (quizState.currentIndex < quizState.selectedAnswers.size)
                    quizState.selectedAnswers[quizState.currentIndex] else -1,
                onOptionSelected = onAnswerSelected
            )

            NavigationButtons(
                currentIndex = quizState.currentIndex,
                totalQuestions = quiz.questions.size,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
private fun QuestionCard(question: Question, quizState: QuizState) {
    Box(
        modifier = Modifier
            .size(400.dp, 210.dp)
            .background(Color(31, 31, 37), RoundedCornerShape(15.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Q${quizState.currentIndex + 1}.",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = question.questionText,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
private fun AnswerOptions(
    options: List<String>,
    selectedAnswer: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, option ->
            AnswerOption(
                text = option,
                isSelected = index == selectedAnswer,
                onClick = { onOptionSelected(index) }
            )
        }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .padding(vertical = 8.dp)
            .border(
                BorderStroke(2.dp, Color(143, 189, 200)),
                RoundedCornerShape(16.dp)
            )
            .background(
                if (isSelected) Color(143, 189, 200)
                else Color.Transparent,
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            style = TextStyle(
                color = if (isSelected) Color.Black
                else Color(143, 189, 200),
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun NavigationButtons(
    currentIndex: Int,
    totalQuestions: Int,
    onNavigate: (NavigationDirection) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onNavigate(NavigationDirection.PREVIOUS) },
            enabled = currentIndex > 0
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = Icons.Default.KeyboardDoubleArrowLeft,
                contentDescription = "Previous",
                tint = Color.White
            )
        }

        Text(
            text = "${currentIndex + 1} of $totalQuestions",
            style = TextStyle(
                color = Color.White,
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
        )

        IconButton(
            onClick = { onNavigate(NavigationDirection.NEXT) }
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = Icons.Default.KeyboardDoubleArrowRight,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}

enum class NavigationDirection {
    PREVIOUS,
    NEXT
}


private fun calculateScore(selectedAnswers: List<Int>, quiz: Quiz): Int {
    var correctAnswers = 0
    selectedAnswers.forEachIndexed { index, answer ->
        if (answer == quiz.questions[index].correctAnswerIndex) {
            correctAnswers++
        }
    }
    return (correctAnswers.toFloat() / quiz.questions.size * 100).toInt()
}


@Composable
fun ScoreDialog(
    score: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(31, 31, 37),
        shape = RoundedCornerShape(16.dp),
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Quiz Completed!",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularScoreIndicator(
                        score = score,
                        modifier = Modifier.fillMaxSize()
                    )

                    Text(
                        text = "$score%",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    )
                }

                Text(
                    text = getScoreMessage(score),
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(143, 189, 200)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Next",
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    )
}

@Composable
fun CircularScoreIndicator(
    score: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.1f
        val center = Offset(size.width / 2, size.height / 2)
        val radius = (min(size.width, size.height) - strokeWidth) / 2

        // Background circle
        drawCircle(
            color = Color.Gray.copy(alpha = 0.2f),
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Score arc
        drawArc(
            color = getScoreColor(score),
            startAngle = -90f,
            sweepAngle = 360f * score / 100,
            useCenter = false,
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

private fun getScoreColor(score: Int): Color {
    return when {
        score >= 80 -> Color(76, 175, 80)  // Green
        score >= 60 -> Color(255, 152, 0)  // Orange
        else -> Color(244, 67, 54)         // Red
    }
}

private fun getScoreMessage(score: Int): String {
    return when {
        score >= 80 -> "Excellent! You have a great understanding!"
        score >= 60 -> "Good job! Keep learning and improving!"
        else -> "Keep practicing! You'll get better!"
    }
}