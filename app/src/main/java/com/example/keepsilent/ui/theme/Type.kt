package com.example.keepsilent.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.keepsilent.R

val JockeyOne = FontFamily(
    Font(R.font.jockeyone_regular)
)

val JostFont = FontFamily(
    Font(R.font.jost_medium)
)

val AbhayaLibre = FontFamily(
    Font(R.font.abhaya_libre)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    //For JockeyOne
    displayLarge = TextStyle(
        fontFamily = JockeyOne,
        fontWeight = FontWeight.Bold
    ),

    //For JoshFont
    displayMedium = TextStyle(
        fontFamily = JostFont,
    ),

    //For AbhayaLibre
    displaySmall = TextStyle(
        fontFamily = AbhayaLibre
    )
)
