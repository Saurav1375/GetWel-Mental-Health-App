package com.example.getwell.data

import androidx.annotation.DrawableRes
import com.example.getwell.R

data class ResourceItem (
    val title: String,
    @DrawableRes val icon : Int,
    val route: String = "",
)

val resourceItemList = listOf(
    ResourceItem(
        title = "Educational content",
        icon = R.drawable.resource_icon,
        route = Screen.EducationScreen.route
    ),ResourceItem(
        title = "Practical Guides",
        icon = R.drawable.baseline_video_library_24,
        route = Screen.PracticalGuidesScreen.route
    ),ResourceItem(
        title = "Support Resources",
        icon = R.drawable.vector__2_,
        route = Screen.SupportResourcesScreen.route
    ),ResourceItem(
        title = "Nearby Wellness Centers",
        icon = R.drawable.hospital,
        route = Screen.WellnessCenters.route
    )
)