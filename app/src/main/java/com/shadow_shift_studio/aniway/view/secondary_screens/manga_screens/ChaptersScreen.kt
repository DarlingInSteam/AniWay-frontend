package com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.ChapterSText
import com.shadow_shift_studio.aniway.view.cards.ChapterCard

@Composable
fun ChaptersScreen(navController: NavController) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack, "", modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                )
            }
            Text(text = ChapterSText, fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.height(11.dp))
        Chapters()
    }
}

@Composable
fun Chapters() {
    var count: Int = 25
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            items(count = count) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ChapterCard()
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    )
}