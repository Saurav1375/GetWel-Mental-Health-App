package com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.model.ThoughtEntry
import com.example.getwell.screens.relaxScreen.mentaltherapy.reachingsatation.data.repository.ThoughtRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PastExercisesViewModel @Inject constructor(
    private val repository: ThoughtRepository
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<ThoughtEntry>>(emptyList())
    val exercises: StateFlow<List<ThoughtEntry>> = _exercises

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser

    val userId = currentUser?.email

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            repository.getThoughtEntries(userId!!)  // Replace with actual user ID when auth is implemented
                .onSuccess { entries ->
                    _exercises.value = entries.sortedByDescending { it.timestamp }
                }
                .onFailure {
                    println("${it.message}")
                }
        }
    }
}