package com.example.keepsilent.design

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.keepsilent.R
import com.example.keepsilent.keepSilentApp
import com.example.keepsilent.Authentication.login.LoginViewModel
import com.example.keepsilent.Authentication.signIn.SignInState
import com.example.keepsilent.ui.theme.md_theme_light_primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state : SignInState,
    navController : NavHostController,
    viewModel: LoginViewModel,
    onEmailChanged : (String) -> Unit,
    onPasswordChanged : (String) -> Unit,
    loginButtonClicked : () -> Unit,
    signupButtonClicked : () -> Unit,
    googleButtonClicked : () -> Unit,
    facebookButtonClicked : () -> Unit,
    modifier: Modifier = Modifier
){
    var emailId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.loginSuccessful.value){
        if(viewModel.loginSuccessful.value == true){
            navController.navigate(keepSilentApp.MainScreen.name){
                popUpTo(keepSilentApp.Login.name){
                    inclusive = true
                }
            }
      }
    }
  //  val context2 = LocalContext.current
//    LaunchedEffect(viewModel.errorMessage.value){
        //val errorMessage = viewModel.errorMessage.value
        //if(!errorMessage.isNullOrBlank()){
           // coroutineScope.launch {
                //Toast.makeText(
                  //  context2,
                //    errorMessage,
              //      Toast.LENGTH_SHORT
            //    ).show()
          //  }
        //    viewModel.errorMessageHandled()
      //  }
    //}

    //If there is any error in signIn show context
    val context = LocalContext.current
    LaunchedEffect(key1 = state.errorMessage){
        state.errorMessage?.let {error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
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
                text = stringResource(R.string.login_caps),
                fontSize = 30.sp,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 40.dp)
            )
            TextField(
                value = emailId,
                onValueChange = {
                    emailId = it
                    onEmailChanged(it) },
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
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    onPasswordChanged(it) },
                label = { Text(text = stringResource(R.string.password))},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
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
            TextButton(
                onClick = { loginButtonClicked() },
                colors = ButtonDefaults.buttonColors(md_theme_light_primary),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 50.dp, end = 50.dp)
            ) {
                Text(
                    text = stringResource(R.string.login),
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
                    text = stringResource(R.string.dont_have_acc),
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 15.sp
                )
                TextButton(
                    onClick = { signupButtonClicked()},
                    modifier = modifier
                ) {
                    Text(
                        text = stringResource(R.string.signup),
                        style = MaterialTheme.typography.displayMedium,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Box(
                modifier = modifier
                    .padding(start = 30.dp, end = 30.dp, top = 60.dp)
            ) {
                Divider(thickness = 1.dp)
            }
            TextButton(
                onClick = { googleButtonClicked() },
                colors = ButtonDefaults.buttonColors(md_theme_light_primary),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 60.dp, end = 60.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = "google_icon",
                        modifier = modifier
                            .size(35.dp)
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    Text(
                        text = stringResource(R.string.login_with_google),
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            TextButton(
                onClick = { facebookButtonClicked() },
                colors = ButtonDefaults.buttonColors(md_theme_light_primary),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 60.dp, end = 60.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = "facebook_icon",
                        modifier = modifier
                            .size(35.dp)
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    Text(
                        text = stringResource(R.string.login_with_facebook),
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(
        state = SignInState(),
        navController = rememberNavController(),
        viewModel = LoginViewModel(),
        onEmailChanged = {},
        onPasswordChanged = {},
        loginButtonClicked = {},
        signupButtonClicked = {},
        googleButtonClicked = {},
        facebookButtonClicked = {}
    )
}