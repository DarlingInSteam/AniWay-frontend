package com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens

import CommentCard
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.ui.theme.md_theme_light_surfaceVariant

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddComment(navController: NavController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack, "", modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                    )
                }
            }
        },
        bottomBar = {
            CommentTextField()
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 100.dp),
            ) {
                CommentsFullScreen()
            }
        }
    )
}

@Composable
fun CommentTextField() {
    var comment by remember { mutableStateOf("") }
    val maxLength = 350
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f),
                value = comment,
                enabled = true,
                onValueChange = { if (it.length <= maxLength) comment = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
            )
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Send, ""
                )
            }
        }
        Row {
            Text(
                text = "${comment.length} / $maxLength",
                textAlign = TextAlign.Start,
                color = md_theme_light_surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CommentsFullScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(450.dp),
        content = {
            items(count = 25) { index ->
                Row(
                    modifier = Modifier
                        .padding(end = 23.dp, start = 23.dp)
                        .fillMaxWidth()
                ) {
                    CommentCard()
                }
                Spacer(modifier = Modifier.height(11.dp))
            }
        }
    )
}



