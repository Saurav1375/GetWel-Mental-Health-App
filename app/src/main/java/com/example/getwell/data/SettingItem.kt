package com.example.getwell.data

import androidx.annotation.DrawableRes

data class SettingItem(
    val id: Int = 0,
    val title: String,
    @DrawableRes val icon: Int,

)