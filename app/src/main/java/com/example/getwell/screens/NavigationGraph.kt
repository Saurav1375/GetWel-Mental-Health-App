package com.example.getwell.screens

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.getwell.R
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.authSystem.AuthViewModel
import com.example.getwell.authSystem.AuthWithUserPass
import com.example.getwell.data.Screen
import com.example.getwell.di.Injection
import com.example.getwell.domain.provider.StressQuizProvider
import com.example.getwell.domain.provider.StressViewModel
import com.example.getwell.screens.chatbot.ui.chat.ChatScreen
import com.example.getwell.screens.profilepage.MeditationStatsScreen
import com.example.getwell.screens.profilepage.ProfileScreen
import com.example.getwell.screens.relaxScreen.RelaxScreen
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.ui.screens.StressAppraisalScreen
import com.example.getwell.screens.relaxScreen.gamesection.BubblePopGame
import com.example.getwell.screens.relaxScreen.gamesection.EtheralFlowScreen
import com.example.getwell.screens.relaxScreen.gamesection.GameSimpleMainScreen
import com.example.getwell.screens.relaxScreen.gamesection.dailyref.ReflectionScreen
import com.example.getwell.screens.relaxScreen.listen.ListenItem
import com.example.getwell.screens.relaxScreen.listen.ListenItemScreen
import com.example.getwell.screens.relaxScreen.listen.ListenScreen
import com.example.getwell.screens.relaxScreen.meditation.MeditationMainSCreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.MentalTherapyMainScreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.ui.screens.HistoryScreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.ui.screens.JournalScreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.viewmodel.FeelingsViewModel
import com.example.getwell.screens.relaxScreen.mentaltherapy.imagery.ui.GuidedImageryScreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.screens.PastExercisesScreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.screens.ThoughtExerciseScreen
import com.example.getwell.screens.relaxScreen.mentaltherapy.thoughtcontrol.IrrationalThoughtScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.BreathingMainScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.deepbreathing.DeepBreathingScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.ExhalationBreathingScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.pranayama.PranayamaScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.RestfulSleepScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.data.YogaData
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.ui.screens.YogaDetailScreen
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.ui.screens.YogaListScreen
import com.example.getwell.screens.relaxScreen.stigmaQuiz.QuizQuestionsScreen
import com.example.getwell.screens.relaxScreen.stigmaQuiz.StigmaQuizMainScreen
import com.example.getwell.screens.resourcesection.education.EducationScreen
import com.example.getwell.screens.resourcesection.practical.PracticalGuidesScreen
import com.example.getwell.screens.resourcesection.support.SupportResourcesScreen
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.WellnessCentersViewModel
import com.example.getwell.screens.resourcesection.wellnesscentersfinder.ui.screens.WellnessCentersScreen
import com.example.getwell.screens.settingspage.SettingsItemView
import com.example.getwell.screens.stressQuiz.AcadStressMainScreen
import com.example.getwell.screens.stressQuiz.BAIQuizMainScreen
import com.example.getwell.screens.stressQuiz.BDIQuizMainScreen
import com.example.getwell.screens.stressQuiz.CheckEnabled
import com.example.getwell.screens.stressQuiz.DSSQuizMainScreen
import com.example.getwell.screens.stressQuiz.DaysStigmaQuizMainScreen
import com.example.getwell.screens.stressQuiz.ISMIQuizMainScreen
import com.example.getwell.screens.stressQuiz.PSSQuizMainScreen
import com.example.getwell.screens.stressQuiz.QuizBoardingScreen
import com.example.getwell.screens.stressanalysis.StressAnalysisApp
import com.example.getwell.screens.stressmanager.data.RecommendationRepository
import com.example.getwell.screens.stressmanager.ui.screens.TodaysRecommendationScreen
import com.example.getwell.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    authUiClient: AuthUiClient,
    authWithUserPass: AuthWithUserPass,
    stressViewModel: StressViewModel,
    applicationContext: Context,
    locationViewModel : WellnessCentersViewModel
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val stressQuizProvider = StressQuizProvider(Injection.instance())
//    val reflectionViewModel = ReflectionViewModel(ReflectionRepository(Injection.instance(), authUiClient))
    val isDASSQuizenabled = remember { mutableStateOf(true) }
    val isPSSQuizenabled = remember { mutableStateOf(true) }
    val isBDIQuizenabled = remember { mutableStateOf(true) }
    val isBAIQuizenabled = remember { mutableStateOf(true) }
    val isASSQuizenabled = remember { mutableStateOf(true) }

    val isISMIQuizenabled = remember { mutableStateOf(true) }
    val isDaysQuizenabled = remember { mutableStateOf(true) }




    val journalViewModel: FeelingsViewModel = viewModel()



    LaunchedEffect(key1 = Unit) {
        if (authUiClient.getSignedInUser() != null) {
            navController.navigate(Screen.HomeScreen.route)
            CheckEnabled(
                flag = isDASSQuizenabled,
                parameter = 7L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "DASS21"
            )
            CheckEnabled(
                flag = isPSSQuizenabled,
                parameter = 30L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "PSS"

            )
            CheckEnabled(
                flag = isBDIQuizenabled,
                parameter = 7L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "BDI"

            )

            CheckEnabled(
                flag = isBAIQuizenabled,
                parameter = 7L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "BAI"

            )

            CheckEnabled(
                flag = isASSQuizenabled,
                parameter = 7L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "Acad-Stress"

            )
            CheckEnabled(
                flag = isISMIQuizenabled,
                parameter = 1L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "ISMI"

            )
            CheckEnabled(
                flag = isDaysQuizenabled,
                parameter = 1L * 24 * 60 * 60 * 1000,
                userData = authUiClient.getSignedInUser(),
                stressQuizProvider,
                id = "Days"

            )
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.OnBoarding1.route,
    ) {
        composable(Screen.OnBoarding1.route) {
            OnBoardingScreen1 {
                navController.navigate(Screen.OnBoarding2.route)
            }

        }

        composable(Screen.OnBoarding2.route) {
            OnBoardingScreen2(
                navController = navController,
                onNext = { navController.navigate(Screen.SignInScreen.route) },
                navigateToBoarding1 = { navController.navigate(Screen.OnBoarding1.route) }
            )


        }


        composable(Screen.SignInScreen.route) {
            val context = LocalContext.current as Activity
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = authUiClient.SignInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)

                        }

                    }

                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign In Successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(Screen.HomeScreen.route)
                    viewModel.resetState()
                }

            }

            SignInScreen(
                navController,
                state = state,
                forgetPass = { navController.navigate(Screen.ForgetPassScreen.route) },
                onRegister = {
                    viewModel.resetState()
                    navController.navigate((Screen.SignUpScreen.route))
                             },

                loginWithGoogle = {
                    scope.launch {
                        val signInIntentSender = authUiClient.SignIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )

                    }


                },
                loginWithFacebook = {

                },
            ) { email, pass ->
                scope.launch {
                    val result = authWithUserPass.signIn(email, pass)
                    viewModel.onSignInResult(result)
                }

            }
        }

        composable(Screen.SignUpScreen.route) {
            LaunchedEffect(key1 = state.isSignUpSuccessful) {
                if (state.isSignUpSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign Up Successful",
                        Toast.LENGTH_LONG
                    ).show()

                    scope.launch {
                        authUiClient.signOut()
                        homeViewModel.setDisplayState(true)
                    }
                    navController.navigate(Screen.SignInScreen.route)
                    viewModel.resetState()


                }
            }
            SignUpScreen(
                state = state,
                onLogin = {
                    viewModel.resetState()
                    navController.navigate(Screen.SignInScreen.route) }
            ) { email, name, pass ->
                scope.launch {
                    val result = authWithUserPass.signUp(email, pass, name)
                    viewModel.onSignUpResult(result)
                }
            }

        }

        composable(Screen.ForgetPassScreen.route) {
            ForgetPassScreen {email->
                if (email.isNotEmpty()) {
                    authWithUserPass.sendPasswordResetEmail(email) { success ->
                        if (success) {
                            Toast.makeText(
                                context,
                                "Link to reset password has been sent to you email",
                                Toast.LENGTH_SHORT

                            ).show()
//                            navController.navigate(Screen.ResetPassScreen.route)
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to send reset email.",
                                Toast.LENGTH_SHORT

                            ).show()

                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please enter your email.",
                        Toast.LENGTH_SHORT

                    ).show()
                }

            }

        }
        composable(Screen.ResetPassScreen.route) {
            ResetPassScreen {
                navController.navigate(Screen.SignInScreen.route)
            }

        }

        composable(Screen.HomeScreen.route) {
            LaunchedEffect(key1 = true) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"
                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )

                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }
            HomeScreen(
                navController,
                userData = authUiClient.getSignedInUser(),
                isDASSQuizenabled = isDASSQuizenabled,
                isPSSQuizenabled = isPSSQuizenabled,
                homeViewModel.getDisplayState(),
                viewModel = stressViewModel,
//                refViewModel = reflectionViewModel,
                onPressed = {
                    homeViewModel.setDisplayState(false)
                }) {
                scope.launch {
                    authUiClient.signOut()
                    Toast.makeText(
                        applicationContext,
                        "Signed Out Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    homeViewModel.setDisplayState(true)
                    navController.navigate(Screen.SignInScreen.route)
                    viewModel.resetState()
//                    refViewModel.resetData()

                }

            }
        }

        composable(Screen.ChatroomScreen.route) {

//            ChatroomScreen(navController, userData = authUiClient.getSignedInUser())

        }
        composable(Screen.RelaxScreen.route) {
            RelaxScreen(navController = navController)

        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController, userData = authUiClient.getSignedInUser(),stressViewModel){}
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(
                navController = navController,
                userData = authUiClient.getSignedInUser(),
                onSignOut = {
                    scope.launch {
                        authUiClient.signOut()
                        Toast.makeText(
                            applicationContext,
                            "Signed Out Successfully",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(Screen.SignInScreen.route)
                        homeViewModel.setDisplayState(true)
                        viewModel.resetState()


                    }
                }
            ) {
                navController.navigate(Screen.SettingsItemScreen.route + "/$it")
            }


        }

        composable(Screen.EducationScreen.route) {

            EducationScreen(navController = navController)
        }

        composable(Screen.PracticalGuidesScreen.route) {
            PracticalGuidesScreen(navController = navController)

        }

        composable(Screen.WellnessCenters.route) {
            WellnessCentersScreen(navController = navController, locationViewModel)

        }

        composable(Screen.SupportResourcesScreen.route) {
            SupportResourcesScreen(navController = navController)

        }
        composable(Screen.QuizBoardingScreen.route) {
            LaunchedEffect(key1 = true) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"
                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )
            }

            QuizBoardingScreen(
                navController = navController,
                isDASSQuizenabled = isDASSQuizenabled,
                isPSSQuizenabled = isPSSQuizenabled,
                isBAIQuizenabled = isBAIQuizenabled,
                isBDIQuizenabled = isBDIQuizenabled,
                isASSQuizenabled = isASSQuizenabled,
                isISMIQuizenabled = isISMIQuizenabled,
                isDaysQuizenabled = isDaysQuizenabled
            )

        }

        composable(Screen.DSSQuizScreen.route) {
            DSSQuizMainScreen(
                navController = navController,
                userData = authUiClient.getSignedInUser()
            ) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"

                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )


            }

        }
        composable(Screen.PSSQuizScreen.route) {
            PSSQuizMainScreen(
                navController = navController,
                userData = authUiClient.getSignedInUser()
            ) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"

                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }

        }

        composable(Screen.BDIQuizScreen.route) {
            BDIQuizMainScreen(navController = navController, userData = authUiClient.getSignedInUser()) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"

                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }
        }
        composable(Screen.BAIQuizScreen.route) {
            BAIQuizMainScreen(navController = navController, userData = authUiClient.getSignedInUser()) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"

                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }
        }
        composable(Screen.AcadQuizScreen.route) {
            AcadStressMainScreen(navController = navController, userData = authUiClient.getSignedInUser()) {
                CheckEnabled(
                    flag = isDASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "DASS21"
                )
                CheckEnabled(
                    flag = isPSSQuizenabled,
                    parameter = 30L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "PSS"

                )
                CheckEnabled(
                    flag = isBDIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BDI"

                )

                CheckEnabled(
                    flag = isBAIQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "BAI"

                )

                CheckEnabled(
                    flag = isASSQuizenabled,
                    parameter = 7L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Acad-Stress"

                )
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }
        }

        composable(Screen.ISMIQuizScreen.route) {
            ISMIQuizMainScreen(navController = navController, userData = authUiClient.getSignedInUser() ) {
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }
        }


        composable(Screen.DaysStigmaQuizScreen.route) {
            DaysStigmaQuizMainScreen(navController = navController, userData = authUiClient.getSignedInUser()) {
                CheckEnabled(
                    flag = isISMIQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "ISMI"

                )
                CheckEnabled(
                    flag = isDaysQuizenabled,
                    parameter = 1L * 24 * 60 * 60 * 1000,
                    userData = authUiClient.getSignedInUser(),
                    stressQuizProvider,
                    id = "Days"

                )

            }
        }

        composable(Screen.ListenScreen.route) {
            ListenScreen(navController = navController) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "music",
                    it
                )
                navController.navigate(Screen.ListemItemScreen.route)

            }


        }

        composable(Screen.ListemItemScreen.route) {
            val musicItem =
                navController.previousBackStackEntry?.savedStateHandle?.get<ListenItem>("music")
                    ?: ListenItem("", R.drawable.chatbg)
            ListenItemScreen(
                navController = navController,
                item = musicItem
            )
        }


        composable(Screen.MeditationMainScreen.route) {
            MeditationMainSCreen(
                navController = navController,
                userData = authUiClient.getSignedInUser()
            )
        }

        composable(Screen.GamesMainScreen.route) {
            GameSimpleMainScreen(
                navController = navController,
                onGameSelected = {
                    when(it){
                        1->navController.navigate(Screen.GradientGameScreen.route)
                        2->navController.navigate(Screen.BubbleGameScreen.route)
                        3->navController.navigate(Screen.DailyRefScreen.route)
                    }
                }
            )

        }

        composable(Screen.StigmaQuizMainScreen.route) {
            StigmaQuizMainScreen(navController = navController)
        }

        composable(Screen.DeepBreathingScreen.route) {
            DeepBreathingScreen(navController = navController)
//            QuizApp()
        }

        composable(Screen.BreathingMainScreen.route) {
            BreathingMainScreen(navController = navController)
        }

        composable(Screen.RestfulSleep.route) {
            RestfulSleepScreen(navController)

        }
        composable(Screen.ExhalationBreathing.route) {
            ExhalationBreathingScreen(navController = navController)
        }

        composable(Screen.Pranayama.route) {
            PranayamaScreen(navController)
        }


        composable(Screen.YogaListScreen.route) {
            YogaListScreen(navController) { pose ->
                navController.navigate(Screen.YogaDetailsScreen.route + "/${pose.id}")
            }
        }

        composable(Screen.YogaDetailsScreen.route + "/{poseId}") { backStackEntry ->
            val poseId = backStackEntry.arguments?.getString("poseId")?.toIntOrNull() ?: return@composable
            val pose = YogaData.poses.find { it.id == poseId } ?: return@composable
            YogaDetailScreen(
                navController = navController,
                pose = pose,
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.GuidedImageryScreen.route) {
            GuidedImageryScreen(navController = navController)
        }

        composable(Screen.IrrationalThoughtScreen.route) {
            IrrationalThoughtScreen(navController = navController)

        }

        composable(Screen.ReachingSatationScreen.route) {
            ThoughtExerciseScreen(navController = navController)

        }
        composable(Screen.PastSatationScreen.route) {
            PastExercisesScreen(navController = navController)

        }

        composable(Screen.MentalTherapyMainScreen.route) {
           MentalTherapyMainScreen(navController = navController)
        }





        composable(Screen.VentilateFeelingsScreen.route) {
            JournalScreen(
                viewModel = journalViewModel,
                userData = authUiClient.getSignedInUser(),
                navController = navController,
                onNavigateToHistory = { navController.navigate(Screen.HistoryFeelingsScreen.route) }
            )
        }
        composable(Screen.HistoryFeelingsScreen.route) {
            HistoryScreen(viewModel = journalViewModel, userData = authUiClient.getSignedInUser(), navController)
        }


        composable(Screen.GradientGameScreen.route) {
            EtheralFlowScreen(navController = navController)

        }


        composable(Screen.SettingsItemScreen.route + "/{it}") {
            val itemId =
                navController.currentBackStackEntry?.arguments?.getString("it")?.toInt() ?: 0
            SettingsItemView(
                navController = navController,
                itemId = itemId,
                userData = authUiClient.getSignedInUser(),
                authUiClient = authUiClient,
                authViewModel = viewModel,
                homeViewModel = homeViewModel
            )
        }


        composable(Screen.BubbleGameScreen.route) {
            BubblePopGame(navController = navController, onGameSelected = {})
        }

        composable(Screen.DailyRefScreen.route) {
            ReflectionScreen(navController, authUiClient.getSignedInUser())

        }


        composable(Screen.MeditationStatsScreen.route) {
            MeditationStatsScreen(navController = navController, userData = authUiClient.getSignedInUser())
        }

        composable(route = "${Screen.QuizQuestionsScreen.route}/{quiz}",) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getInt("quizId") ?: 0
            QuizQuestionsScreen(
                navController = navController,
                quizId = quizId,
                userData = authUiClient.getSignedInUser()
            )
        }


        composable(Screen.AIStressScreen.route) {
            StressAnalysisApp(navController)
        }

        composable(Screen.AppraisalScreen.route) {
            StressAppraisalScreen(navController = navController)
        }


        composable(Screen.TodaysPlanScreen.route) {
            TodaysRecommendationScreen(
                navController = navController,
                viewModel = stressViewModel,
                repository = RecommendationRepository("AIzaSyDBcqnegXZr8g0h5ED7kKZ4Nc_lraS_jIQ")
            )
        }


        composable(Screen.ChatBot.route) {
            ChatScreen(navController = navController)

        }
    }
}







