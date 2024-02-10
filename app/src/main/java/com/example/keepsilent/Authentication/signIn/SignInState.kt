package com.example.keepsilent.Authentication.signIn

data class SignInState (
    val isSignInSuccessful : Boolean = false,
    val errorMessage : String? = null
)