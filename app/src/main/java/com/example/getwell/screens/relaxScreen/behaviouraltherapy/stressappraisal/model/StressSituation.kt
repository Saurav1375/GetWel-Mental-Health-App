package com.example.getwell.screens.relaxScreen.behaviouraltherapy.stressappraisal.model

data class StressSituation(
    val description: String = "",
    var situationType: SituationType? = null,
    var control: ControlType? = null,
    var impact: ImpactType? = null,
    var resources: ResourceType? = null
)

enum class SituationType {
    CHALLENGING,
    THREATENING,
    HARMFUL
}

enum class ControlType {
    SELF,
    OTHERS,
    FATE
}

enum class ImpactType {
    SPECIFIC,
    ALL_ASPECTS,
    OTHERS_AND_SELF
}

enum class ResourceType {
    OWN_CAPABILITY,
    HELP_FROM_OTHERS,
    NEITHER
}

enum class AppraisalResult {
    POSITIVE,
    NEUTRAL,
    NEGATIVE
}