package com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens

import CommentCard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.AddBookmarkButtonText
import com.shadow_shift_studio.aniway.ChaptersButtonText
import com.shadow_shift_studio.aniway.GenresButtonText
import com.shadow_shift_studio.aniway.LastCommentButtonText
import com.shadow_shift_studio.aniway.ReadButtonText
import com.shadow_shift_studio.aniway.SimilarWorksText
import com.shadow_shift_studio.aniway.view.cards.MangaPreviewCard
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onPrimary
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSecondaryContainer
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_primary
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_secondaryContainer
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_high
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel

@Composable
fun MangaPage(navController: NavController, viewModelBottom: BottomNavBarViewModel) {
    val navControllerMangaPage = rememberNavController()
    NavHost(navController = navControllerMangaPage, startDestination = "main") {
        composable("main") {
            viewModelBottom.setFirstVisibleItemIndex(true)

            val scrollState = rememberScrollState()
            var description =
                "Король Грей обладает непревзойденной силой, богатством и престижем в мире, управляемом боевыми способностями. Однако одиночество тесно связано с теми, кто обладает большой властью. Под гламурной внешностью могущественного короля скрывается оболочка человека, лишенного целей и воли. Перевоплотившись в новом мире, наполненном магией и монстрами, король получает второй шанс вновь прожить свою жизнь. Однако исправление ошибок прошлого будет не единственной его задачей. Под миром и процветанием нового мира скрывается подводное течение, угрожающее разрушить все, ради чего он работал, подвергая сомнению его роль и причину рождения заново."

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Row(modifier = Modifier) {
                        GradientImage(
                            startColor = Color.Transparent,
                            endColor = md_theme_dark_background
                        )
                    }
                    Row {
                        TopMangaBar(navController = navController, viewModelBottom)
                    }
                    Spacer(
                        modifier = Modifier
                            .height(100.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 120.dp)
                    ) {
                        MangaInfo()
                    }
                }
                Spacer(
                    modifier = Modifier.height(11.dp)
                )

                MangaActionsButtons(navControllerMangaPage)

                Spacer(
                    modifier = Modifier.height(11.dp)
                )

                Genres(
                    listOf(
                        "Мистика",
                        "Приключения",
                        "Фэнтези",
                        "В цвете",
                        "Демоны",
                        "Зверолюди",
                        "Кто",
                        "Прочитал",
                        "Тот",
                        "Лапочка"
                    )
                )
                Spacer(modifier = Modifier.height(11.dp))

                ExpandableText(text = description)

                Spacer(modifier = Modifier.height(11.dp))

                SimilarWorks(navController)

                Spacer(modifier = Modifier.height(11.dp))

                CommentsMangaPage(navController = navControllerMangaPage)
            }
        }
        composable("commentsScreen") {
            AddComment(navControllerMangaPage)
        }
        composable("chaptersScreen")
        {
            ChaptersScreen(navControllerMangaPage)
        }
        composable("ReadScreen")
        {
            viewModelBottom.setFirstVisibleItemIndex(false)
            ReadScreen(navControllerMangaPage, viewModelBottom)
        }
    }
}

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
fun TopMangaBar(navController: NavController, viewModelBottom: BottomNavBarViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 23.dp, end = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top


    ) {

        ExtendedFloatingActionButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .height(40.dp)
                .width(60.dp)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(100)),
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.White,
            contentColor = md_theme_dark_background
        ) {
            Icon(
                Icons.Default.ArrowBack, "",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
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
}

@Composable
fun MangaInfo() {
    var titleName = "Токийские мстители"
    var titleType = "Манга"
    var year = "2018"
    var status = "Продолжается"
    var views = "1.5M"
    var likes = "21K"
    var bookMarks = "12K"
    Column {
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
    }
}

@Composable
fun MangaActionsButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ExtendedFloatingActionButton(
            onClick = { navController.navigate("chaptersScreen") },
            modifier = Modifier
                .width(115.dp)
                .background(Color.Transparent)
                .height(40.dp),
            shape = RoundedCornerShape(28.dp),
            containerColor = md_theme_dark_primary,
            contentColor = md_theme_dark_onPrimary
        ) {
            Icon(
                Icons.Default.Menu, "",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Text(
                text = ChaptersButtonText,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        ExtendedFloatingActionButton(
            onClick = { navController.navigate("ReadScreen") },
            modifier = Modifier
                .width(115.dp)
                .background(Color.Transparent)
                .height(40.dp),
            shape = RoundedCornerShape(28.dp),
            containerColor = md_theme_dark_secondaryContainer,
            contentColor = md_theme_dark_onSecondaryContainer
        ) {
            Icon(
                Icons.Default.AutoStories, "",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Text(
                text = ReadButtonText,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun Genres(genresList: List<String>) {
    var currentRow by remember { mutableStateOf(0) }
    var expended by remember { mutableStateOf(false) }
    val isGenreExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
    ) {
        Button(
            onClick = { isGenreExpanded.value = !isGenreExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                Color.White,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = GenresButtonText,
                    fontSize = 16.sp,
                    color = md_theme_light_surfaceVariant
                )
                if (!isGenreExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isGenreExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                ChipVerticalGrid(
                    spacing = 7.dp,
                    modifier = Modifier
                        .padding(7.dp),
                    onRowChange = { newCurrentRow ->
                        currentRow = newCurrentRow
                        if (currentRow == 2) expended = true
                    }
                ) {
                    genresList.forEach { word ->
                        Text(
                            word,
                            color = md_theme_dark_onSurface,
                            modifier = Modifier
                                .background(
                                    color = md_theme_dark_surface_container_high,
                                    shape = RoundedCornerShape(20)
                                )
                                .padding(vertical = 3.dp, horizontal = 5.dp)
                                .clickable { }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    onRowChange: (Int) -> Unit,
    content: @Composable () -> Unit,
) {
    var currentRow = 0

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                onRowChange(currentRow) // Вызываем функцию высшего порядка для передачи текущего значения currentRow

                currentOrigin =
                    currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }


        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 5,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value
    var textAlignJustify by remember { mutableStateOf(TextAlign.Justify) }
    var textAlignLeft by remember { mutableStateOf(TextAlign.Left) }


    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(
        modifier = Modifier
            .padding(start = 23.dp, end = 23.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = if (expanded) textAlignJustify else textAlignLeft,
            onTextLayout = { textLayoutResultState.value = it },
            color = Color.White,
            fontSize = 16.sp
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(
                "... Раскрыть",
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else
                            Modifier
                    )
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f),
                color = md_theme_dark_onSurface,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SimilarWorks(navController: NavController) {
    Column {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
        {
            Text(
                modifier = Modifier
                    .padding(start = 23.dp),
                text = SimilarWorksText,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = md_theme_light_surfaceVariant
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MangaPreviewCard(navController)
            MangaPreviewCard(navController)
            MangaPreviewCard(navController)
        }
    }
}

@Composable
fun CommentsMangaPage(navController: NavController) {
    Column {
        Row {
            Button(
                onClick = { navController.navigate("commentsScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 23.dp),
                colors = ButtonColors(
                    md_theme_dark_bottom_sheet_bottoms,
                    Color.White,
                    Color.White,
                    Color.White
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = LastCommentButtonText,
                        fontSize = 16.sp,
                        color = md_theme_light_surfaceVariant
                    )
                    Icon(Icons.Default.ArrowRight, contentDescription = "")
                }
            }
        }

        Spacer(modifier = Modifier.height(11.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .height(450.dp),
            content = {
                items(count = 3) { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 12.dp, end = 23.dp, start = 23.dp)
                            .fillMaxWidth()
                    ) {
                        CommentCard()
                    }
                }
            }
        )
    }
}








