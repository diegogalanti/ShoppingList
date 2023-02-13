package com.gallardo.shoppinglist.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gallardo.shoppinglist.R

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 64.sp,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 52.sp,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 44.sp,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 40.sp,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 36.sp,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
        lineHeight = 28.sp,
        fontSize = 22.sp),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.crafty_girls_regular)),
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 16.sp,
        fontSize = 11.sp,
        fontWeight = FontWeight.W500
    )
)