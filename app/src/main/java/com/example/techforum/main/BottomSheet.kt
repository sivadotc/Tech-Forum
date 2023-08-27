package com.example.techforum.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techforum.R
import com.example.techforum.ui.theme.Blue
import com.example.techforum.ui.theme.nexaCustomFont
import com.google.common.io.Files.append


@Composable
fun BottomSheetContent() {


    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = R.drawable.ic_drag), contentDescription = null)
        Text(text = "About Tech Forum", style = nexaCustomFont.body2)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(id = R.string.about_app1), fontSize = 14.sp, textAlign = TextAlign.Justify)
       // Spacer(modifier = Modifier.height(10.dp))
       // Text(text = stringResource(id = R.string.about_app2), fontSize = 14.sp, textAlign = TextAlign.Justify)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Developer Contact", style = nexaCustomFont.body2)
        ProfileImage("https://firebasestorage.googleapis.com/v0/b/tech-forum-939f0.appspot.com/o/profilepicture.jpg?alt=media&token=833cf753-b283-454a-b259-7c15a6716903")
        Text(text = "Siva", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "2019WA86989", fontSize = 14.sp)
        Text(text = "siva.chinnasamy@wipro.com", fontSize = 14.sp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 60.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            SocialMediaLink(icon = painterResource(id = R.drawable.ic_insta), inputUrl = "https://www.instagram.com/sivadotc/")
            SocialMediaLink(icon = painterResource(id = R.drawable.ic_linkedin), inputUrl = "https://www.linkedin.com/in/sivadotc/")
            SocialMediaLink(icon = painterResource(id = R.drawable.ic_github), inputUrl = "https://github.com/sivadotc")
            }
        }


}


@Composable
fun SocialMediaLink(icon: Painter, inputUrl: String) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(inputUrl)) }

    Icon(
        painter = icon,
        contentDescription = null, tint = Blue,
        modifier = Modifier
            .size(30.dp)
            .padding(end = 8.dp)
            .clickable(onClick = {
                context.startActivity(intent)
            }
            )
    )
    
}





