package com.example.getwell.data

import androidx.annotation.DrawableRes

data class RelaxItem(
    val route: String = "",
    val title: String,
    @DrawableRes val banner : Int
)