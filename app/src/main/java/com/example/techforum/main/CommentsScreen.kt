package com.example.techforum.main


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.DestinationScreen
import com.example.techforum.TfViewModel
import com.example.techforum.data.CommentData
import org.w3c.dom.Comment


@Composable
fun CommentsScreen(navController: NavController, vm: TfViewModel, postId: String) {

    var commentText by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val comments = vm.comments.value
    val commentsProgress = vm.commentsProgress.value
    
    Column(modifier = Modifier.fillMaxSize()) {
        
        if (commentsProgress) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonProgressSpinner()
            }
        } else if (comments.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No Comments available")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items = comments) { comment ->
                    CommentRow(comment)
                }
            }
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            TextField(
                value = commentText,
                onValueChange = { commentText = it },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color.LightGray),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Button(onClick = {
                vm.createComment(postId = postId, text = commentText)
                commentText = ""
                focusManager.clearFocus()
            },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Comment")
            }
        }
    }
}

@Composable
fun CommentRow(comment: CommentData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = comment.username ?: "", fontWeight = FontWeight.Bold)
        Text(text = comment.text ?: "", modifier = Modifier.padding(start = 8.dp))
    }
}
