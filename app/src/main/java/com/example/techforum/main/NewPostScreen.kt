package com.example.techforum.main

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.techforum.TfViewModel
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont


@Composable
fun NewPostScreen(navController: NavController, vm: TfViewModel, encodedUri: String) {
    val imageUri by remember { mutableStateOf(encodedUri) }
    var description by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Cancel", modifier = Modifier.clickable { navController.popBackStack() })
        Text(text = "Post", modifier = Modifier.clickable {
            focusManager.clearFocus()
            vm.onNewPost(Uri.parse(imageUri), description) { navController.popBackStack() }
        })
        }
        
        CommonDivider()
        
        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 150.dp),
            contentScale = ContentScale.FillWidth
        )
        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = description,
                onValueChange = {description = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                label = { Text(text = "Description", style = nexaCustomFont.body1) },
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Blue,
                    cursorColor = Blue,
                    focusedLabelColor = Blue
                )
            )
        }
    }

    val inProgress = vm.inProgress.value
    if (inProgress)
        CommonProgressSpinner()
}
