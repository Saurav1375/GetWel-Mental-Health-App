package com.example.getwell.authSystem

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val isSignUpSuccessful: Boolean = false,
    val signInError : String? = null
    )


