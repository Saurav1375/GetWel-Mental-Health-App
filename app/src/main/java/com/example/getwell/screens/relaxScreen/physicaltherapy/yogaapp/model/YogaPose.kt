package com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.model

data class YogaPose(
    val id: Int,
    val name: String,
    val sanskritName: String,
    val description: String,
    val benefits: List<String>,
    val instructions: List<String>,
    val imageUrl: Int,
    val duration: Int // in minutes
)