package com.shadow_shift_studio.aniway.view.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
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
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun MangaCardTop(navController: NavController) {
    val titleName = "Реинкарнация короля демонов, убивающего богов"
    val titleType = "Манхва"
    val description =
        "Предоставьте управление своим подчиненным и живите жизнью мечты! С такими амбициями Джоруссия - демон низкого ранга, стал мастером подземелья. Однако, поскольку место, где было построено подземелье, является особым местом, называемым Землей Хаоса, талантливый демон приходит работать секретаршей, а темный бог приходит в гости. Кроме того, несколько авантюрист... "
    val chapters = "60 Глав"
    val views = "1.5M"
    val likes = "21K"
    val rating = "4,9"

    Card(
        modifier = Modifier
            .height(160.dp)
            .clickable { navController.navigate("fullScreen") },
        colors = CardColors(
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(rating)
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = titleName,
                        color = Color.White,
                        fontSize = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = titleType,
                        color = md_theme_dark_onSurfaceVariant,
                        fontSize = 12.sp
                    )
                    Text(
                        text = chapters,
                        color = md_theme_dark_onSurfaceVariant,
                        fontSize = 12.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.RemoveRedEye,
                            contentDescription = "Eye",
                            tint = md_theme_dark_onSurfaceVariant,
                            modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = views,
                            color = md_theme_dark_onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Eye",
                            tint = md_theme_dark_onSurfaceVariant,
                            modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = likes,
                            color = md_theme_dark_onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = description,
                        color = Color.White,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
fun Image(rating: String) {
    Box(
        modifier = Modifier
            .width(108.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(17.dp))
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
            contentDescription = "PreviewImage",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .clip(RoundedCornerShape(bottomEnd = 10.dp))
                .background(md_theme_dark_background)
        ) {
            Row {
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
    }
}
