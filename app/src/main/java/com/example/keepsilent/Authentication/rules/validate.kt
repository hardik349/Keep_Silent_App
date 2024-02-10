package com.example.keepsilent.Authentication.rules

object Validator {

    fun validateEmail(email : String) : validateResult {
        return validateResult(
            (!email.isNullOrEmpty())
        )
    }

    fun validatePassword(password : String) : validateResult {
        return validateResult(
            (!password.isNullOrEmpty() && password.length >= 6)
        )
    }

    data class validateResult(
        val status : Boolean = false
    )
}