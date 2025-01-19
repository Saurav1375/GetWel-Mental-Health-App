package com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.model.FeelingEntry
import com.example.getwell.screens.relaxScreen.mentaltherapy.feelingsjournal.repository.FeelingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeelingsViewModel : ViewModel() {
    private val repository = FeelingsRepository()
    
    private val _entries = MutableStateFlow<List<FeelingEntry>>(emptyList())
    val entries: StateFlow<List<FeelingEntry>> = _entries
    
    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted: StateFlow<Boolean> = _isSubmitted

    fun saveFeelingEntry(content: String, userId: String) {
        viewModelScope.launch {
            val entry = FeelingEntry(content = content, userId = userId)
            repository.saveFeelingEntry(entry)
                .onSuccess { 
                    _isSubmitted.value = true
                    loadEntries(userId)
                }
        }
    }

    fun loadEntries(userId: String) {
        viewModelScope.launch {
            repository.getFeelingEntries(userId)
                .onSuccess { entries ->
                    _entries.value = entries
                    println("Entries: $entries")
                }
                .onFailure { exception ->
                    println("Error loading entries: ${exception.message}")
                }
        }
    }

    fun resetSubmission() {
        _isSubmitted.value = false
    }
}