package com.example.techforum.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.CustomButton
import com.example.techforum.CustomOutlinedButton
import com.example.techforum.DestinationScreen
import com.example.techforum.LottieAnimation
import com.example.techforum.TfViewModel
import com.example.techforum.main.navigateTo
import com.example.techforum.R
import com.example.techforum.main.CheckSignedIn
import com.example.techforum.main.CommonProgressSpinner
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(navController: NavController, vm: TfViewModel) {

    CheckSignedIn(vm = vm, navController = navController)

    val coroutineScope = rememberCoroutineScope()
    val focus = LocalFocusManager.current
    val bringIntViewRequester = BringIntoViewRequester()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailState = remember { mutableStateOf(TextFieldValue()) }
            val passState = remember { mutableStateOf(TextFieldValue()) }

            //Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.techforum_logo),
                contentDescription = null,
                Modifier.size(150.dp)
            )
            LottieAnimation(aniUrl = "https://lottie.host/5fd63085-d089-4046-b251-442988b1ed0e/qNTiUbxGiP.lottie")
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Email", style = nexaCustomFont.body1) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_mail),
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Blue,
                    cursorColor = Blue,
                    focusedLabelColor = Blue
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                value = passState.value,
                onValueChange = { passState.value = it },
                modifier = Modifier
                    .padding(8.dp)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringIntViewRequester.bringIntoView()
                            }

                        }
                    },
                label = { Text(text = "Password", style = nexaCustomFont.body1) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_lock),
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focus.clearFocus() }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Blue,
                    cursorColor = Blue,
                    focusedLabelColor = Blue
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(text = "Login") {
                focus.clearFocus(force = true)
                vm.onLogin(emailState.value.text, passState.value.text)
            }
            CustomOutlinedButton(text = "Signup") {
                navigateTo(navController, DestinationScreen.Signup)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "New here? click on Signup",
                modifier = Modifier
                    .padding(8.dp)
                    .bringIntoViewRequester(bringIntViewRequester)
            )
        }

        val isLoading = vm.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }
    }

}