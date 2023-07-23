package com.example.techforum.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.TfViewModel
import com.example.techforum.R

@Composable
fun MyPostsScreen(navController: NavController, vm: TfViewModel) {

    val userData = vm.userData.value
    val isLoading = vm.inProgress

    Column() {
        Column(modifier = Modifier.weight(1f)) {
            Row {
                ProfileImage(userData?.imageUrl) {
                    
                }
                Text(
                    text = "15\nPosts",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "45\nFollowers",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "93\nFollowing",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                val usernameDisplay = if (userData?.username == null) "" else "@${userData?.username}"
                Text(text = userData?.name ?: "", fontWeight = FontWeight.Bold)
                Text(text = usernameDisplay)
                Text(text = userData?.bio ?: "")
            }
            OutlinedButton(onClick = { },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                shape = RoundedCornerShape(10)
            ) {
                Text(text = "Edit Profile")
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Posts list")
            }

        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.POSTS,
            navController = navController
        )
    }

}

@Composable
fun ProfileImage(imageUrl: String?, onClick: () -> Unit) {
    Box(modifier = Modifier
        .padding(top = 16.dp)
        .clickable { onClick.invoke() }) {
            UserImageCard(
                userImage = imageUrl,
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
            )
            Card(
                shape = CircleShape,
                border = BorderStroke(2.dp, color = Color.White),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.background(Color.Blue)
                )
            }

    }
}