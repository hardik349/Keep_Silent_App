package com.example.keepsilent.design

import android.content.ContentValues.TAG
import android.service.autofill.UserData
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.keepsilent.R
import com.example.keepsilent.keepSilentApp
import com.example.keepsilent.Authentication.signIn.SignInState
import com.example.keepsilent.Authentication.signUp.SignupViewModel
import com.example.keepsilent.Authentication.signUp.UserModel
import com.example.keepsilent.ui.theme.md_theme_light_primary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    signupNavController : NavController,
    onEmailValueChanged : (String) -> Unit,
    onPasswordValueChanged : (String) -> Unit,
    signupButtonClicked : () -> Unit,
    loginButtonClicked : () -> Unit,
    modifier: Modifier = Modifier
){
    var emailId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible = remember { mutableStateOf(false) }
    var matchError = remember { mutableStateOf(false) }

    val viewModel = viewModel<SignupViewModel>()
    LaunchedEffect(key1 = viewModel.signupSuccessful){
        if (viewModel.signupSuccessful.value == true) {
            signupNavController.navigate(keepSilentApp.MainScreen.name){
                popUpTo(keepSilentApp.Signup.name) {
                    inclusive = true
                }
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(150.dp),
            verticalAlignment = Alignment.Top
        ) {

            //Insert image on left side corner
            Image(
                painter = painterResource(R.drawable.ellipse_7) ,
                contentDescription = "design1",
            )

            //Insert image on right side corner
            Image(
                painter = painterResource(R.drawable.ellipse_8),
                contentDescription = "design2",
                modifier = modifier.size(120.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.signup_caps),
                fontSize = 30.sp,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 40.dp)
            )
            //TextField for email id
            TextField(
                value = emailId,
                onValueChange = {
                    emailId = it
                    onEmailValueChanged(it) },
                label = { Text(text = stringResource(R.string.email))},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = modifier
                    .height(80.dp)
                    .padding(bottom = 22.dp, start = 5.dp, end = 5.dp)
                    .clip(RoundedCornerShape(25.dp))
            )
            //TextField for password
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    onPasswordValueChanged(it) },
                label = { Text(text = stringResource(R.string.password))},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    val iconImage = if(passwordVisible.value){
                        Icons.Filled.Visibility
                    }else{
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }
                    ) {
                        Icon(
                            imageVector = iconImage,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation =
                if (passwordVisible.value) VisualTransformation.None else
                    PasswordVisualTransformation(),
                modifier = modifier
                    .height(80.dp)
                    .padding(bottom = 22.dp, start = 5.dp, end = 5.dp)
                    .clip(RoundedCornerShape(25.dp))
            )
            //TextField for confirm password
            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                label = { Text(text = stringResource(R.string.confirm_password))},
                trailingIcon = {
                    val iconImage = if(passwordVisible.value){
                        Icons.Filled.Visibility
                    }else{
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(onClick = { confirmPasswordVisible.value = !confirmPasswordVisible.value }
                    ) {
                        Icon(
                            imageVector = iconImage,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation =
                if (confirmPasswordVisible.value) VisualTransformation.None else
                    PasswordVisualTransformation(),
                modifier = modifier
                    .height(80.dp)
                    .padding(bottom = 22.dp, start = 5.dp, end = 5.dp)
                    .clip(RoundedCornerShape(25.dp))
            )
            //Check if password and confirm password matches
            if(confirmPassword != password){
                Text(
                    text = stringResource(R.string.error_message_no_match),
                    color = Color.Red,
                    fontSize = 10.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "ConfirmPasswordMessage" }
                        .padding(start = 22.dp, bottom = 15.dp)
                )
                matchError.value = true
            }else{
                matchError.value = false
            }
            //TextButton for signup button
            TextButton(
                onClick = {
                        signupButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(md_theme_light_primary),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 50.dp, end = 50.dp)
            ) {
                Text(
                    text = stringResource(R.string.signup),
                    fontSize = 22.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding( start = 5.dp, end = 5.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier

            ) {
                Text(
                    text = stringResource(R.string.already_have_acc),
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 15.sp
                )
                TextButton(
                    onClick = { loginButtonClicked()},
                    modifier = modifier
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.displayMedium,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun SignupScreenPreview(){
    SignupScreen(
        signupNavController = rememberNavController(),
        onEmailValueChanged = {},
        onPasswordValueChanged = {},
        signupButtonClicked = {},
        loginButtonClicked = {}
    )
}
