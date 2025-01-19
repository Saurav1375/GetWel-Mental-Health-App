package com.example.getwell.data

data class StressValues(
    val stressValue : Double,
    val pss : Double,
    val dss : Double,
    val acad : Double,
    val face : Double,
    val speech : Double
)

data class DepressionValues(
    val depressionValue : Double,
    val bdi : Double,
    val dssDep : Double
)

data class AnxietyValues(
    val anxietyValue : Double,
    val bai : Double,
    val dssAnx : Double
)

data class StigmaValues(
    val stigmaValue : Double,
    val ismi : Double,
    val days : Double
)