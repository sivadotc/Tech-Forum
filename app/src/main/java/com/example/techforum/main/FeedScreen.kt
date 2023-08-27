package com.example.techforum.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.DestinationScreen
import com.example.techforum.R
import com.example.techforum.TfViewModel
import com.example.techforum.data.PostData
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(navController: NavController, vm: TfViewModel) {
    val userDataLoading = vm.inProgress.value
    val userData = vm.userData.value
    val personalizedFeed = vm.postsFeed.value
    val personalizedFeedLoading = vm.postsFeedProgress.value

    val newPostImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val encoded = Uri.encode(it.toString())
            val route = DestinationScreen.NewPost.createRoute(encoded)
            navController.navigate(route)
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(20.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                BottomSheetContent()
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        newPostImageLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .padding(bottom = 80.dp, end = 10.dp),
                    shape = RoundedCornerShape(15.dp),
                    contentColor = Color.White,
                    backgroundColor = Blue
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.techforum_logo),
                            contentDescription = null
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info1),
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                                .padding(end = 8.dp)
                                .clickable {

                                    coroutineScope.launch {
                                        if (bottomSheetState.isVisible) {
                                            bottomSheetState.hide()
                                        } else {
                                            bottomSheetState.show()
                                        }
                                    }
                                }
                        )
                    }

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
        )
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
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation = 10.dp
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