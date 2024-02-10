package com.example.keepsilent.Authentication.signUp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.keepsilent.Authentication.signIn.SignInResult
import com.example.keepsilent.Authentication.signIn.Userdata
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel : ViewModel() {
    private val TAG = SignupViewModel::class.simpleName
    var userUiState = mutableStateOf(Userdata())
    var signupInProgress = mutableStateOf(false)
    var signupCredentials = mutableStateOf(false)

    private val _signSuccessful = MutableLiveData<Boolean>()
    val signupSuccessful : LiveData<Boolean>
        get() = _signSuccessful
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String>
        get() = _errorMessage

    fun Event (event : SignupUiEvent){
        when(event){
            is SignupUiEvent.UsernameChanged -> {
                userUiState.value = userUiState.value.copy(
                    username =event.username
                )
                printState()
            }
            is SignupUiEvent.EmailChanged -> {
                userUiState.value = userUiState.value.copy(
                    email = event.email
                )
                printState()
            }
            is SignupUiEvent.PasswordChanged -> {
                userUiState.value = userUiState.value.copy(
                    password = event.password
                )
                printState()
            }
            is SignupUiEvent.SignupButtonClicked -> {
                Log.d(TAG,"Signup button Clicked")
                signup()
            }
        }
    }

    //fun for signup
    private fun signup() {
        Log.d(TAG,"Inside_Signup")
        printState()
        createUserInFirebase(
            email = userUiState.value.email,
            password = userUiState.value.password
        )
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, userUiState.value.toString())
    }

    //fun to create user in firebase
    private fun createUserInFirebase(email : String, password : String){
        signupInProgress.value = true
        if(email.isEmpty() || password.isEmpty()){
            //Handle empty email and password error
            _errorMessage.value = "Email and Password cannot be empty"
            return
        }
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.d(TAG,"inside_OnCompleteListener")
                Log.d(TAG,"is_Successful = ${it.isSuccessful}")
                //After done with signup change signupInProgress value
                signupInProgress.value = false
                if(it.isSuccessful){
                    _signSuccessful.value = true
                    //Current user variable
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    //Create new user in Firestore
                    val userRef = FirebaseFirestore.getInstance().collection("users").document(currentUser!!.uid)
                    //Create UserModel object
                    val user = UserModel(
                        emailId = email,
                        password = password,
                    )
                    userRef
                        .set(user)
                        .addOnSuccessListener {
                            Log.d(TAG,"Document created successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.d(TAG,"error",e)
                        }
                }else{
                    _signSuccessful.value = false
                    _errorMessage.value = it.exception?.message ?: "Signup Failed"
                }
            }
            .addOnFailureListener {it ->
                Log.d(TAG,"inside_OnCanceledListener")
                Log.d(TAG,"Exception = ${it.message}")
                Log.d(TAG,"Exception = ${it.localizedMessage}")
                _signSuccessful.value = false
                _errorMessage.value = it.message ?: "Signup Failed"
            }
    }
    //Handle error message
    fun errorMessageHandled(){
        _errorMessage.value = null
    }
}