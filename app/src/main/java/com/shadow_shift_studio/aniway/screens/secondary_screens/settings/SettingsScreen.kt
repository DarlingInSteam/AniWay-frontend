package com.shadow_shift_studio.aniway.screens.secondary_screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.screens.InformationAboutUser
import com.shadow_shift_studio.aniway.screens.LvLFragment
import com.shadow_shift_studio.aniway.screens.NickAndBadge
import com.shadow_shift_studio.aniway.screens.UserTab
import com.shadow_shift_studio.aniway.screens.Wallpaper
import com.shadow_shift_studio.aniway.screens.secondary_screens.MangaPage
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.ui.theme.md_theme_light_surfaceVariant

@Composable
fun Settings(navController: NavController) {
    val navControllerSettings = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        NavHost(
            navController = navControllerSettings,
            startDestination = "main"
        ) {
            composable("main") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SettingsButtons(navControllerSettings, navController)
                }
            }
            composable("profileSettings") {
                ProfileSettings(navControllerSettings)
            }
            composable("safetySettings") {
                SafetySettings(navControllerSettings)
            }
            composable("notifySettings") {
                NotifySettings(navControllerSettings)
            }
            composable("visualSettings") {
                VisualStyleSettings(navControllerSettings)
            }
        }

    }
}

@Composable
fun SettingsButtons(navControllerSettings: NavController, navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, "", modifier = Modifier
                .height(28.dp)
                .width(28.dp))
        }
        Text(text = "Настройки", fontSize = 18.sp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                  navControllerSettings.navigate("profileSettings")
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, md_theme_light_surfaceVariant, Color.White, Color.White)
        ) {
            Text(text = "Настройки профиля")
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                navControllerSettings.navigate("safetySettings")
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, md_theme_light_surfaceVariant, Color.White, Color.White)
        ) {
            Text(text = "Настройки безопасности")
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                navControllerSettings.navigate("notifySettings")
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, md_theme_light_surfaceVariant, Color.White, Color.White)
        ) {
            Text(text = "Настройки уведомлений")
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                navControllerSettings.navigate("visualSettings")
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(md_theme_dark_bottom_sheet_bottoms, md_theme_light_surfaceVariant, Color.White, Color.White)
        ) {
            Text(text = "Настройки внешнего вида")
        }
    }
}