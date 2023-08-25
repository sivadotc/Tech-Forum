package com.example.techforum.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.techforum.CustomOutlinedButton
import com.example.techforum.DestinationScreen
import com.example.techforum.LottieAnimation
import com.example.techforum.TfViewModel
import com.example.techforum.R
import com.example.techforum.data.PostData
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont


data class PostRow(
    var post1: PostData? = null,
    var post2: PostData? = null,
    var post3: PostData? = null
) {
    fun isFull() = post1 != null && post2 != null && post3 != null
    fun add(post: PostData) {
        if (post1 == null) {
            post1 = post
        } else if (post2 == null) {
            post2 = post
        } else if (post3 == null) {
            post3 = post
        }
    }
}

@Composable
fun MyPostsScreen(navController: NavController, vm: TfViewModel) {

    val newPostImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ uri ->
        uri?.let {
            val encoded = Uri.encode(it.toString())
            val route = DestinationScreen.NewPost.createRoute(encoded)
            navController.navigate(route)
        }

    }

    val userData = vm.userData.value
    val isLoading = vm.inProgress.value

    val postsLoading = vm.refreshPostsProgress.value
    val posts = vm.posts.value

    val followers = vm.followers.value

    Column() {
        Spacer(modifier = Modifier.height(30.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(userData?.imageUrl) {
                    newPostImageLauncher.launch("image/*")
                }
            }
            val usernameDisplay = if (userData?.username == null) "" else "${userData?.username}"
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = userData?.name ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "@$usernameDisplay"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${posts.size}",
                        style = nexaCustomFont.body2,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Posts",
                        textAlign = TextAlign.Center
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$followers",
                        style = nexaCustomFont.body2,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Followers",
                        textAlign = TextAlign.Center
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${userData?.following?.size ?: 0}",
                        style = nexaCustomFont.body2,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Following",
                        textAlign = TextAlign.Center
                    )
                }

            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "About Me",style = nexaCustomFont.body2)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable { navigateTo(navController, DestinationScreen.Profile) },
                            tint = Blue
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = userData?.bio ?: "")
                }
            }

            PostList(
                isContextLoading = isLoading,
                postsLoading = postsLoading,
                posts = posts,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .fillMaxSize(),
            ) { post ->
                navigateTo(
                    navController = navController,
                    DestinationScreen.SinglePost,
                    NavParam("post", post)
                )
            }

            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.POSTS,
                navController = navController
            )
        }

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
                border = BorderStroke(2.dp, color = Color.Gray),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    //modifier = Modifier.background(Color.Blue)
                )
            }


    }
}


@Composable
fun PostList(
    isContextLoading: Boolean,
    postsLoading: Boolean,
    posts: List<PostData>,
    modifier: Modifier,
    onPostClick: (PostData) -> Unit
) {
    if (postsLoading) {
        CommonProgressSpinner()
    } else if (posts.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isContextLoading) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        // .fillMaxSize()
                        .size(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(aniUrl = "https://lottie.host/c5d77710-fcfb-4a0f-97fa-f00d2ac09d81/Drch4bQtte.lottie")

                }
                Text(text = "No posts available", style = nexaCustomFont.body2)
            }
        }
    } else {
        LazyColumn(modifier = modifier) {

            val rows = arrayListOf<PostRow>()
            var currentRow = PostRow()
            rows.add(currentRow)
            for (post in posts) {
                if (currentRow.isFull()) {
                    currentRow = PostRow()
                    rows.add(currentRow)
                }
                currentRow.add(post = post)
            }

            items(items = rows) { row ->
                PostsRow(item = row, onPostClick = onPostClick)
            }
        }
    }
}

@Composable
fun PostsRow(item: PostRow, onPostClick: (PostData) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        PostImage(
            imageUrl = item.post1?.postImage,
            modifier = Modifier
                .weight(1f)
                .clickable { item.post1?.let { post -> onPostClick(post) } }
        )
        PostImage(
            imageUrl = item.post2?.postImage,
            modifier = Modifier
                .weight(1f)
                .clickable { item.post2?.let { post -> onPostClick(post) } }
        )
        PostImage(
            imageUrl = item.post3?.postImage,
            modifier = Modifier
                .weight(1f)
                .clickable { item.post3?.let { post -> onPostClick(post) } }
        )

    }
}

@Composable
fun PostImage(imageUrl: String?, modifier: Modifier) {
    Box(modifier = modifier) {
        var modifier = Modifier
            .padding(1.dp)
            .fillMaxSize()
        if (imageUrl == null) {
            modifier = modifier.clickable(enabled = false) {}
        }
        CommonImage(data = imageUrl, modifier = modifier, contentScale = ContentScale.Crop)
    }
}

@Composable
fun CustomCard(postNum: String, text: String, icon: Int, modifier: Modifier) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Blue)){
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = postNum, style = nexaCustomFont.body2, fontSize = 12.sp)
                Text(text = text, fontSize = 12.sp)
            }
        }

    }
}


