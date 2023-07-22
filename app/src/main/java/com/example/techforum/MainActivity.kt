package com.example.techforum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.techforum.auth.LoginScreen
import com.example.techforum.auth.SignupScreen
import com.example.techforum.main.FeedScreen
import com.example.techforum.main.NotificationMessage
import com.example.techforum.ui.theme.TechForumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechForumTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TechForumApp()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Feed: DestinationScreen("feed")
}

@Composable
fun TechForumApp(){
    val vm = hiltViewModel<TfViewModel>()
    val navController = rememberNavController()
    
    NotificationMessage(vm = vm)
    
    NavHost(navController = navController, startDestination = DestinationScreen.Signup.route ) {
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Feed.route) {
            FeedScreen(navController = navController, vm = vm)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TechForumTheme {
       TechForumApp()
    }
}