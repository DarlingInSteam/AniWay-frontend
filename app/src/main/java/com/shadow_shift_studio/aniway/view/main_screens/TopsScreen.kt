@file:OptIn(ExperimentalFoundationApi::class)

package com.shadow_shift_studio.aniway.view.main_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.view.cards.MangaCardTop
import com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens.MangaPage
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel
import com.shadow_shift_studio.aniway.view_model.main_screens.TopsViewModel
import java.lang.Math.abs

suspend fun PointerInputScope.detectSwipe(
    swipeState: MutableIntState = mutableIntStateOf(-1),
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
) = detectDragGestures(
    onDrag = { change, dragAmount ->
        change.consume()
        val (x, y) = dragAmount
        if (abs(x) > abs(y)) {
            when {
                x > 0 -> swipeState.intValue = 0
                x < 0 -> swipeState.intValue = 1
            }
        } else {
            when {
                y > 0 -> swipeState.intValue = 2
                y < 0 -> swipeState.intValue = 3
            }
        }
    },
    onDragEnd = {
        when (swipeState.intValue) {
            0 -> onSwipeRight()
            1 -> onSwipeLeft()
            2 -> onSwipeDown()
            3 -> onSwipeUp()
        }
    }
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopsScreen(
    viewModel: TopsViewModel,
    scrollState: LazyListState,
    viewModelBottom: BottomNavBarViewModel
) {
    val navControllerTop = rememberNavController()
    var prevFirstVisibleItemIndex by remember { mutableStateOf(0) }
    var currentFirstVisibleItemIndex by remember { mutableStateOf(0) }
    var tabBarVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectHorizontalDragGestures { _, dragAmount ->
//                    if (dragAmount.dp > 100.dp) {
//                        // Swipe from left to right

//                    } else if (dragAmount.dp < (-100).dp) {
//                        // Swipe from right to left

//                    }
//                }
//            }
            .pointerInput(Unit) {
                    detectSwipe(
                        onSwipeLeft = {
                            if (viewModel.selectedTabIndex.value < viewModel.tabTitles.size - 1) {
                                viewModel.selectedTabIndex.value += 1
                            }
                        },
                        onSwipeRight = {
                            if (viewModel.selectedTabIndex.value > 0) {
                                viewModel.selectedTabIndex.value -= 1
                            }
                        }
                    )
            },
    ) {
        Column(Modifier.fillMaxSize()) {
            NavHost(
                navController = navControllerTop,
                startDestination = "main"
            ) {
                composable("main") {
                    LaunchedEffect(Unit) {
                        // Восстанавливаем значение firstVisibleItemIndex из ViewModel после отображения компонента
                        scrollState.scrollToItem(viewModel.firstVisibleItemIndex.value)
                    }

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
                            TabScreen(viewModel)
                        }

                        TopCheckBox()
                        TopCards(navControllerTop, scrollState, viewModel)
                    }
                }

                composable("fullScreen") {
                    viewModel.setFirstVisibleItemIndex(scrollState.firstVisibleItemIndex)
                    viewModel.setFirstVisibleItemScrollOffset((scrollState.firstVisibleItemScrollOffset))
                    MangaPage(navController = navControllerTop, viewModelBottom, 0)
                }
            }
        }
    }
}

@Composable
fun TopCards(navController: NavController, scrollState: LazyListState, viewModel: TopsViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            ,
        state = scrollState,
        content = {
            items(count = 25) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(viewModel: TopsViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = viewModel.selectedTabIndex.value,
            modifier = Modifier
                .height(60.dp),
            containerColor = md_theme_dark_background,
            divider = ({}),
            contentColor = md_theme_dark_onSurface
        ) {
            viewModel.tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = viewModel.selectedTabIndex.value == index,
                    modifier = Modifier
                        .height(60.dp),
                    onClick = {
                        viewModel.selectedTabIndex.value = index
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

@Composable
fun TabContent(title: String) {
    // Implement your content for each tab here
    Text(text = title, modifier = Modifier.padding(16.dp))
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilterChip(
            label = ({ Text(text = "Все подряд") }),
            selected = selectedAll,
            onClick = { /*TODO*/
                selectedAll = !selectedAll
            }
        )
        FilterChip(
            label = ({ Text(text = "Манга") }),
            selected = selectedManga,
            onClick = { /*TODO*/
                selectedManga = !selectedManga
            }
        )
        FilterChip(
            label = ({ Text(text = "Маньхуа") }),
            selected = selectedManihua,
            onClick = { /*TODO*/
                selectedManihua = !selectedManihua
            }
        )
        FilterChip(
            label = ({ Text(text = "Манхва") }),
            selected = selectedManhwa,
            onClick = { /*TODO*/
                selectedManhwa = !selectedManhwa
            }
        )
    }
}

