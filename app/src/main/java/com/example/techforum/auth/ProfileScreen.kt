package com.example.techforum.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.CustomOutlinedButton
import com.example.techforum.DestinationScreen
import com.example.techforum.TfViewModel
import com.example.techforum.main.CommonDivider
import com.example.techforum.main.CommonImage
import com.example.techforum.main.CommonProgressSpinner
import com.example.techforum.main.navigateTo
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont

@Composable
fun ProfileScreen(navController: NavController, vm: TfViewModel) {
    val isLoading = vm.inProgress.value
    if (isLoading) {
        CommonProgressSpinner()
    } else {
       val userData = vm.userData.value
       var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
       var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
       var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }

        ProfileContent(
            vm = vm,
            name = name,
            username = username,
            bio = bio,
            onNameChange = { name = it },
            onUsernameChange = { username = it },
            onBioChange = { bio = it },
            onSave = { vm.updateProfileData(name, username, bio) },
            onBack = { navigateTo(navController = navController, DestinationScreen.MyPosts) },
            onLogout = {
                vm.onLogout()
                navigateTo(navController, DestinationScreen.Login)
            }
        )
    }
}

@Composable
fun ProfileContent(
    vm: TfViewModel,
    name: String,
    username: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()
    val imageUrl = vm.userData?.value?.imageUrl
    val focus = LocalFocusManager.current
    
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() })
        }

        CommonDivider()

        ProfileImage(imageUrl = imageUrl, vm = vm)

        CommonDivider()

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.padding(8.dp),
            label = { Text(text = "Name", style = nexaCustomFont.body1) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Blue,
                cursorColor = Blue,
                focusedLabelColor = Blue
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focus.clearFocus() }
            )
        )
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            modifier = Modifier.padding(8.dp),
            label = { Text(text = "Username", style = nexaCustomFont.body1) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Blue,
                cursorColor = Blue,
                focusedLabelColor = Blue
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focus.clearFocus() }
            )
        )
        OutlinedTextField(
            value = bio,
            onValueChange = onBioChange,
            modifier = Modifier.padding(8.dp),
            label = { Text(text = "Bio", style = nexaCustomFont.body1) },
            singleLine = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Blue,
                cursorColor = Blue,
                focusedLabelColor = Blue,
                trailingIconColor = Blue
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
            keyboardActions = KeyboardActions(
                onDone = { focus.clearFocus() }
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        
        CustomOutlinedButton(text = "Logout") {
            onLogout.invoke()
        }

    }
}

@Composable
fun ProfileImage(imageUrl: String?, vm: TfViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { vm.uploadProfileImage(uri) }

    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { launcher.launch("image/*") },
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                shape = CircleShape, 
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(data = imageUrl)
            }
            Text(text = "Change profile picture")
        }
        
        val isLoading = vm.inProgress.value
        if (isLoading)
            CommonProgressSpinner()
    }
}
