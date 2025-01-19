package com.example.getwell.domain.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.data.AnxietyValues
import com.example.getwell.data.DepressionValues
import com.example.getwell.data.Mood
import com.example.getwell.data.StigmaValues
import com.example.getwell.data.StressValues
import com.example.getwell.screens.stressQuiz.finalAnxiety
import com.example.getwell.screens.stressQuiz.finalDepression
import com.example.getwell.screens.stressQuiz.finalStigma
import com.example.getwell.screens.stressQuiz.finalStress
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StressViewModel(
    private val repository: StressRepository
) : ViewModel() {
    private val _stressState = MutableStateFlow<StressUIState>(StressUIState.Loading)
    val stressState: StateFlow<StressUIState> = _stressState.asStateFlow()


    private val _depressionState = MutableStateFlow<DepressionUIState>(DepressionUIState.Loading)
    val depressionState: StateFlow<DepressionUIState> = _depressionState.asStateFlow()

    private val _anxietyState = MutableStateFlow<AnxietyUIState>(AnxietyUIState.Loading)
    val anxietyState: StateFlow<AnxietyUIState> = _anxietyState.asStateFlow()

    private val _stigmaState = MutableStateFlow<StigmaUIState>(StigmaUIState.Loading)
    val stigmaState: StateFlow<StigmaUIState> = _stigmaState.asStateFlow()

    fun calculateStressForToday(userEmail: String) {
        viewModelScope.launch {
            try {
                _stressState.value = StressUIState.Loading

                // Fetch all required data
                val moodList = repository.getTodayMoods(userEmail)
                val pssQuizItems = repository.getLatestQuizData(userEmail, "PSS") ?: emptyList()
                val dassQuizItems = repository.getLatestQuizData(userEmail, "DASS21") ?: emptyList()
                val acadQuizItems = repository.getLatestQuizData(userEmail, "Acad-Stress") ?: emptyList()

                val faceList = repository.getTodayAIData(userEmail, "Face")
                val speechList = repository.getTodayAIData(userEmail, "Speech")


//                println(moodList)
//                println(pssQuizItems)
//                println(dassQuizItems)

                // Check if we have all required data
                if (pssQuizItems.isEmpty() && dassQuizItems.isEmpty() && moodList.isEmpty() && acadQuizItems.isEmpty() && faceList.isEmpty() && speechList.isEmpty()) {
                    _stressState.value = StressUIState.Error("You have not given any of the test to measure your stress")
                    return@launch
                }

                // Extract required data for calculation
                val moodIds = moodList.map { it.id }
                val timestamps = moodList.map { it.timestamp }

                val faceIds = faceList.map { it.level }
                val timestampsFace = faceList.map { it.timestamp }

                val speechIds = speechList.map { it.level }
                val timestampsSpeech = speechList.map { it.timestamp }

                // Calculate final stress
                val stressScore = finalStress(
                    pssList = pssQuizItems,
                    dassList = dassQuizItems,
                    moodList = moodIds,
                    timeList = timestamps,
                    faceList = faceIds,
                    speechList = speechIds,
                    acadList = acadQuizItems,
                    faceTimeList = timestampsFace,
                    speechTimeList = timestampsSpeech
                )

                _stressState.value = StressUIState.Success(stressScore)
            } catch (e: Exception) {
                _stressState.value = StressUIState.Error("Error calculating stress: ${e.message}")
            }
        }
    }

    fun calculateDepressionForToday(userEmail: String) {
        viewModelScope.launch {
            try {

                _depressionState.value = DepressionUIState.Loading

                // Fetch all required data
                val bdiQuizItems = repository.getLatestQuizData(userEmail, "BDI") ?: emptyList()
                val dassQuizItems = repository.getLatestQuizData(userEmail, "DASS21") ?: emptyList()

                // Check if we have all required data
                if (bdiQuizItems.isEmpty() && dassQuizItems.isEmpty()) {
                    _depressionState.value = DepressionUIState.Error("You have not given any of the test to measure your Depression")
                    return@launch
                }



                // Calculate final depression
                val depressionScore = finalDepression(
                    bdiList = bdiQuizItems,
                    dassList = dassQuizItems,
                )

                _depressionState.value = DepressionUIState.Success(depressionScore)
            } catch (e: Exception) {
                _depressionState.value = DepressionUIState.Error("Error calculating stress: ${e.message}")
            }
        }
    }



    fun calculateAnxietyForToday(userEmail: String) {
        viewModelScope.launch {
            try {


                _anxietyState.value = AnxietyUIState.Loading

                // Fetch all required data
                val baiQuizItems = repository.getLatestQuizData(userEmail, "BAI") ?: emptyList()
                val dassQuizItems = repository.getLatestQuizData(userEmail, "DASS21") ?: emptyList()

                // Check if we have all required data
                if (baiQuizItems.isEmpty() && dassQuizItems.isEmpty()) {
                    _anxietyState.value = AnxietyUIState.Error("You have not given any of the test to measure your Anxiety")
                    return@launch
                }

                // Calculate final anxiety
                val anxietyScore = finalAnxiety(
                    baiList = baiQuizItems,
                    dassList = dassQuizItems
                )


                _anxietyState.value = AnxietyUIState.Success(anxietyScore)
            } catch (e: Exception) {
                _anxietyState.value = AnxietyUIState.Error("Error calculating stress: ${e.message}")
            }
        }
    }


    fun calculateStigmaForToday(userEmail: String) {
        viewModelScope.launch {
            try {

                _stigmaState.value = StigmaUIState.Loading

                // Fetch all required data
                val ismiQuizItems = repository.getLatestQuizData(userEmail, "ISMI") ?: emptyList()
                val daysQuizItems = repository.getLatestQuizData(userEmail, "Days") ?: emptyList()

                // Check if we have all required data
                if (ismiQuizItems.isEmpty() && daysQuizItems.isEmpty()) {
                    _stigmaState.value = StigmaUIState.Error("You have not given any of the test to measure your Mental Stigma")
                    return@launch
                }

                // Calculate final anxiety
                val stigmaScore = finalStigma(
                    ismiList = ismiQuizItems,
                    daysList = daysQuizItems
                )

                _stigmaState.value = StigmaUIState.Success(stigmaScore)
            } catch (e: Exception) {
                _stigmaState.value = StigmaUIState.Error("Error calculating stress: ${e.message}")
            }
        }
    }

}

// Sealed class for UI state
sealed class StressUIState {
    object Loading : StressUIState()
    data class Success(val stressValues: StressValues) : StressUIState()
    data class Error(val message: String) : StressUIState()
}

sealed class DepressionUIState {
    object Loading :DepressionUIState()
    data class Success(val depressionValues: DepressionValues) : DepressionUIState()
    data class Error(val message: String) : DepressionUIState()
}

sealed class AnxietyUIState {
    object Loading :AnxietyUIState()
    data class Success(val anxietyValues: AnxietyValues) : AnxietyUIState()
    data class Error(val message: String) : AnxietyUIState()
}

sealed class StigmaUIState {
    object Loading :StigmaUIState()
    data class Success(val stigmaValues: StigmaValues) : StigmaUIState()
    data class Error(val message: String) : StigmaUIState()
}