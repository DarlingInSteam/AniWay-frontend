package com.shadow_shift_studio.aniway.screens

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurface

@Composable
fun MyScreen(){
    var selectedTabTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(Modifier.fillMaxSize()) {
            MyScreenSearchBar("Поиск в \"$selectedTabTitle\"")
            TabMyScreen(onTabSelected = { tabTitle ->
                selectedTabTitle = tabTitle
            })
            Spacer(modifier = Modifier.height(12.dp))
            MyScreenCards()
        }
    }
}

@Composable
fun MyScreenCards() {
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
//                MangaPreviewCard()
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
    val horizontalPadding = animateDpAsState(if (expanded) 0.dp else 23.dp).value
    val verticalPadding = animateDpAsState(if (expanded) 0.dp else 11.dp).value

    Column(modifier = Modifier
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
fun TabMyScreen(onTabSelected: (String) -> Unit) {
    val tabTitles = listOf("Читаю", "Буду читать", "Прочитано", "Брошено")
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
                onTabSelected(tabTitles[selectedTabIndex])
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