package com.example.getwell.data

import androidx.annotation.DrawableRes
import com.example.getwell.R

data class ChatroomItem(
    val id: Long = 0L,
    val title: String,
    val description: String,
    @DrawableRes val icon : Int = R.drawable._icon__google_
)