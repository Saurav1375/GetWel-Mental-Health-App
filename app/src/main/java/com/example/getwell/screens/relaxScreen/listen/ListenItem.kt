package com.example.getwell.screens.relaxScreen.listen

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.getwell.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class ListenItem(
    val title: String,
    val imageResId: Int,
    val videoUri: String  = "seawaves.mp4",// Firebase storage path
    val musicUri: String  = "",// Firebase storage path for audio
    val volVideo: Float = 0f,
    val volAudio: Float = 1f
) : Parcelable


