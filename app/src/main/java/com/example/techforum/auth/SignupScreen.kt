package com.example.techforum.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.techforum.DestinationScreen
import com.example.techforum.TfViewModel
import com.example.techforum.R
import com.example.techforum.SignupAnimation
import com.example.techforum.main.CheckSignedIn
import com.example.techforum.main.CommonProgressSpinner
import com.example.techforum.main.navigateTo
import com.example.techforum.ui.theme.Typography
import com.example.techforum.ui.theme.nexa
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreen(navController: NavController, vm: TfViewModel){

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

        ){
            val usernameState = remember { mutableStateOf(TextFieldValue()) }
            val emailState = remember { mutableStateOf(TextFieldValue()) }
            val passState = remember { mutableStateOf(TextFieldValue()) }
            
            SignupAnimation(aniUrl = "https://lottie.host/24c71afa-ebcb-41a6-8f2c-82dcf6cb6fdc/5XtWSAAPHz.lottie")
            Text(
                text = "Signup", style = Typography.h1,
                modifier = Modifier.padding(top = 0.dp, bottom = 8.dp)
            )
            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Username", style = Typography.body1) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_person),
                        modifier = Modifier.size(30.dp),
                        contentDescription = null
                    )
                }
            )
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier
                    .padding(8.dp)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringIntViewRequester.bringIntoView()
                            }

                        }
                    },
                label = { Text(text = "Email", style = Typography.body1) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_mail),
                        modifier = Modifier.size(30.dp),
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focus.clearFocus() }
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
                label = { Text(text = "Password", style = Typography.body1) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_lock),
                        modifier = Modifier.size(30.dp),
                        contentDescription = null
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focus.clearFocus() }
                )
            )
            Button(
                onClick = {
                    focus.clearFocus(force = true)
                          vm.onSignup(
                              usernameState.value.text,
                              emailState.value.text,
                              passState.value.text
                          )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "SIGN UP", style = Typography.body2)
            }
            Text(
                text = "Already a user? Go to Login ->", style = Typography.body2,
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp).bringIntoViewRequester(bringIntViewRequester)
                    .clickable {
                        navigateTo(navController, DestinationScreen.Login)
                    }
            )
        }

        val isLoading = vm.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }
    }
}