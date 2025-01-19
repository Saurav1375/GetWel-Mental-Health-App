package com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.AppraisalResult
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.ControlType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.ImpactType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.ResourceType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.SituationType
import com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model.StressSituation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StressAppraisalViewModel : ViewModel() {
    private val _situations = MutableStateFlow<List<StressSituation>>(List(3) { StressSituation() })
    val situations: StateFlow<List<StressSituation>> = _situations.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    fun updateSituationDescription(index: Int, description: String) {
        val updatedList = _situations.value.toMutableList()
        updatedList[index] = updatedList[index].copy(description = description)
        _situations.value = updatedList
    }

    fun updateSituationType(index: Int, type: SituationType) {
        val updatedList = _situations.value.toMutableList()
        updatedList[index] = updatedList[index].copy(situationType = type)
        _situations.value = updatedList
    }

    fun updateControl(index: Int, control: ControlType) {
        val updatedList = _situations.value.toMutableList()
        updatedList[index] = updatedList[index].copy(control = control)
        _situations.value = updatedList
    }

    fun updateImpact(index: Int, impact: ImpactType) {
        val updatedList = _situations.value.toMutableList()
        updatedList[index] = updatedList[index].copy(impact = impact)
        _situations.value = updatedList
    }

    fun updateResources(index: Int, resources: ResourceType) {
        val updatedList = _situations.value.toMutableList()
        updatedList[index] = updatedList[index].copy(resources = resources)
        _situations.value = updatedList
    }

    fun nextStep() {
        _currentStep.value++
    }

    fun previousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value--
        }
    }

    fun calculateAppraisal(situation: StressSituation): AppraisalResult {
        var score = 0
        
        // Count 'a' answers (positive)
        if (situation.situationType == SituationType.CHALLENGING) score++
        if (situation.control == ControlType.SELF) score++
        if (situation.impact == ImpactType.SPECIFIC) score++
        if (situation.resources == ResourceType.OWN_CAPABILITY) score++

        // Count 'c' answers (negative)
        var negativeScore = 0
        if (situation.situationType == SituationType.HARMFUL) negativeScore++
        if (situation.control == ControlType.FATE) negativeScore++
        if (situation.impact == ImpactType.OTHERS_AND_SELF) negativeScore++
        if (situation.resources == ResourceType.NEITHER) negativeScore++

        return when {
            score >= 3 -> AppraisalResult.POSITIVE
            negativeScore >= 3 -> AppraisalResult.NEGATIVE
            else -> AppraisalResult.NEUTRAL
        }
    }

    fun reset() {
        // Reset the currentStep to 0
        _currentStep.value = 0
        // Reset the situations list to its initial state (3 default StressSituation objects)
        _situations.value = List(3) { StressSituation() }
    }

}