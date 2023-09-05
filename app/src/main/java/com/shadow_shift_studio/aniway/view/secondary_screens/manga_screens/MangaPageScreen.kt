package com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens

import CommentCard
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.BookmarksAbandoned
import com.shadow_shift_studio.aniway.BookmarksAlreadyRead
import com.shadow_shift_studio.aniway.BookmarksFavorite
import com.shadow_shift_studio.aniway.BookmarksReading
import com.shadow_shift_studio.aniway.BookmarksWillRead
import com.shadow_shift_studio.aniway.ChaptersButtonText
import com.shadow_shift_studio.aniway.ReadButtonText
import com.shadow_shift_studio.aniway.SimilarWorksText
import com.shadow_shift_studio.aniway.data.singleton_object.Navbar
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Genre
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import com.shadow_shift_studio.aniway.model.enum.toFormattedString
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onPrimary
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSecondaryContainer
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_primary
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_secondaryContainer
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_higher
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.surface_container_low
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.CommentsViewModel
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.MangaPageViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MangaPage(navController: NavController, viewModelBottom: BottomNavBarViewModel, id: Long) {
    val navControllerMangaPage = rememberNavController()
    var bookmarksBottomSheetVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel = MangaPageViewModel(context)
    val viewModelComment = CommentsViewModel(context)
    viewModel.id.longValue = id

    val commentsState = remember { mutableStateOf<List<Comment>?>(null) }

    val commentsObserver = Observer<List<Comment>> { newComments ->
        commentsState.value = newComments
    }

    LaunchedEffect(viewModel) {
        viewModel.getTitleComments()
    }

    DisposableEffect(viewModel) {
        viewModel.commentsLiveData.observeForever(commentsObserver)

        onDispose {
            viewModel.commentsLiveData.removeObserver(commentsObserver)
        }
    }

    val titleState = remember { mutableStateOf<Title?>(null) }

    val titleObserver = Observer<Title> { newTitle ->
        titleState.value = newTitle
    }

    LaunchedEffect(viewModel) {
        viewModel.getTitle()
    }

    DisposableEffect(viewModel) {
        viewModel.titleLiveData.observeForever(titleObserver)

        onDispose {
            viewModel.titleLiveData.removeObserver(titleObserver)
        }
    }

    NavHost(navController = navControllerMangaPage, startDestination = "main") {
        composable("main") {
            Scaffold(
                topBar = {
                    TopMangaBar(navController = navController, viewModelBottom, viewModel, changeBookmarksSheetVisible = { bookmarksBottomSheetVisible = true })
                }
            ) {
                val scrollState = rememberScrollState()
                var description =
                    titleState.value?.description.toString()

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
                        Spacer(
                            modifier = Modifier
                                .height(100.dp)
                        )
                        Row(
                            modifier = Modifier
                                .padding(top = 120.dp)
                        ) {
                            titleState.value?.let { it1 -> MangaInfo(it1) }
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(11.dp)
                    )

                    MangaActionsButtons(navControllerMangaPage)

                    Spacer(
                        modifier = Modifier.height(22.dp)
                    )

                    titleState.value?.genres?.let { it1 ->
                        Genres(
                            it1
                        )
                    }
                    Spacer(modifier = Modifier.height(22.dp))

                    ExpandableText(text = description)

                    Spacer(modifier = Modifier.height(50.dp))

                    titleState.value?.let { it1 -> TitleRating(viewModelComment, it1) }

//                Spacer(modifier = Modifier.height(11.dp))
//
//                SimilarWorks(navController)

                    Spacer(modifier = Modifier.height(50.dp))

                    commentsState.value?.let { it1 ->
                        CommentsMangaPage(navController = navControllerMangaPage,
                            it1
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    CommentTextField(navControllerMangaPage)
                }
            }
        }
        composable("commentsScreen") {
            titleState.value?.id?.let { it1 ->
                AddComment(navControllerMangaPage, it1.toLong())
            }
        }
        composable("chaptersScreen")
        {
            ChaptersScreen(navControllerMangaPage)
        }
        composable("ReadScreen")
        {
            ReadScreen(navControllerMangaPage, viewModelBottom)
        }
    }
    AnimatedVisibility(
        visible = bookmarksBottomSheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {BookmarksBottomSheet(viewModel, onClose = {
            bookmarksBottomSheetVisible = false
        })
        }
    )
}

@Composable
fun TitleRating(viewModel: CommentsViewModel, title: Title) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Рейтинг", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(14.dp))
        Row() {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                Text(text = "4.9", fontSize = 38.sp)
                Text(text = "156" + " голосов", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "5")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(modifier = Modifier
                        .height(6.dp)
                        .clip(RoundedCornerShape(100)))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "4")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(modifier = Modifier
                        .height(6.dp)
                        .clip(RoundedCornerShape(100)))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "3")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(modifier = Modifier
                        .height(6.dp)
                        .clip(RoundedCornerShape(100)))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "2")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(modifier = Modifier
                        .height(6.dp)
                        .clip(RoundedCornerShape(100)))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "1")
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(modifier = Modifier
                        .height(6.dp)
                        .clip(RoundedCornerShape(100)))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://remanga.org/media/users/613697/avatar.jpg",
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100.dp))
            )

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Outlined.StarOutline,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {

                            }
                    )
                    Icon(
                        Icons.Outlined.StarOutline,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {

                            }
                    )
                    Icon(
                        Icons.Outlined.StarOutline,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {

                            }
                    )
                    Icon(
                        Icons.Outlined.StarOutline,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {

                            }
                    )
                    Icon(
                        Icons.Outlined.StarOutline,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {

                            }
                    )
                }
            }
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
fun TopMangaBar(navController: NavController, viewModelBottom: BottomNavBarViewModel, viewModel: MangaPageViewModel, changeBookmarksSheetVisible: () -> Unit) {
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
            onClick = { changeBookmarksSheetVisible() },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .height(40.dp)
                .width(60.dp)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(100)),
            containerColor = Color.White,
            contentColor = md_theme_dark_background
        ) {
            Icon(
                Icons.Default.BookmarkAdd, "",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }
    }
}

