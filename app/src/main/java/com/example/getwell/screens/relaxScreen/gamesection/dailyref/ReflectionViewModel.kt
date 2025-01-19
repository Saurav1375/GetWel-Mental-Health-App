package com.example.getwell.screens.relaxScreen.gamesection.dailyref

import android.app.Application
import android.graphics.ColorSpace.Model
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.di.Injection
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ReflectionViewModel(
    private val repository: ReflectionRepository
) : ViewModel() {
    // State for current reflection
    val todayReflection = repository.getTodayReflection().asLiveData()

    private val _currentQuestion = MutableStateFlow<String?>(null)
    val currentQuestion = _currentQuestion.asStateFlow()

    // State for past reflections
    private val _pastReflections = MutableStateFlow<List<Reflection>>(emptyList())
    val pastReflections: StateFlow<List<Reflection>> = _pastReflections.asStateFlow()

    // State for user streak
    private val _userStreak = MutableStateFlow(UserStreak())
    val userStreak: StateFlow<UserStreak> = _userStreak.asStateFlow()

    // Initialize ViewModel
    init {
        loadInitialData()
        generateDailyQuestion()
    }


    private fun generateDailyQuestion() {
        _currentQuestion.value = ReflectionQuestions.getRandomQuestion()
    }

    // Load Initial Data
    private fun loadInitialData() {
        viewModelScope.launch {
            loadPastReflections()
            loadUserStreak()
        }
    }

    // Submit Reflection
    @RequiresApi(Build.VERSION_CODES.O)
    fun submitReflection(answer: String, question : String) {
        viewModelScope.launch {
            repository.submitReflection(answer, question)
            loadPastReflections()
            loadUserStreak()
        }
    }

    // Load Past Reflections
    private suspend fun loadPastReflections() {
        _pastReflections.value = repository.getPastReflections()
    }

    // Load User Streak
    private suspend fun loadUserStreak() {
        _userStreak.value = repository.getCurrentStreak()
    }

    // Perform Streak Check
    @RequiresApi(Build.VERSION_CODES.O)
    fun performStreakCheck() {
        viewModelScope.launch {
            repository.checkAndResetStreak()
            loadUserStreak()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndRestStreak(){
        viewModelScope.launch {
            repository.checkAndResetStreak()
        }
    }

    fun resetData() {
        _currentQuestion.value = null
        _pastReflections.value = emptyList()
        _userStreak.value = UserStreak()
        loadInitialData() // Reloads initial data as needed
    }



}