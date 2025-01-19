package com.example.getwell.data

import com.example.getwell.R

data class NavItem (
    val title: String,
    val route: String,
    val icon: Int
)

val navItemList =  listOf(
    NavItem(
        title ="Home",
        route = Screen.HomeScreen.route,
        icon =  R.drawable.home_icon
    ),
    NavItem(
        title ="Chatroom",
        route = Screen.ChatroomScreen.route,
        icon = R.drawable.chatnavicon
    ),
    NavItem(
        title ="Relax",
        route = Screen.RelaxScreen.route,
        icon = R.drawable.game_icon__1_
    ),
    NavItem(
        title ="Resources",
        route = Screen.EducationScreen.route,
        icon = R.drawable.resource_icon
    ),
    NavItem(
        title ="ChatBot",
        route = Screen.ChatBot.route,
        icon = R.drawable.chatbot
    ),
    NavItem(
        title ="Profile",
        route = Screen.ProfileScreen.route,
        icon = R.drawable.profile_icon
    )

)