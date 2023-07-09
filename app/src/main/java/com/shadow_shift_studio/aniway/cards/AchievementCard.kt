package com.shadow_shift_studio.aniway.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadow_shift_studio.aniway.R
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_outlineVariant

@Composable
fun AchievementCard() {
    val titleName = "Реинкарнация короля демонов, убивающего богов"
    val titleType = "Манхва"
    val description =
        "Предоставьте управление своим подчиненным и живите жизнью мечты! С такими амбициями Джоруссия - демон низкого ранга, стал мастером подземелья. Однако, поскольку место, где было построено подземелье, является особым местом, называемым Землей Хаоса, талантливый демон приходит работать секретаршей, а темный бог приходит в гости. Кроме того, несколько авантюрист... "
    val chapters = "60 Глав"
    val views = "1.5M"
    val likes = "21K"
    val rating = "4,9"

    androidx.compose.material3.Card(
        modifier = Modifier
            .height(90.dp)
            .clickable { },
        colors = CardColors(
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth().background(md_theme_dark_outlineVariant)) {
            Column(modifier = Modifier.fillMaxHeight().padding(start = 12.dp), verticalArrangement = Arrangement.Center) {
                ImageAchievement()
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Добро пожаловать",
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
                        text = "Прочитать 10 тайтлов",
                        color = md_theme_dark_onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ImageAchievement() {
    Column() {
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.ava),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    }
}