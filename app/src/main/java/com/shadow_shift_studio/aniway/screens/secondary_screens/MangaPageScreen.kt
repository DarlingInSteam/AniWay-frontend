package com.shadow_shift_studio.aniway.screens.secondary_screens

import android.content.res.Resources.Theme
import android.widget.ListView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.chip.ChipGroup
import com.shadow_shift_studio.aniway.AddBookmarkButtonText
import com.shadow_shift_studio.aniway.ChaptersButtonText
import com.shadow_shift_studio.aniway.ReadButtonText
import com.shadow_shift_studio.aniway.SimilarWorksText
import com.shadow_shift_studio.aniway.cards.MangaPreviewCard
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onPrimary
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSecondaryContainer
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_primary
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_secondaryContainer
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun GradientImage(startColor: Color, endColor: Color) {
    val coverHeightPx = 273
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 17.dp,
                        topEnd = 17.dp,
                        bottomStart = 17.dp,
                        bottomEnd = 17.dp
                    )
                )
                .height(coverHeightPx.dp)
                .width(412.dp)
        )

        Box(
            modifier = Modifier
                .drawBehind {
                    val gradientBrush = Brush.verticalGradient(
                        colors = listOf(startColor, endColor),
                        startY = size.height - size.width,
                        endY = size.height
                    )

                    drawRect(brush = gradientBrush)
                }
                .padding(top = (coverHeightPx).dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomCenter)
                .background(Color.Transparent)
        )
    }
}

@Composable
fun MangaPage( navController: NavController)
{
    var titleName = "Токийские мстители"
    var titleType = "Манга"
    var year = "2018"
    var status = "Продолжается"
    var views = "1.5M"
    var likes = "21K"
    var bookMarks = "12K"
    var rating = "4,9"
    var description = "Король Грей обладает непревзойденной силой, богатством и престижем в мире, управляемом боевыми способностями. Однако одиночество тесно связано с теми, кто обладает большой властью. Под гламурной внешностью могущественного короля скрывается оболочка человека, лишенного целей и воли. Перевоплотившись в новом мире, наполненном магией и монстрами, король получает второй шанс вновь прожить свою жизнь. Однако исправление ошибок прошлого будет не единственной его задачей. Под миром и процветанием нового мира скрывается подводное течение, угрожающее разрушить все, ради чего он работал, подвергая сомнению его роль и причину рождения заново."

    GradientImage(
        startColor = Color.Transparent,
        endColor = md_theme_dark_background
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically


        ) {

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(100)),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonColors(
                    Color.White,
                    md_theme_dark_background,
                    Color.White,
                    Color.White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.ArrowBack, ""       )
            }
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .height(40.dp),
                containerColor = Color.White,
                contentColor = md_theme_dark_background
            ) {
                Icon(
                    Icons.Default.Add, "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                )
                Text(
                    text = AddBookmarkButtonText,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
                contentDescription = "",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp,
                            bottomStart = 25.dp,
                            bottomEnd = 25.dp
                        )
                    )
                    .height(202.dp)
                    .width(132.dp)
            )
        }
        Row(
            modifier = Modifier
                .width(450.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = titleName,
                color = Color.White,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Spacer(
            modifier = Modifier
                .height(6.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = year + " | ",
                color = md_theme_dark_onSurfaceVariant,
                fontSize = 14.sp
            )
            Text(
                text = titleType + " | ",
                color = md_theme_dark_onSurfaceVariant,
                fontSize = 14.sp
            )
            Text(
                text = status,
                color = md_theme_dark_onSurfaceVariant,
                fontSize = 14.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Bookmark,
                    contentDescription = "Eye",
                    tint = md_theme_dark_onSurfaceVariant,
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = bookMarks,
                    color = md_theme_dark_onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(7.dp))
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
            Spacer(modifier = Modifier.width(7.dp))
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
        
        Spacer(modifier = Modifier.height(11.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(113.dp)
                    .background(md_theme_dark_background),
                colors = ButtonColors(
                    md_theme_dark_primary,
                    md_theme_dark_onPrimary,
                    Color.White,
                    Color.White
                )
            ) {
                Icon(Icons.Default.Menu, "")
                Text(
                    text = ChaptersButtonText,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(113.dp)
                    .background(md_theme_dark_background),
                colors = ButtonColors(
                    md_theme_dark_secondaryContainer,
                    md_theme_dark_onSecondaryContainer,
                    Color.White,
                    Color.White
                )
            ) {
                Text(
                    text = ReadButtonText,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(11.dp))

        Genres(listOf("Мистика", "Приключения", "Фэнтези", "В цвете", "Демоны", "Зверолюди", "Магия", "Антигерой", "Заебал", "Ты меня"))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Justify,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                text = description
            )
        }
        Row(
            modifier = Modifier
                .padding(vertical =10.dp)
        )
        {
            Text(
                modifier = Modifier
                    .padding(start=10.dp),
                text = SimilarWorksText,
                fontSize = 20.sp,
                textAlign = TextAlign.Left
            )
        }

    }
}

@Composable
fun Genres(genresList: List<String>) {
    val maxGenresPerRow = 3
    val maxRows = 2
    val totalGenres = genresList.size
    val remainingGenres = remember { mutableStateOf(genresList.drop(maxGenresPerRow * maxRows)) }
    val showRemainingGenres = remember { mutableStateOf(false) }

    Column (modifier = Modifier.padding(start = 18.dp, end = 18.dp)){
        for (rowIndex in 0 until maxRows) {
            val startIdx = rowIndex * maxGenresPerRow
            val endIdx = minOf((rowIndex + 1) * maxGenresPerRow, genresList.size)
            Row {
                for (index in startIdx until endIdx) {
                    GenreButton(genresList[index])
                }
            }
        }

        if (showRemainingGenres.value) {
            val chunkedGenres = remainingGenres.value.chunked(maxGenresPerRow)
            chunkedGenres.forEach { rowGenres ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowGenres.forEach { genre ->
                        GenreButton(genre)
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { showRemainingGenres.value = false },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "Скрыть")
                }
            }
        } else if (remainingGenres.value.isNotEmpty()) {
            TextButton(
                onClick = { showRemainingGenres.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Показать ${remainingGenres.value.size} жанров")
            }
        }
    }
}


@Composable
fun GenreButton(genre: String) {
    Button(
        modifier = Modifier
            .height(40.dp)
            .padding(4.dp),
        onClick = {},
        colors = ButtonColors(md_theme_dark_onSurface, Color.White, Color.White, Color.White)
    ) {
        Text(text = genre)
    }
}

