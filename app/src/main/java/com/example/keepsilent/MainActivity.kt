package com.example.keepsilent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keepsilent.design.GetStartedScreen
import com.example.keepsilent.design.LoginScreen
import com.example.keepsilent.design.MainScreen
import com.example.keepsilent.design.MapScreen
import com.example.keepsilent.design.SignupScreen
import com.example.keepsilent.design.SplashScreen
import com.example.keepsilent.Authentication.login.LoginUiEvent
import com.example.keepsilent.Authentication.login.LoginViewModel
import com.example.keepsilent.Authentication.signIn.GoogleAuthUiClient
import com.example.keepsilent.Authentication.signIn.SignInState
import com.example.keepsilent.Authentication.signIn.SignInViewModel
import com.example.keepsilent.Authentication.signUp.SignupUiEvent
import com.example.keepsilent.Authentication.signUp.SignupViewModel
import com.example.keepsilent.Authentication.signUp.UserModel

import com.example.keepsilent.ui.theme.KeepSilentTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private fun isGooglePlayServicesAvailable() : Boolean{
        val resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        return resultCode == ConnectionResult.SUCCESS
    }
    //private variable for google authentication client
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isGooglePlayServicesAvailable()){
                Log.d("TAG","Google play services available")
            }else{
                Log.d("MainActivity","NOt available")
            }
            val navController = rememberNavController()
            KeepSilentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Add NavHost
                    NavHost(
                        navController = navController,
                        startDestination = keepSilentApp.Splash.name
                    ){
                        composable(route = keepSilentApp.Splash.name){
                            SplashScreen(
                                navController = navController   
                            )
                        }

                        composable(route = keepSilentApp.GetStarted.name){
                            GetStartedScreen(
                                loginButtonClicked = {
                                    navController.navigate(keepSilentApp.Login.name)
                                                     },
                                signupButtonClicked = {
                                    navController.navigate(keepSilentApp.Signup.name)
                                })
                        }

                        composable(route = keepSilentApp.Login.name){
                            val viewModel = viewModel<SignInViewModel>()
                            val viewsModel = viewModel<LoginViewModel>()
                            val state by viewModel.state.collectAsState()
                            val signupNavController = rememberNavController()
                            
                            //If login successful navigate to MainScreen
                           /* LaunchedEffect(viewsModel.loginSuccessful.value){
                                if(viewsModel.loginSuccessful.value == true){
                                    navController.navigate(keepSilentApp.MainScreen.name)
                                }
                            }*/
                            //If there is any error message show toast
                            val context = LocalContext.current
                            LaunchedEffect(viewsModel.errorMessage.value ){
                                val errorMessage = viewsModel.errorMessage.value
                                if(!errorMessage.isNullOrBlank()){
                                    Toast.makeText(
                                        context,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                viewsModel.errorMessageHandled()
                            }
                            //Add launchedEffect to check if user is already logged in or not
                            LaunchedEffect(key1 = Unit){
                                if(googleAuthUiClient.getSignInUser() != null){
                                    navController.navigate(keepSilentApp.MainScreen.name)
                                }
                            }
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = {result ->
                                    if(result.resultCode == RESULT_OK){
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            //Try if signIn successful
                            LaunchedEffect(key1 = state.isSignInSuccessful){
                                if(state.isSignInSuccessful){
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign In Successful!!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    //After login is successful navigate to MainScreen
                                    navController.navigate(keepSilentApp.MainScreen.name)
                                    viewModel.resetSate()
                                }
                            }

                            LoginScreen(
                                state = SignInState(),
                                navController = navController,
                                viewModel = viewsModel,
                                onEmailChanged = {
                                    viewsModel.Event(LoginUiEvent.EmailChanged(it))
                                },
                                onPasswordChanged = {
                                    viewsModel.Event(LoginUiEvent.PasswordChanged(it))
                                },
                                loginButtonClicked = {
                                    viewsModel.Event(LoginUiEvent.LoginButtonClicked)
                                },
                                signupButtonClicked = {
                                    navController.navigate(keepSilentApp.Signup.name)
                                },
                                googleButtonClicked = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },

                                facebookButtonClicked = {},
                            )
                        }

                        composable(route = keepSilentApp.Signup.name){
                            val viewModel = viewModel<SignupViewModel>()
                            val signupNavController = rememberNavController()


                            //If there is any error message show toast
                            val context = LocalContext.current
                            LaunchedEffect(viewModel.errorMessage.value ){
                                val errorMessage = viewModel.errorMessage.value
                                if(!errorMessage.isNullOrBlank()){
                                    Toast.makeText(
                                        context,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                viewModel.errorMessageHandled()
                            }

                            SignupScreen(
                                signupNavController = signupNavController,
                                onEmailValueChanged = {
                                    viewModel.Event(SignupUiEvent.EmailChanged(it))
                                },
                                onPasswordValueChanged = {
                                    viewModel.Event(SignupUiEvent.PasswordChanged(it))
                                },
                                signupButtonClicked = {
                                    viewModel.Event(SignupUiEvent.SignupButtonClicked)
                                },
                                loginButtonClicked = {
                                    navController.navigate(keepSilentApp.Login.name)
                                }
                            )
                        }

                        composable(route = keepSilentApp.MainScreen.name){
                            MainScreen(
                                userdata = googleAuthUiClient.getSignInUser(),
                                onIconButtonClicked = {
                                    navController.navigate(keepSilentApp.MapScreen.name)
                                },
                                onSignOut ={
                                    lifecycleScope.launch{
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out!!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }

                        composable(route = keepSilentApp.MapScreen.name){
                            MapScreen()
                        }

                    }
                }
            }
        }
    }
}

//enum class to add screens
enum class keepSilentApp{
    Splash,
    GetStarted,
    Login,
    Signup,
    MainScreen,
    MapScreen
}