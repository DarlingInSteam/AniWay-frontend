package com.shadow_shift_studio.aniway.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.cards.MangaPreviewCard
import com.shadow_shift_studio.aniway.screens.secondary_screens.MangaPage
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_bottom_sheet_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_surface_container_high

@Preview
@Composable
fun CatalogScreen() {
    var sortingBottomSheetVisible by remember { mutableStateOf(false) }
    var filterBottomSheetVisible by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                Column(modifier = Modifier.fillMaxSize()) {
                    SearchBar()
                    Spacer(modifier = Modifier.height(0.dp))
                    CatalogButtons(
                        changeButtonSheetSortVisible = { sortingBottomSheetVisible = true },
                        changeButtonSheetFilterVisible = { filterBottomSheetVisible = true }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CardsList(navController = navController)
                }
            }
            composable("fullScreen") {
                MangaPage(navController = navController)
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
            FilterButtonSheet(onClose = { filterBottomSheetVisible = false })
        }
    )
}

@Composable
fun CatalogButtons(changeButtonSheetSortVisible: () -> Unit, changeButtonSheetFilterVisible: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(23.dp, 11.dp),
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
            containerColor = md_theme_dark_surface_container_high
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
            containerColor = md_theme_dark_surface_container_high
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

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        SearchBar(
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
fun CardsList(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            GridCells.FixedSize(108.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 23.dp, end = 23.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(count = 25) { index ->
                MangaPreviewCard(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingBottomSheet(onClose: () -> Unit) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val touchSlop = with(LocalDensity.current) { 8.dp.toPx() }
    val offsetY = remember { mutableStateOf(0f) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxWidth(),
        sheetPeekHeight = 400.dp,
        sheetContainerColor = md_theme_dark_bottom_sheet_background,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            val delta = offsetY.value + dragAmount.y
                            offsetY.value = delta
                            if (change != null && dragAmount.y.dp > 50.dp) {
                                if (delta > touchSlop) {
                                    onClose()
                                } else {
                                    offsetY.value = 0f
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.TopStart
            ) {
                ButtonsForSorting(onClose = { onClose() })
            }
        }
    ) {}
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
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
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
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
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
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
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
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
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
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
        ) {
            Text(text = "По количеству лайков")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FilterButtonSheet(onClose: () -> Unit) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val touchSlop = with(LocalDensity.current) { 8.dp.toPx() }
    val offsetY = remember { mutableStateOf(0f) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxWidth(),
        sheetPeekHeight = 400.dp,
        sheetContainerColor = md_theme_dark_bottom_sheet_background,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxSize()
                    .height(400.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            val delta = offsetY.value + dragAmount.y
                            offsetY.value = delta
                            if (change != null && dragAmount.y.dp > 50.dp) {
                                if (delta > touchSlop) {
                                    onClose()
                                } else {
                                    offsetY.value = 0f
                                }
                            }
                        }
                    },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    FilterButtons()
                }
            }
        }
    ) {}
}

@Composable
fun FilterButtons() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ButtonsType()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsGenres()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsCategory()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsTitleStatus()
        Spacer(modifier = Modifier.height(12.dp))
        ButtonsAge()
    }
}

@Composable
fun ButtonsGenres() {
    val isGenresExpanded = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
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
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Жанры")
                if (!isGenresExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isGenresExpanded.value,
            enter = slideInVertically(initialOffsetY = { height -> -height }, animationSpec = tween()),
            exit = slideOutVertically(targetOffsetY = { height -> -height }, animationSpec = tween()),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Романтика")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Комедия")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Драма")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Фэнтези")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Приключения")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Повседневность")
                            Checkbox(checked = false, onCheckedChange = {})
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

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 23.dp, end = 23.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isTypeExpanded.value = !isTypeExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Типы")
                if (!isTypeExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isTypeExpanded.value,
            enter = slideInVertically(initialOffsetY = { height -> -height }, animationSpec = tween()),
            exit = slideOutVertically(targetOffsetY = { height -> -height }, animationSpec = tween()),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Манга")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Манхва")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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
fun ButtonsCategory() {
    val isCategoryExpanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 23.dp, end = 23.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isCategoryExpanded.value = !isCategoryExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Категории")
                if (!isCategoryExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isCategoryExpanded.value,
            enter = slideInVertically(initialOffsetY = { height -> -height }, animationSpec = tween()),
            exit = slideOutVertically(targetOffsetY = { height -> -height }, animationSpec = tween()),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Веб")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "В цвете")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Хентай!")
                            Checkbox(checked = false, onCheckedChange = {})
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

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 23.dp, end = 23.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isTitleStatusExpanded.value = !isTitleStatusExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Статус проекта")
                if (!isTitleStatusExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isTitleStatusExpanded.value,
            enter = slideInVertically(initialOffsetY = { height -> -height }, animationSpec = tween()),
            exit = slideOutVertically(targetOffsetY = { height -> -height }, animationSpec = tween()),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Закончен")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Продолжается")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Заморожен")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 23.dp, end = 23.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(md_theme_dark_bottom_sheet_bottoms)
    ) {
        Button(
            onClick = { isAgeExpanded.value = !isAgeExpanded.value },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Возрастной рейтинг")
                if (!isAgeExpanded.value) Icon(Icons.Default.ArrowRight, contentDescription = "")
                else Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(
            visible = isAgeExpanded.value,
            enter = slideInVertically(initialOffsetY = { height -> -height }, animationSpec = tween()),
            exit = slideOutVertically(targetOffsetY = { height -> -height }, animationSpec = tween()),
            content = {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Для всех")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "16+")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, Color.White, Color.White, Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "18+")
                            Checkbox(checked = false, onCheckedChange = {})
                        }
                    }
                }
            }
        )
    }
}