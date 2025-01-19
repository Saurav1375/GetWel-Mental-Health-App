package com.example.getwell.screens.resourcesection.wellnesscentersfinder.data

data class WellnessCenter(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val type: String?,
    val distance: Double?
)