package com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens

import android.annotation.SuppressLint

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.view.modifiers.drawVerticalScrollbar
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReadScreen(navController: NavController, viewModelBottom: BottomNavBarViewModel) {
    val navControllerReadPage = rememberNavController()
    var readBarsVisible by remember { mutableStateOf(true) }
    val images: Array<String> = arrayOf(
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/a703990a-e6ec-4c33-8c66-1ac75e36bf45.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/dd080cde-e046-4603-9a21-7b374617cc12.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/777725b7-ddd7-4108-935d-fcef2447851e.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/d036cb42-ec33-416f-bf54-666265945e71.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/907ec384-919f-4407-bd57-8899ec619a64.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/4e0e46b8-3c46-4d4a-bc85-ab8efd695e8c.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/a0c55523-1697-4379-a56c-e777e00c47ed.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/cc2357e3-6272-4366-9428-88b07b10d9d8.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/2489a269-365a-4c34-ac17-c1ea83b5b3f3.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/f45d8b8f-5bc6-4f0f-b7db-6645fd72f7a8.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/e2edbbd3-e1ee-4858-9df7-f20676179410.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/37b00da4-65ca-4453-bbfc-ee47dec39a25.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/96e53cfd-6f49-4c9c-ab9e-a3d1e5621a32.jpg",
        "https://img-cdn.trendymanga.com/b6b0e1c1-7543-4b8b-8ef4-ec39ae9929e8/993d93ca-01f9-47a3-a055-1909df56c5e7.jpg",
    )

    NavHost(navController = navControllerReadPage, startDestination = "main") {
        composable("main") {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AnimatedVisibility(
                        visible = readBarsVisible,
                        enter = slideInVertically(initialOffsetY = { height -> -height }, animationSpec = tween()),
                        exit = slideOutVertically(targetOffsetY = { height -> -height }, animationSpec = tween()),
                        content = {
                            TopBar(navController, viewModelBottom)
                        }
                    )
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = readBarsVisible,
                        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
                        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
                        content = {
                            BottomBar(images)
                        }
                    )
                },
                content =
                {
                    ImageList(images, {readBarsVisible = !readBarsVisible})
                }
            )
        }
        composable("chaptersScreen")
        {
            ChaptersScreen(navControllerReadPage)
        }
    }
}

@Composable
fun TopBar(navController: NavController, viewModelBottom: BottomNavBarViewModel) {
    var volumeNumber: Int = 1
    var chapterNumber: Int = 77
    var dash: String = "-"
    var volumeChapterText: String = "$volumeNumber$dash$chapterNumber"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(md_theme_dark_background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }
        Text(
            text = volumeChapterText,
            fontSize = 22.sp,

            )
        IconButton(onClick = { navController.navigate("chaptersScreen") }) {
            Icon(
                Icons.Default.Menu, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }
    }
}



@Composable
fun ImageList(imagesResource: Array<String>, onChangeVisible: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .drawVerticalScrollbar(state = listState)
    ) {
        items(imagesResource) { i ->
            AsyncImage(
                model = i,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) { onChangeVisible() },
            )
        }
    }
}

@Composable
fun BottomBar(imagesResource: Array<String>) {
    var likeCount: Int = 22
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(md_theme_dark_background)
            .padding(start = 23.dp, end = 23.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row()
            {
                Icon(
                    Icons.Default.FavoriteBorder, ""
                )
                Text(
                    text = likeCount.toString(),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(11.dp))
            }
        }
        Column {
            Row()
            {
                IconButton(onClick = { /*TODO*/ })
                {
                    Icon(Icons.Default.ArrowBackIos, "")
                }
                IconButton(onClick = { /*TODO*/ })
                {
                    Icon(Icons.Default.ArrowForwardIos, "")
                }
            }
        }
    }
}
































