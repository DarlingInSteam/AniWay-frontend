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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
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

@Composable
fun NotifySettings(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NotifySettingsButtons(navController = navController)
    }
}

@Composable
fun NotifySettingsButtons(navController: NavController) {
    var nickName by remember { mutableStateOf("Привет") }
    var reading by remember { mutableStateOf(false) }
    var planed by remember { mutableStateOf(false) }
    var like by remember { mutableStateOf(false) }
    var cRead by remember { mutableStateOf(false) }
    var trash by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp))
        }
        Text(text = "Настройки уведомлений", fontSize = 18.sp)
    }
    
    Spacer(modifier = Modifier.height(15.dp))
    
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 23.dp, end = 23.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "Уведомления из закладок", fontSize = 18.sp)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Читаю", modifier = Modifier)
            Switch(checked = reading, onCheckedChange = {reading = !reading})
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "В планах", modifier = Modifier)
            Switch(checked = planed, onCheckedChange = {planed = !planed})
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Любимое", modifier = Modifier)
            Switch(checked = like, onCheckedChange = {like = !like})
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Прочитано", modifier = Modifier)
            Switch(checked = cRead, onCheckedChange = {cRead = !cRead})
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Брошено", modifier = Modifier)
            Switch(checked = trash, onCheckedChange = {trash = !trash})
        }
    }
}