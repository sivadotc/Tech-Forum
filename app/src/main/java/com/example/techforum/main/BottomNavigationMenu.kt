package com.example.techforum.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techforum.DestinationScreen
import com.example.techforum.R
import com.example.techforum.ui.theme.Blue

enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreen) {
    FEED(R.drawable.ic_home, DestinationScreen.Feed),
    SEARCH(R.drawable.ic_search, DestinationScreen.Search),
    POSTS(R.drawable.ic_person, DestinationScreen.MyPosts)
}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(Color.Transparent)
    ) {
       for (item in BottomNavigationItem.values()) {
           Image(
               painter = painterResource(id = item.icon),
               contentDescription = null,
               modifier = Modifier
                   .size(40.dp)
                   .padding(5.dp)
                   .weight(1f)
                   .clickable {
                       navigateTo(navController, item.navDestination)
                   },
               colorFilter = if (item == selectedItem) ColorFilter.tint(Blue)
               else ColorFilter.tint(Color.Gray)
           )
       }
    }
}