package com.shadow_shift_studio.aniway.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_surface_container_high
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat.NestedScrollType
import com.shadow_shift_studio.aniway.manga_card.MangaPreviewCard


@Composable
fun ScrollableAppBar(
    scrollDelta: Int,
    height: MutableState<Dp>,
    content: @Composable ColumnScope.() -> Unit
) {
    var offset by remember { mutableIntStateOf(0) }
    var heightInPx by remember { mutableIntStateOf(0) }
    val localDestiny = LocalDensity.current

    offset = if (offset - scrollDelta < -heightInPx) { -heightInPx }
            else if (offset - scrollDelta > 0) { 0 }
            else { offset - scrollDelta }

    Column(modifier = Modifier
        .fillMaxWidth()
        .graphicsLayer { translationY = offset.toFloat() }
        .onGloballyPositioned { coordinates ->
            heightInPx = coordinates.size.height
            height.value = with(localDestiny) {
                coordinates.size.height.toDp()
            }
        },
        content = content
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen() {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val horizontalPadding = animateDpAsState(if (expanded) 0.dp else 23.dp).value
    val verticalPadding = animateDpAsState(if (expanded) 0.dp else 11.dp).value

//    var prevScrollValue by remember { mutableStateOf(0) }
//    val scrollState = rememberScrollState();
//    var temp = prevScrollValue;
//    prevScrollValue = scrollState.value
//    var delta = scrollState.value - temp;
    var prevScrollValue by remember { mutableIntStateOf(0) }
    val gridState = rememberLazyGridState()
    var temp = prevScrollValue;

    val itemHeight = 200

    var topBarHeight = remember { mutableStateOf(0.dp) }
    val scroll by remember {
        derivedStateOf {
//            topBarHeight.value.value.toInt()
             gridState.firstVisibleItemIndex * itemHeight
            + gridState.firstVisibleItemScrollOffset
        }
    }
    prevScrollValue = scroll
    var delta = scroll - temp;

    ScrollableAppBar(scrollDelta = delta, height = topBarHeight) {
        Text("Im scrollable app bar")
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridState,
        content = {
//            items(3) {
//                Box(modifier = Modifier.height(topBarHeight.value)) {}
//            }
            items(500) { index ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(itemHeight.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "$index",
                        fontSize = 30.sp,
                    )
                }
            }
        }
    )

//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3),
//                modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
////                verticalArrangement = Arrangement.spacedBy(10.dp),
//                content = {
//                    items(count = 450) { index ->
//                        Card() {
//                            Text("item $index", fontSize=30.sp)
//                        }
////                    MangaPreviewCard()
//                    }
//                }
//            )
//        Column(modifier = Modifier
//            .verticalScroll(scrollState)
////            .nestedScroll(nestedScrollConnection)
//            .padding(it)) {
//            repeat(100) {
//                Text("hello world")
//            }
//        }


//        SearchBar(
//            modifier = Modifier.fillMaxWidth(),
//            query = searchText,
//            onQueryChange = {
//                searchText = it
//            },
//            onSearch = {
//                active = false
//                expanded = false
//            },
//            active = active,
//            onActiveChange = {
//                active = it
//                expanded = it
//            },
//            placeholder = {
//                Text(text = com.shadow_shift_studio.aniway.CatalogSearchBarText)
//            },
//            leadingIcon = {
//                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
//            },
//            trailingIcon = {
//                if (active) {
//                    Icon(
//                        modifier = Modifier.clickable {
//                            searchText = ""
//                            active = false
//                            expanded = false
//                        },
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close icon"
//                    )
//                }
//            },
//        ) {}


//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(horizontal = horizontalPadding, vertical = verticalPadding)
//    ) {
//        SearchBar(
//            modifier = Modifier.fillMaxWidth(),
//            query = searchText,
//            onQueryChange = {
//                searchText = it
//            },
//            onSearch = {
//                active = false
//                expanded = false
//            },
//            active = active,
//            onActiveChange = {
//                active = it
//                expanded = it
//            },
//            placeholder = {
//                Text(text = com.shadow_shift_studio.aniway.CatalogSearchBarText)
//            },
//            leadingIcon = {
//                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
//            },
//            trailingIcon = {
//                if (active) {
//                    Icon(
//                        modifier = Modifier.clickable {
//                            searchText = ""
//                            active = false
//                            expanded = false
//                        },
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close icon"
//                    )
//                }
//            },
//        ) {
//        }
//        Spacer(modifier = Modifier.height(24.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//
//            ExtendedFloatingActionButton(
//                onClick = { /*TODO*/
//
//                },
//                shape = RoundedCornerShape(28.dp),
//                modifier = Modifier
//                    .width(146.dp)
//                    .height(40.dp),
//                containerColor = md_theme_dark_surface_container_high
//            ) {
//                Icon(
//                    Icons.Default.Sort,
//                    "Sort icon",
//                    modifier = Modifier
//                        .width(18.dp)
//                        .height(18.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = com.shadow_shift_studio.aniway.CatalogSortingButton)
//            }
//            ExtendedFloatingActionButton(
//                onClick = { /*TODO*/
//
//                },
//                shape = RoundedCornerShape(28.dp),
//                modifier = Modifier
//                    .width(127.dp)
//                    .height(40.dp),
//                containerColor = md_theme_dark_surface_container_high
//            ) {
//                Icon(
//                    Icons.Default.FilterList,
//                    "Sort icon",
//                    modifier = Modifier
//                        .width(18.dp)
//                        .height(18.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = com.shadow_shift_studio.aniway.CatalogFilterButtonText)
//            }
//        }
//        Spacer(modifier = Modifier.height(24.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            LazyVerticalGrid(
//                GridCells.Fixed(3),
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                items(count = 450) { index ->
//                    MangaPreviewCard()
//                }
//            }
//        }
//    }
}