@Composable
fun MangaInfo(title: Title) {
    var titleName = title.name.toString()
    var titleType = title.type!!.toFormattedString()
    var year = title.year.toString()
    var status = title.status!!.toFormattedString()
    var views = title.views.toString()
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
            onClick = {
                navController.navigate("chaptersScreen")
            },
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
            onClick = {
                Navbar.setNavbarVisible(false)
                navController.navigate("ReadScreen")
            },
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
fun Genres(genresList: List<Genre>) {
    var currentRow by remember { mutableStateOf(0) }
    var expended by remember { mutableStateOf(false) }
    val isGenreExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
    ) {
        ChipVerticalGrid(
            spacing = 7.dp,
            modifier = Modifier,
            onRowChange = { newCurrentRow ->
                currentRow = newCurrentRow
                if (currentRow == 2) expended = true
            }
        ) {
            genresList.forEach { word ->
                Text(
                    word.name,
                    color = Color.White,
                    modifier = Modifier
                        .clickable { },
                    textDecoration = TextDecoration.Underline,
                    fontSize = 14.sp
                )
            }
        }
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
                onRowChange(currentRow)

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
//            MangaPreviewCard(navController)
//            MangaPreviewCard(navController)
//            MangaPreviewCard(navController)
        }
    }
}

@Composable
fun CommentsMangaPage(navController: NavController, value: List<Comment>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Комментарии",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                color = md_theme_light_surfaceVariant
            )
            Text(
                text = "Показать все",
                modifier = Modifier
                    .clickable {
                        Navbar.setNavbarVisible(false)
                        navController.navigate("commentsScreen")
                    }
                    .align(Alignment.CenterVertically),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .heightIn(0.dp, 900.dp),
            userScrollEnabled = false,
            content = {
                items(count = value.size) { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 12.dp, end = 11.dp, start = 11.dp)
                            .fillMaxWidth()
                    ) {
                        CommentCard(value[index])
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksBottomSheet(viewModel: MangaPageViewModel, onClose: () -> Unit) {

    val scaffoldState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = scaffoldState,
        modifier = Modifier.height(400.dp),
        containerColor = surface_container_low
    ) {
        ButtonsForBookmarks(viewModel, onClose = { onClose() })
    }
}

@Composable
fun ButtonsForBookmarks(viewModel: MangaPageViewModel, onClose: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Column {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                coroutineScope.launch {
                    viewModel.setReadingStatus(ReadingStatus.IN_PROGRESS)
                }

                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = BookmarksReading)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                coroutineScope.launch {
                    viewModel.setReadingStatus(ReadingStatus.PLANNED)
                }

                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = BookmarksWillRead)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                coroutineScope.launch {
                    viewModel.setReadingStatus(ReadingStatus.COMPLETED)
                }

                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = BookmarksAlreadyRead)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                coroutineScope.launch {
                    viewModel.setReadingStatus(ReadingStatus.POSTPONED)
                }

                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = BookmarksAbandoned)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                coroutineScope.launch {
                    viewModel.setReadingStatus(ReadingStatus.FAVOURITE)
                }

                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = BookmarksFavorite)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentTextField(navController: NavController) {
//    var comment by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(md_theme_dark_surface_container_higher)
            .clickable {
                Navbar.setNavbarVisible(false)
                navController.navigate("commentsScreen")
            }
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
                readOnly = true,
                value = "",
                enabled = false,
                onValueChange = {  },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                ),
                placeholder = { Text(text = "Ваш комментарий", color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            IconButton(
                onClick = {
                    Navbar.setNavbarVisible(false)
                    navController.navigate("commentsScreen")
                }
            ) {
                Icon(
                    Icons.Default.Send, ""
                )
            }
        }
//        Row {
//            Text(
//                text = "${viewModel.commentText.value.length} / $maxLength",
//                textAlign = TextAlign.Start,
//                color = md_theme_light_surfaceVariant,
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//        }
    }
}