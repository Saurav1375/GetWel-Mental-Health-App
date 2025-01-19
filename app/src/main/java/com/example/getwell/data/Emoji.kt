package com.example.getwell.data

import androidx.annotation.DrawableRes

data class Emoji(
    val id: Int,
    @DrawableRes val icon: Int,
    val description : String = "",
    var selected: Boolean = false,
    val weight: Int = 0,
)