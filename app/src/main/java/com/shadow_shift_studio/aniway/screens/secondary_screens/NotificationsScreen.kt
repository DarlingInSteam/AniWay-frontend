package com.shadow_shift_studio.aniway.screens.secondary_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.cards.NotificationCard
import com.shadow_shift_studio.aniway.screens.secondary_screens.settings.ProfileSettings
import com.shadow_shift_studio.aniway.screens.secondary_screens.settings.SettingsButtons

@Composable
fun Notification(navController: NavController) {
    val navControllerNotify = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp, end = 23.dp)
    ) {

        NavHost(
            navController = navControllerNotify,
            startDestination = "main"
        ) {
            composable("main") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NotifyTop(navController = navController)

                    Spacer(modifier = Modifier.height(20.dp))

                    NotifyList(navControllerNotify)
                }
            }

            composable("fullScreen") {
                MangaPage(navController = navControllerNotify)
            }
        }
    }
}

@Composable
fun NotifyTop(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp))
        }
        Text(text = "Уведомления", fontSize = 18.sp)
    }
}

@Composable
fun NotifyList(navController: NavController) {
    LazyColumn() {
        items(25) { index ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)) {
                NotificationCard(navController)
            }
        }
    }
}