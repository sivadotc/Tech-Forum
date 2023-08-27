package com.example.techforum.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.techforum.DestinationScreen
import com.example.techforum.TfViewModel
import com.example.techforum.data.PostData
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont


@Composable
fun SinglePostScreen(navController: NavController, vm: TfViewModel, post: PostData) {

    val comments = vm.comments.value

    LaunchedEffect(key1 = Unit) {
        vm.getComments(post.postId)
    }

    post.userId?.let { 
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Back", modifier = Modifier.clickable { navController.popBackStack() })

            CommonDivider()

            SinglePostDisplay(
                navController = navController,
                vm = vm, post = post,
                nbComments = comments.size
            )

        }
    }
}

@Composable
fun SinglePostDisplay(navController: NavController, vm: TfViewModel, post: PostData, nbComments: Int) {
    val userData = vm.userData.value
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = CircleShape, modifier = Modifier.size(32.dp)
            ) {
                CommonImage(data = post.userImage, contentScale = ContentScale.Crop)

            }
            Text(text = post.username ?: "", modifier = Modifier.padding(start = 8.dp), style = nexaCustomFont.body2)
            Text(text = ".", modifier = Modifier.padding(8.dp))
            
            if (userData?.userId == post.userId) {
                //Current user's post. don't show anything
            } else if (userData?.following?.contains(post.userId) == true) {
                Text(
                    text = "Following",
                    color = Color.Gray,
                    modifier = Modifier.clickable { vm.onFollowClick(post.userId!!) }
                )
            } else {
                Text(
                    text = "Follow",
                    color = Blue,
                    modifier = Modifier.clickable { vm.onFollowClick(post.userId!!) }
                )
            }
        }

    }

    Box {
        val modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 150.dp)
        CommonImage(
            data = post.postImage,
            modifier = modifier,
            contentScale = ContentScale.FillWidth
        )
    }

    Row(modifier = Modifier.padding(top = 8.dp)) {
       // Text(text = post.username ?: "", style = nexaCustomFont.body2)
        Text(text = post.postDescription ?: "", modifier = Modifier.padding(start = 8.dp))
    }
    Row(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "$nbComments comments",
            color = Color.Gray,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    post.postId?.let {
                        navController.navigate(DestinationScreen.CommentsScreen.createRoute(it))
                    }
                }
        )
    }
}