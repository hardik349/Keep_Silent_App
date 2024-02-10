package com.example.keepsilent.Authentication.login

import android.app.usage.UsageEvents.Event
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.keepsilent.Authentication.rules.Validator
import com.example.keepsilent.Authentication.signIn.Userdata
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    val loginUiState = mutableStateOf(Userdata())
    val loginInProgress = mutableStateOf(false)
    val allValidationPassed = mutableStateOf(false)
    var loginCredentials = mutableStateOf(false)

    private val _loginSuccessful = MutableLiveData<Boolean>()
    val loginSuccessful : LiveData<Boolean>
        get() = _loginSuccessful
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String>
        get() = _errorMessage

    fun Event(event : LoginUiEvent){
        when(event){
            is LoginUiEvent.EmailChanged -> {
                loginUiState.value = loginUiState.value.copy(
                    email = event.email
                )
                printState()
            }
            is LoginUiEvent.PasswordChanged -> {
                loginUiState.value = loginUiState.value.copy(
                    password = event.password
                )
                printState()
            }
            is LoginUiEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUiRules()
    }

    private fun validateLoginUiRules(){
        //check for email
        val emailResult = Validator.validateEmail(
            email =loginUiState.value.email
        )
        //check for password
        val passwordResult = Validator.validatePassword(
            password = loginUiState.value.password
        )
        loginUiState.value = loginUiState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        allValidationPassed.value =emailResult.status && passwordResult.status
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG,loginUiState.value.toString())
    }

    private fun login() {
        loginInProgress.value = true
        val email = loginUiState.value.email
        val password = loginUiState.value.password
        if(email.isEmpty() || password.isEmpty()){
            //Handle empty email and password error
            _errorMessage.value = "Email and Password cannot be empty"
            return
        }
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.d(TAG,"inside_login_successful")
                Log.d(TAG,"${it.isSuccessful}")

                loginInProgress.value = false
                if(it.isSuccessful){
                    _loginSuccessful.value = true
                }else{
                    _loginSuccessful.value = false
                    _errorMessage.value = it.exception?.message ?: "Login Failed"
                }
            }
            .addOnFailureListener {it ->
                loginInProgress.value = false
                Log.d(TAG,"inside_login_failure")
                Log.d(TAG, it.localizedMessage)
                _loginSuccessful.value = false
                _errorMessage.value = it.message ?: "Login Failed"
            }
    }

    //Handle error message
    fun errorMessageHandled(){
        _errorMessage.value = null
    }
}