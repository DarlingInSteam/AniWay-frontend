package com.shadow_shift_studio.aniway.screens.secondary_screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SafetySettings(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SafetySettingsButtons(navController = navController)
    }
}

@Composable
fun SafetySettingsButtons(navController: NavController) {
    var nickName by remember { mutableStateOf("Привет") }
    var yaoi by remember { mutableStateOf(false) }
    var yuri by remember { mutableStateOf(false) }
    var hentai by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp))
        }
        Text(text = "Настройки безопасности", fontSize = 18.sp)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 23.dp, end = 23.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Изменить пароль", modifier = Modifier)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.LockReset, "")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text(text = "Привязанные аккаунты", fontSize = 18.sp)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Вконтаке", modifier = Modifier)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, "")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Google", modifier = Modifier)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, "")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Yandex", modifier = Modifier)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, "")
                }
            }
        }
    }
}