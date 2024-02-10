package com.example.keepsilent.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.keepsilent.R
import com.example.keepsilent.keepSilentApp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    modifier : Modifier = Modifier
){
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition.value
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        //Add launched effect to make delay and navigate to next screen
        LaunchedEffect(true){
            delay(4800)
            navController.navigate(keepSilentApp.GetStarted.name)
        }
        //Add lottie animation
        LottieAnimation(
            composition = composition.value ,
            progress = { progress },
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun splashScreenPreview(){
    SplashScreen(
        navController = rememberNavController()
    )
}