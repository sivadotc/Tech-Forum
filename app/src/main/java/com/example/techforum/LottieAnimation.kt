package com.example.techforum

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimation(aniUrl: String) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url(aniUrl))
    com.airbnb.lottie.compose.LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
}

@Composable
fun SignupAnimation(aniUrl: String) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url(aniUrl))
    com.airbnb.lottie.compose.LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.size(300.dp).padding(0.dp)
    )
}