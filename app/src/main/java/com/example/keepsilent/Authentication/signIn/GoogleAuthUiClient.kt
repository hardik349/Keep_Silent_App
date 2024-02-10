package com.example.keepsilent.Authentication.signIn

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.keepsilent.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val oneTapClient : SignInClient,
    private val context: Context
) {
    private val auth = Firebase.auth
    //1.Made request to firebase to sign in
    suspend fun signIn() : IntentSender? {
        val result = try{
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    //2.Use buildInSignRequest function to made request
    private fun buildSignInRequest() : BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    //3.Get the response from firebase
    suspend fun signInWithIntent(intent : Intent) : SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    Userdata(
                        userId =uid,
                        username = displayName,
                        userImageUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )

        }
    }

    //4.After done for signIn make a function for sign out
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    //5.Make fun for getSignInUser
    fun getSignInUser() : Userdata? = auth.currentUser?.run {
        Userdata(
            userId = uid,
            username = displayName,
            userImageUrl = photoUrl?.toString()
        )
    }
}