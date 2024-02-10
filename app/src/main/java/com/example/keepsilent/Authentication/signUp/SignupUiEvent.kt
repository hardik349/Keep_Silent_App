package com.example.keepsilent.Authentication.signUp

sealed class SignupUiEvent {
    data class UsernameChanged(val username : String) : SignupUiEvent()
    data class EmailChanged(val email : String) : SignupUiEvent()
    data class PasswordChanged(val password : String) : SignupUiEvent()

    object SignupButtonClicked : SignupUiEvent()
}