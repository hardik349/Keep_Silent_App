package com.example.keepsilent.Authentication.signIn

data class SignInResult(
    val data : Userdata?,
    val errorMessage : String?
)

data class Userdata (
    val userId : String = "",
    val username : String? = "",
    val userImageUrl : String? = "",

    val email : String = "",
    val password : String = "",
    val confirmPassword : String = "",

    val emailError : Boolean = false,
    val passwordError : Boolean = false

)