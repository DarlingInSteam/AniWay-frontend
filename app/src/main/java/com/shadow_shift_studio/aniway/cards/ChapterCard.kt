package com.shadow_shift_studio.aniway.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadow_shift_studio.aniway.ChapterText
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_primary
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ChapterCard() {
    val isChapterClosed by remember { mutableStateOf(true) }
    val isChapterLiked by remember { mutableStateOf(false) }
    val isChapterDownloaded by remember { mutableStateOf(true) }

    var volumeNumber: Int = 1
    var chapterNumber: Int = 1
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    var date = LocalDate.parse("08-05-2023", formatter)
    var likesCount: Int = 521
    val space: String = " "

    var text: String = "$ChapterText$space$chapterNumber"

    Card(
        modifier = Modifier
            .clickable { },
        colors = CardColors(
            md_theme_dark_background,
            Color.White,
            md_theme_dark_background,
            md_theme_dark_background
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Column() {
                Row() {
                    Text(
                        text = volumeNumber.toString(),
                        fontSize = 22.sp,
                        color = md_theme_dark_onSurfaceVariant
                    )
                    Text(
                        text = text,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Icon(
                        Icons.Default.Lock, "",
                        modifier = Modifier
                            .alpha(if (isChapterClosed) 1f else 0f)
                    )
                }
            }
            Column(){
                Row(){
                    Text(
                        text = date.toString(),
                        fontSize = 17.sp,
                        color = md_theme_dark_onSurfaceVariant
                    )
                    Icon(
                        Icons.Default.Favorite, "", tint = if(isChapterLiked) md_theme_dark_primary else Color.White
                    )
                    Text(
                        text = likesCount.toString(),
                        fontSize = 17.sp,
                        color = md_theme_dark_primary
                    )
                    Icon(
                        Icons.Default.Download, ""
                    )
                }
            }

        }
    }
}