package com.example.techforum.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.TfViewModel


@Composable
fun AboutScreen(navController: NavController, vm: TfViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Text(text = "This is about screen")
    }
}