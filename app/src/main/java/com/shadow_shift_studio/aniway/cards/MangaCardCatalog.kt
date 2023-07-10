package com.shadow_shift_studio.aniway.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun MangaPreviewCard(navController: NavController) {
    val titleName = "Подземелье демона"
    val titleType = "Манхва"
    val rating = "4,9"

    androidx.compose.material3.Card(
        modifier = Modifier
            .width(108.dp)
            .widthIn(108.dp, 108.dp)
            .height(220.dp)
            .clickable { navController.navigate("fullScreen") },
        colors = CardColors(md_theme_dark_background, md_theme_dark_background, md_theme_dark_background, md_theme_dark_background)
    ) {
        Column(modifier = Modifier.fillMaxWidth().width(108.dp)) {
            Box(
                modifier = Modifier
                    .widthIn(108.dp, 108.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(17.dp))

            ) {
                AsyncImage(
                    model = "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
                    contentDescription = "PreviewImage",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(bottomEnd = 10.dp))
                        .background(md_theme_dark_background),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = rating,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 4.dp),
                        fontSize = 14.sp
                    )
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Star",
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp)
                            .padding(top = 4.dp),
                        tint = md_theme_light_tertiaryContainer
                    )
                }

            }
            Column(
                modifier = Modifier
                    .widthIn(108.dp, 108.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = titleName,
                        color = Color.White,
                        modifier = Modifier.width(108.dp),
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = titleType,
                        color = md_theme_dark_onSurfaceVariant,
                        modifier = Modifier.width(108.dp),
                        fontSize = 12.sp
                    )
                }
            }

        }
    }
}



