package com.example.techforum.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.techforum.TfViewModel

@Composable
fun FeedScreen(navController: NavController, vm: TfViewModel) {
    Text(text = "Feed Screen")
}