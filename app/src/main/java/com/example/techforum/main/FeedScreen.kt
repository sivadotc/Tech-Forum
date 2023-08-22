package com.example.techforum.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.DestinationScreen
import com.example.techforum.R
import com.example.techforum.TfViewModel
import com.example.techforum.data.PostData
import com.example.techforum.ui.theme.nexaCustomFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FeedScreen(navController: NavController, vm: TfViewModel) {
    val userDataLoading = vm.inProgress.value
    val userData = vm.userData.value
    val personalizedFeed = vm.postsFeed.value
    val personalizedFeedLoading = vm.postsFeedProgress.value
    
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
            //.background(Color.LightGray)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.techforumlogo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(60.dp)
        )
        PostsList(
            posts = personalizedFeed,
            modifier = Modifier.weight(1f),
            loading = personalizedFeedLoading or userDataLoading,
            navController = navController,
            vm = vm,
            currentUserId = userData?.userId ?: ""
        )

        BottomNavigationMenu(selectedItem = BottomNavigationItem.FEED, navController = navController)
    }
}

@Composable
fun PostsList(
    posts: List<PostData>,
    modifier: Modifier,
    loading: Boolean,
    navController: NavController,
    vm: TfViewModel,
    currentUserId: String
) {
    Box(modifier = modifier) {
        LazyColumn {
            items(items = posts) {
                Post(post = it, currentUserId = currentUserId, vm) {
                    navigateTo(
                    navController,
                    DestinationScreen.SinglePost,
                    NavParam("post", it)
                  )
                }
            }
        }
        if (loading)
            CommonProgressSpinner()
    }
}

@Composable
fun Post(post: PostData, currentUserId: String, vm: TfViewModel, onPostClick: () -> Unit) {

    //val likeAnimation = remember { mutableStateOf(false) }
    //val dislikeAnimation = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 8.dp).shadow(100.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                ) {
                   CommonImage(data = post.userImage, contentScale = ContentScale.Crop)
                }
                Text(text = post.username ?: "", style = nexaCustomFont.body2, modifier = Modifier.padding(4.dp))
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
               val modifier = Modifier
                   .fillMaxWidth()
                   .defaultMinSize(minHeight = 150.dp)
                   .pointerInput(Unit) {
                       detectTapGestures(
                           onTap = {
                               onPostClick.invoke()
                           }
                       )
                   }
                CommonImage(
                    data = post.postImage,
                    modifier = modifier,
                    contentScale = ContentScale.FillWidth
                )

            }
        }
    }
}