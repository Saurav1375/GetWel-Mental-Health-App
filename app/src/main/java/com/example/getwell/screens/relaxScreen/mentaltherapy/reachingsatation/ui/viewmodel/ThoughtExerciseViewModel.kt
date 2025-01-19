package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.model.ThoughtEntry
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.repository.ThoughtRepository
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.util.AIPromptGenerator
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ThoughtExerciseViewModel @Inject constructor(
    private val repository: ThoughtRepository,
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<ThoughtExerciseState>(ThoughtExerciseState.Initial)
    val uiState: StateFlow<ThoughtExerciseState> = _uiState

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser

    val userId = currentUser?.email

    private var currentEntry = ThoughtEntry(
        id = UUID.randomUUID().toString(),
        userId = userId!! // Set this when user authenticates
    )

    fun startExercise() {
        _uiState.value = ThoughtExerciseState.Ready
    }

    fun startNew() {
        currentEntry = ThoughtEntry(
            id = UUID.randomUUID().toString(),
            userId = currentEntry.userId
        )
        _uiState.value = ThoughtExerciseState.Ready
    }

    fun setInitialThought(thought: String) {
        _uiState.value = ThoughtExerciseState.ThoughtCaptured(thought)
    }

    fun addConsequence(consequence: String) {
        val currentState = _uiState.value
        if (currentState is ThoughtExerciseState.ThoughtCaptured) {
            _uiState.value = currentState.copy(
                consequences = currentState.consequences + consequence
            )
        }
    }

    fun analyzeConsequence(consequence: String, isRational: Boolean) {
        val currentState = _uiState.value
        if (currentState is ThoughtExerciseState.ThoughtCaptured) {
            _uiState.value = currentState.copy(
                rationalAnalysis = currentState.rationalAnalysis + (consequence to isRational)
            )
        }
    }

    fun generateAISuggestion() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is ThoughtExerciseState.ThoughtCaptured) {
                try {
                    val prompt = AIPromptGenerator.generateCopingStrategiesPrompt(
                        currentState.initialThought,
                        currentState.consequences
                    )
                    val response = generativeModel.generateContent(prompt).text
                    _uiState.value = currentState.copy(aiSuggestion = response ?: "")
                } catch (e: Exception) {
                    _uiState.value =
                        ThoughtExerciseState.Error(e.message ?: "Error generating AI suggestion")
                }
            }
        }
    }

    fun saveEntry(moodBefore: Int, moodAfter: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is ThoughtExerciseState.ThoughtCaptured) {
                val entry = currentEntry.copy(
                    initialThought = currentState.initialThought,
                    consequences = currentState.consequences,
                    rationalAnalysis = currentState.rationalAnalysis,
                    copingStrategies = currentState.copingStrategies,
                    aiSuggestion = currentState.aiSuggestion,
                    moodBefore = moodBefore,
                    moodAfter = moodAfter
                )

                repository.saveThoughtEntry(entry)
                    .onSuccess { _uiState.value = ThoughtExerciseState.Completed }
                    .onFailure { _uiState.value =
                        ThoughtExerciseState.Error(it.message ?: "Error saving entry")
                    }
            }
        }
    }
}

sealed class ThoughtExerciseState {
    object Initial : ThoughtExerciseState()
    object Ready : ThoughtExerciseState()
    data class ThoughtCaptured(
        val initialThought: String,
        val consequences: List<String> = emptyList(),
        val rationalAnalysis: Map<String, Boolean> = emptyMap(),
        val copingStrategies: List<String> = emptyList(),
        val aiSuggestion: String = ""
    ) : ThoughtExerciseState()
    object Completed : ThoughtExerciseState()
    data class Error(val message: String) : ThoughtExerciseState()
}