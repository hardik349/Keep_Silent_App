package com.example.keepsilent.design


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.keepsilent.R
import com.example.keepsilent.ui.theme.md_theme_light_primary

@Composable
fun GetStartedScreen(
    loginButtonClicked : () -> Unit,
    signupButtonClicked : () -> Unit,
    modifier : Modifier = Modifier
){
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.getstartedanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            //Add lottie Animation
            LottieAnimation(
                composition = composition.value,
                progress = { progress},
                modifier = modifier
                    .size(500.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.background)
            )
            Text(
                text = stringResource(R.string.welcome),
                fontSize = 25.sp,
                style = MaterialTheme.typography.displayLarge,
                modifier = modifier
                    .padding(bottom = 7.dp)
            )
            Text(
                text = stringResource(R.string.welcome_desc),
                style = MaterialTheme.typography.displayMedium,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(bottom = 15.dp)
            )

            //OutlinedButton for login button
            OutlinedButton(
                onClick = { loginButtonClicked() },
                colors = ButtonDefaults.buttonColors(md_theme_light_primary),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 6.dp)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 20.sp
                )
            }

            //OutlinedButton for signup button
            OutlinedButton(
                onClick = {signupButtonClicked()},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)
            ) {
                Text(
                    text = stringResource(R.string.signup),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
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
    }
}

@Preview(showBackground = true)
@Composable
fun GetStartedScreenPreview(){
    GetStartedScreen(
        loginButtonClicked = {},
        signupButtonClicked = {}
    )
}