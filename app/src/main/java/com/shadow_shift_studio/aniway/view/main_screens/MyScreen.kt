package com.shadow_shift_studio.aniway.view.main_screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.data.singleton_object.Navbar
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import com.shadow_shift_studio.aniway.view.cards.MangaPreviewCard
import com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens.MangaPage
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel
import com.shadow_shift_studio.aniway.view_model.main_screens.MyViewModel
import kotlinx.coroutines.launch

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun MyScreen(
    viewModel: MyViewModel,
    scrollState: LazyGridState,
    viewModelBottom: BottomNavBarViewModel
) {
    var selectedTabTitle by remember { mutableStateOf("") }
    val navController = rememberNavController()
    var prevFirstVisibleItemIndex by remember { mutableIntStateOf(0) }
    var currentFirstVisibleItemIndex by remember { mutableIntStateOf(0) }
    var mySearchBarVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectSwipe(
                    onSwipeLeft = {
                        if (viewModel.selectedTabIndex.value < viewModel.tabTitles.size - 1) {
                            viewModel.selectedTabIndex.value += 1
                            selectedTabTitle = viewModel.tabTitles[viewModel.selectedTabIndex.value]
                        }
                    },
                    onSwipeRight = {
                        if (viewModel.selectedTabIndex.value > 0) {
                            viewModel.selectedTabIndex.value -= 1
                            selectedTabTitle = viewModel.tabTitles[viewModel.selectedTabIndex.value]
                        }
                    }
                )
            }
    ) {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                LaunchedEffect(Unit) {
                    // Восстанавливаем значение firstVisibleItemIndex из ViewModel после отображения компонента
                    scrollState.scrollToItem(viewModel.firstVisibleItemIndex.intValue)
                }

                Column(Modifier.fillMaxSize()) {
                    LaunchedEffect(scrollState.firstVisibleItemIndex) {
                        currentFirstVisibleItemIndex = scrollState.firstVisibleItemIndex

                        if (currentFirstVisibleItemIndex > prevFirstVisibleItemIndex) {
                            mySearchBarVisible = false
                        } else if (currentFirstVisibleItemIndex < prevFirstVisibleItemIndex) {
                            mySearchBarVisible = true
                        }
                        prevFirstVisibleItemIndex = currentFirstVisibleItemIndex
                    }

                    AnimatedVisibility(
                        visible = mySearchBarVisible,
                        enter = expandVertically(
                            spring(
                                stiffness = Spring.StiffnessLow,
                                visibilityThreshold = IntSize.VisibilityThreshold
                            )
                        ),
                        exit = shrinkVertically(),
                    ) {
                        MyScreenSearchBar("Поиск в \"$selectedTabTitle\"")
                    }

                    TabMyScreen(onTabSelected = { tabTitle ->
                        selectedTabTitle = tabTitle
                        coroutineScope.launch {
                            if (selectedTabTitle == "Читаю") {
                                viewModel.getTitles(ReadingStatus.IN_PROGRESS)
                            }
                            if (selectedTabTitle == "Буду читать") {
                                viewModel.getTitles(ReadingStatus.PLANNED)
                            }
                            if (selectedTabTitle == "Прочитано") {
                                viewModel.getTitles(ReadingStatus.COMPLETED)
                            }
                            if (selectedTabTitle == "Брошено") {
                                viewModel.getTitles(ReadingStatus.POSTPONED)
                            }
                        }
                    }, viewModel)

                    Spacer(modifier = Modifier.height(12.dp))
                    MyScreenCards(navController, scrollState, viewModel)
                }
            }
            composable("fullScreen") {
                viewModel.setFirstVisibleItemIndex(scrollState.firstVisibleItemIndex)
                viewModel.setFirstVisibleItemScrollOffset((scrollState.firstVisibleItemScrollOffset))
                MangaPage(navController, viewModelBottom, viewModel.id)
            }
        }
    }
}

@Composable
fun MyScreenCards(navController: NavController, scrollState: LazyGridState, viewModel: MyViewModel) {
    val titlesState = remember { mutableStateOf<List<TitlePreview>?>(null) }

    val titlesObserver = Observer<List<TitlePreview>> { newTitles ->
        titlesState.value = newTitles
    }

    DisposableEffect(viewModel) {
        viewModel.titlesLiveData.observeForever(titlesObserver)

        onDispose {
            viewModel.titlesLiveData.removeObserver(titlesObserver)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            GridCells.FixedSize(108.dp),
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 23.dp, end = 23.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            titlesState.value?.let {
                items(count = it.size) { index ->
                    MangaPreviewCard(navController, titlesState.value!![index]) { id: Long ->
                        viewModel.id = id
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreenSearchBar(selectedTabTitle: String) {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val horizontalPadding = animateDpAsState(if (expanded) 0.dp else 23.dp, label = "").value
    val verticalPadding = animateDpAsState(if (expanded) 0.dp else 11.dp, label = "").value
    Navbar.setNavbarVisible(!active)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)

    ) {
        androidx.compose.material3.SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
                active = false
                expanded = false
            },
            active = active,
            onActiveChange = {
                active = it
                expanded = it
            },
            placeholder = {
                Text(text = selectedTabTitle)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            searchText = ""
                            active = false
                            expanded = false
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            },
        ) {
        }
    }
}

@Composable
fun TabMyScreen(onTabSelected: (String) -> Unit, viewModel: MyViewModel) {
    Column(Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = viewModel.selectedTabIndex.intValue,
            modifier = Modifier
                .height(60.dp),
            containerColor = md_theme_dark_background,
            divider = ({}),
            contentColor = md_theme_dark_onSurface
        ) {
            viewModel.tabTitles.forEachIndexed { index, title ->
                onTabSelected(viewModel.tabTitles[viewModel.selectedTabIndex.intValue])
                Tab(
                    selected = false,
                    modifier = Modifier
                        .height(60.dp),
                    onClick = {
                        viewModel.selectedTabIndex.intValue = index
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