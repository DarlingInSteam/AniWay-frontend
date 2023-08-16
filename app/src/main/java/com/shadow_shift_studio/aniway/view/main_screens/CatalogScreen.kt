package com.shadow_shift_studio.aniway.view.main_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.data.singleton_object.Filter
import com.shadow_shift_studio.aniway.data.singleton_object.Navbar
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import com.shadow_shift_studio.aniway.view.cards.MangaPreviewCard
import com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens.MangaPage
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_high
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.surface_container_low
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel
import com.shadow_shift_studio.aniway.view_model.main_screens.CatalogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    scrollState: LazyGridState,
    viewModelBottom: BottomNavBarViewModel
) {
    var sortingBottomSheetVisible by remember { mutableStateOf(false) }
    var filterBottomSheetVisible by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    var prevFirstVisibleItemIndex by remember { mutableStateOf(0) }
    var currentFirstVisibleItemIndex by remember { mutableStateOf(0) }
    var searchBarVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                LaunchedEffect(Unit) {
                    // Восстанавливаем значение firstVisibleItemIndex из ViewModel после отображения компонента
                    scrollState.scrollToItem(viewModel.firstVisibleItemIndex.value)
                }

                Column(modifier = Modifier.fillMaxSize()) {

                    LaunchedEffect(scrollState.firstVisibleItemIndex) {
                        currentFirstVisibleItemIndex = scrollState.firstVisibleItemIndex

                        if (currentFirstVisibleItemIndex > prevFirstVisibleItemIndex) {
                            searchBarVisible = false
                        } else if (currentFirstVisibleItemIndex < prevFirstVisibleItemIndex) {
                            searchBarVisible = true
                        }

                        prevFirstVisibleItemIndex = currentFirstVisibleItemIndex
                    }

                    AnimatedVisibility(
                        visible = searchBarVisible,
                        enter = expandVertically(
                            spring(
                                stiffness = Spring.StiffnessLow,
                                visibilityThreshold = IntSize.VisibilityThreshold
                            )
                        ),
                        exit = shrinkVertically(),
                    ) {
                        SearchBar()
                    }

                    CatalogButtons(
                        changeButtonSheetSortVisible = { sortingBottomSheetVisible = true },
                        changeButtonSheetFilterVisible = { filterBottomSheetVisible = true }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CardsList(navController = navController, scrollState)
                }
            }
            composable("fullScreen") {
                viewModel.setFirstVisibleItemIndex(scrollState.firstVisibleItemIndex)
                viewModel.setFirstVisibleItemScrollOffset((scrollState.firstVisibleItemScrollOffset))
                MangaPage(navController = navController, viewModelBottom)
            }
        }
    }
    AnimatedVisibility(
        visible = sortingBottomSheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            SortingBottomSheet(onClose = { sortingBottomSheetVisible = false })
        }
    )
    AnimatedVisibility(
        visible = filterBottomSheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            FilterButtonSheet(onClose = { filterBottomSheetVisible = false }, viewModel = viewModel)
        }
    )
}

