package com.shadow_shift_studio.aniway.manga_card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import okhttp3.internal.wait
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun MangaPreviewCard() {
    val titleName = "Подземелье демона"
    val titleType = "Манхва"

    androidx.compose.material3.Card(
        modifier = Modifier
            .width(108.dp)
            .height(220.dp)
            .clickable {
            },
        colors = CardColors(md_theme_dark_background, md_theme_dark_background, md_theme_dark_background, md_theme_dark_background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
                contentDescription = "PreviewImage",
                modifier = Modifier
                    .width(108.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(17.dp))
            )
        }
        Row(
            modifier = Modifier
            .fillMaxWidth().padding(start = 8.dp),
            horizontalArrangement = Arrangement.Center
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
                .fillMaxWidth().padding(start = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = titleType, color = md_theme_dark_onSurfaceVariant, modifier = Modifier.width(108.dp), fontSize = 12.sp)
        }
    }
}


