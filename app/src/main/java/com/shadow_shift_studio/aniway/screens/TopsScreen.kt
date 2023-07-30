package com.shadow_shift_studio.aniway.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.cards.MangaCardTop
import com.shadow_shift_studio.aniway.screens.secondary_screens.MangaPage
import com.shadow_shift_studio.aniway.screens.secondary_screens.NotifyList
import com.shadow_shift_studio.aniway.screens.secondary_screens.NotifyTop
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurface

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopsScreen(){
    val navControllerTop = rememberNavController()
    val scrollState = rememberLazyListState()
    var prevFirstVisibleItemIndex by remember { mutableStateOf(0) }
    var currentFirstVisibleItemIndex by remember { mutableStateOf(0) }
    var tabBarVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(Modifier.fillMaxSize()) {
            NavHost(
                navController = navControllerTop,
                startDestination = "main"
            ) {
                composable("main") {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LaunchedEffect(scrollState.firstVisibleItemIndex) {
                            currentFirstVisibleItemIndex = scrollState.firstVisibleItemIndex

                            if (currentFirstVisibleItemIndex > prevFirstVisibleItemIndex) {
                                tabBarVisible = false
                            } else if (currentFirstVisibleItemIndex < prevFirstVisibleItemIndex) {
                                tabBarVisible = true
                            }
                            prevFirstVisibleItemIndex = currentFirstVisibleItemIndex
                        }

                        AnimatedVisibility(
                            visible = tabBarVisible,
                            enter = expandVertically(
                                spring(
                                    stiffness = Spring.StiffnessLow,
                                    visibilityThreshold = IntSize.VisibilityThreshold
                                )
                            ),
                            exit = shrinkVertically(),
                        ) {
                            TabScreen()
                        }

                        TopCheckBox()
                        TopCards(navControllerTop, scrollState)
                    }
                }

                composable("fullScreen") {
                    MangaPage(navController = navControllerTop)
                }
            }
        }
    }
}

@Composable
fun TopCards(navController: NavController, scrollState: LazyListState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
        content = {
            items(count = 25) { index ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 12.dp, end = 23.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = (index + 1).toString(),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 15.dp, end = 15.dp)
                            .widthIn(min = 20.dp, max = 20.dp),
                        textAlign = TextAlign.Center
                    )
                    MangaCardTop(navController)
                }
            }
        }
    )
}

@Composable
fun TabScreen() {
    val tabTitles = listOf("Новинки", "Топ месяца", "Топ недели", "Топ дня")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .height(60.dp),
            containerColor = md_theme_dark_background,
            divider = ({}),
            contentColor = md_theme_dark_onSurface
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = false,
                    modifier = Modifier
                        .height(60.dp),
                    onClick = {
                        selectedTabIndex = index
                    }
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.background(Color.Transparent),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCheckBox() {
    var selectedAll by remember { mutableStateOf(false) }
    var selectedManga by remember { mutableStateOf(false) }
    var selectedManihua by remember { mutableStateOf(false) }
    var selectedManhwa by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        FilterChip(
            label = ({ Text(text = "Все подряд")}),
            selected = selectedAll,
            onClick = { /*TODO*/
                selectedAll = !selectedAll
            }
        )
        FilterChip(
            label = ({ Text(text = "Манга")}),
            selected = selectedManga,
            onClick = { /*TODO*/
                selectedManga = !selectedManga
            }
        )
        FilterChip(
            label = ({ Text(text = "Маньхуа")}),
            selected = selectedManihua,
            onClick = { /*TODO*/
                selectedManihua = !selectedManihua
            }
        )
        FilterChip(
            label = ({ Text(text = "Манхва")}),
            selected = selectedManhwa,
            onClick = { /*TODO*/
                selectedManhwa = !selectedManhwa
            }
        )
    }
}