@Composable
fun CatalogButtons(
    changeButtonSheetSortVisible: () -> Unit,
    changeButtonSheetFilterVisible: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExtendedFloatingActionButton(
            onClick = { /*TODO*/
                changeButtonSheetSortVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(146.dp)
                .height(40.dp),
            containerColor = md_theme_dark_surface_container_high,
            contentColor = md_theme_light_surfaceVariant
        ) {
            Icon(
                Icons.Default.Sort,
                "Sort icon",
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = com.shadow_shift_studio.aniway.CatalogSortingButton)
        }
        ExtendedFloatingActionButton(
            onClick = {
                changeButtonSheetFilterVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(127.dp)
                .height(40.dp),
            containerColor = md_theme_dark_surface_container_high,
            contentColor = md_theme_light_surfaceVariant
        ) {
            Icon(
                Icons.Default.FilterList,
                "Sort icon",
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = com.shadow_shift_studio.aniway.CatalogFilterButtonText)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val horizontalPadding = animateDpAsState(if (expanded) 0.dp else 23.dp).value
    val verticalPadding = animateDpAsState(if (expanded) 0.dp else 11.dp).value
    Navbar.setNavbarVisible(!active)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth()
                .onFocusEvent {  },
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
                active = false
                expanded = false
                Navbar.setNavbarVisible(index = false)
            },
            active = active,
            onActiveChange = {
                active = it
                expanded = it
            },
            placeholder = {
                Text(text = com.shadow_shift_studio.aniway.CatalogSearchBarText)
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
fun CardsList(navController: NavController, scrollState: LazyGridState) {
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
            items(count = 25, key = null) { index ->
                MangaPreviewCard(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingBottomSheet(onClose: () -> Unit) {
    val scaffoldState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = scaffoldState,
        modifier = Modifier.height(400.dp),
        containerColor = surface_container_low
    ) {
        ButtonsForSorting(onClose = { onClose() })
    }
}

@Composable
fun ButtonsForSorting(onClose: () -> Unit) {
    Column {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
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
            Text(text = "По популярности")
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
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
            Text(text = "По рейтингу")
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
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
            Text(text = "По последним обновлениям")
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
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
            Text(text = "По новизне")
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {/*TODO*/
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
            Text(text = "По количеству лайков")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FilterButtonSheet(onClose: () -> Unit, viewModel: CatalogViewModel) {
    val genresState = remember { mutableStateOf<List<Genre>?>(null) }
    val categoriesState = remember { mutableStateOf<List<Category>?>(null) }

    val genresObserver = Observer<List<Genre>> { newGenres ->
        genresState.value = newGenres
    }

    val categoriesObserver = Observer<List<Category>> { newCategories ->
        categoriesState.value = newCategories
    }

    LaunchedEffect(viewModel) {
        viewModel.getGenres()
    }

    LaunchedEffect(viewModel) {
        viewModel.getCategories()
    }

    DisposableEffect(viewModel) {
        viewModel.catalogGenresLiveData.observeForever(genresObserver)
        viewModel.catalogCategoriesLiveData.observeForever(categoriesObserver)

        onDispose {
            viewModel.catalogGenresLiveData.removeObserver(genresObserver)
            viewModel.catalogCategoriesLiveData.removeObserver(categoriesObserver)
        }
    }

    val scaffoldState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = scaffoldState,
        modifier = Modifier.height(400.dp),
        containerColor = surface_container_low
    ) {
        genresState.value?.let { categoriesState.value?.let { it1 -> FilterButtons(it, it1) } }
    }
}

@Composable
fun FilterButtons(genres: List<Genre>, categories: List<Category>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ButtonsType()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsGenres(genres)
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsCategory(categories)
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsTitleStatus()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsAge()
    }
}

@Composable
fun GenreButton(genre: Genre, onCheckedChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = genre.name)
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = !isChecked
            onCheckedChange(isChecked)
        })
    }
}

@Composable
fun CategoryButton(category: Category, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category.text)
        Checkbox(checked = false, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun ButtonsGenres(genres: List<Genre>) {
    val isGenresExpanded = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
            .verticalScroll(scrollState, isGenresExpanded.value)
    ) {
        Button(
            onClick = { isGenresExpanded.value = !isGenresExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Жанры")
                if (!isGenresExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isGenresExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(start = 23.dp, end = 23.dp)
                ) {
                    for (genre in genres) {
                        GenreButton(genre = genre) { isChecked ->
                            if (isChecked) {
                                Filter.selectedGenres.add(genre)
                            } else {
                                Filter.selectedGenres.remove(genre)
                            }
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun ButtonsType() {
    val isTypeExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isTypeExpanded.value = !isTypeExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Типы")
                if (!isTypeExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isTypeExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Манга")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Манхва")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Маньхуа")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ButtonsCategory(categories: List<Category>) {
    val selectedCategories = remember { mutableStateListOf<Category>() }
    val isCategoryExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isCategoryExpanded.value = !isCategoryExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Категории")
                if (!isCategoryExpanded.value) Icon(
                    Icons.Default.ArrowRight,
                    contentDescription = ""
                )
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isCategoryExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    for (category in categories) {
                        CategoryButton(category = category) { isChecked ->
                            if (isChecked) {
                                selectedCategories.add(category)
                            } else {
                                selectedCategories.remove(category)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ButtonsTitleStatus() {
    val isTitleStatusExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isTitleStatusExpanded.value = !isTitleStatusExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Статус проекта")
                if (!isTitleStatusExpanded.value) Icon(
                    Icons.Default.ArrowRight,
                    contentDescription = ""
                )
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isTitleStatusExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Закончен")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Продолжается")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Заморожен")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Анонс")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ButtonsAge() {
    val isAgeExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isAgeExpanded.value = !isAgeExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Возрастной рейтинг")
                if (!isAgeExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isAgeExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ),
            exit = shrinkVertically(),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Для всех")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "16+")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            md_theme_dark_bottom_sheet_bottoms,
                            md_theme_light_surfaceVariant,
                            Color.White,
                            Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "18+")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                }
            }
        )
    }
}
