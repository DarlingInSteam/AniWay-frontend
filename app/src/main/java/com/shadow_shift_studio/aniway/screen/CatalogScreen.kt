package com.shadow_shift_studio.aniway.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_surface_container_high
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shadow_shift_studio.aniway.manga_card.MangaPreviewCard
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_bottom_sheet_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_bottom_sheet_bottoms

@Preview
@Composable
fun CatalogScreen() {
    var sortingBottomSheetVisible by remember { mutableStateOf(false) }
    var filterBottomSheetVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
            SearchBar()
            Spacer(modifier = Modifier.height(0.dp))
            CatalogButtons(
                changeButtonSheetSortVisible = { sortingBottomSheetVisible = true },
                changeButtonSheetFilterVisible = { filterBottomSheetVisible = true }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CardsList()
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
fun CardsList() {
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
                MangaPreviewCard()
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
    Column() {
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
                        .fillMaxSize(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {

                    }
                }
            }
        }
    ) {}
}


