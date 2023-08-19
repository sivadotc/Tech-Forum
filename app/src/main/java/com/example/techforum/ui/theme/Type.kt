package com.example.techforum.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.techforum.R

val nexa = FontFamily(
    listOf(
        Font(R.font.nexa_light, FontWeight.Light),
        Font(R.font.nexa_bold, FontWeight.Bold)
    )
)


val Typography = Typography(
    body1 = TextStyle(
        fontFamily = nexa,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = nexa,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = nexa,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    h2 = TextStyle(
        fontFamily = nexa,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = nexa,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    h4 = TextStyle(
        fontFamily = nexa,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    )
)