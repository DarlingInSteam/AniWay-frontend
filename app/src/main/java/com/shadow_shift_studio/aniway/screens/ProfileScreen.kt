package com.shadow_shift_studio.aniway.screens

import CommentCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.R
import com.shadow_shift_studio.aniway.cards.AchievementCard
import com.shadow_shift_studio.aniway.cards.MangaPreviewCard
import com.shadow_shift_studio.aniway.screens.secondary_screens.MangaPage
import com.shadow_shift_studio.aniway.screens.secondary_screens.settings.Settings
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Wallpaper(navController)
                    Spacer(modifier = Modifier.height(15.dp))
                    NickAndBadge()
                    Spacer(modifier = Modifier.height(15.dp))
                    LvLFragment()
                    Spacer(modifier = Modifier.height(35.dp))
                    InformationAboutUser()
                    Spacer(modifier = Modifier.height(20.dp))
                    UserTab(navController)
                }
            }
            composable("fullScreen") {
                MangaPage(navController)
            }
            composable("settings") {
                Settings(navController)
            }
            }
        }
    }


@Composable
fun Balance() {

}

@Composable
fun Comments() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(450.dp),
        content = {
            items(count = 25) { index ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 12.dp, end = 23.dp, start = 23.dp)
                        .fillMaxWidth()
                ) {
                    CommentCard()
                }
            }
        }
    )
}

@Composable
fun Achievements() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(450.dp),
        content = {
            items(count = 25) { index ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 12.dp, end = 23.dp, start = 23.dp)
                        .fillMaxWidth()
                ) {
                    AchievementCard()
                }
            }
        }
    )
}

@Composable
fun Favorites(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        LazyVerticalGrid(
            GridCells.FixedSize(108.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(count = 6) { index ->
                MangaPreviewCard(navController)
            }
        }
    }
}

@Composable
fun UserTab(navController: NavController) {
    val tabTitles = listOf("Любимое", "Ачивки", "Комментарии", "Баланс")
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
                        modifier = Modifier
                            .background(Color.Transparent),
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if(selectedTabIndex == 0) {
            Favorites(navController)
        }
        else if(selectedTabIndex == 1) {
            Achievements()
        }
        else if(selectedTabIndex == 2) {
            Comments()
        }
        else if(selectedTabIndex == 3) {
            Balance()
        }
    }
}

@Composable
fun InformationAboutUser() {
    val fontSizeForNumbers by remember { mutableStateOf(18.sp) }
    val fontSizeForText by remember { mutableStateOf(12.sp) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(text = "16.7K", color = Color.White, fontSize = fontSizeForNumbers, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Прочитано глав", color = md_theme_dark_onSurfaceVariant, fontSize = fontSizeForText)
        }
        Column() {
            Text(text = "1.2K", color = Color.White, fontSize = fontSizeForNumbers, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Лайков к главам", color = md_theme_dark_onSurfaceVariant, fontSize = fontSizeForText)
        }
        Column() {
            Text(text = "31", color = Color.White, fontSize = fontSizeForNumbers, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Комментариев", color = md_theme_dark_onSurfaceVariant, fontSize = fontSizeForText)
        }
    }
}

@Composable
fun LvLFragment() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 23.dp, end = 23.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = "LVL 0", color = Color.White, fontSize = 10.sp)
            Text(text = "350/500", color = Color.White, fontSize = 10.sp)
        }
        Row() {
            LinearProgressIndicator(progress = 0.7f, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun NickAndBadge() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "TheNorth", color = Color.White, fontSize = 26.sp)
    }
    Spacer(modifier = Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Читатель F ранга",
            color = md_theme_dark_onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Wallpaper(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(Color.White)
        ) {
            Box() {
                Image(
                    painter = painterResource(R.drawable.wallpaper),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }

        }

        Image(
            painter = painterResource(R.drawable.ava),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .align(Alignment.BottomCenter)
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 14.dp, end = 4.dp),
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(Icons.Outlined.Notifications, "asd", tint = Color.White)
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 14.dp, start = 4.dp),
        ) {
            IconButton(
                onClick = { navController.navigate("settings") },
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(Icons.Outlined.Settings, "asd", tint = Color.White)
            }
        }
    }
}
