package com.shadow_shift_studio.aniway.view.secondary_screens.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.data.singleton_object.Navbar
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_secondaryContainer
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_higher
import kotlinx.coroutines.launch

@Composable
fun ProfileSettings(navController: NavController) {
    val navControllerSettings = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileSettingsButtons(navController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileSettingsButtons(navController: NavController) {
    var nickName by remember { mutableStateOf("Привет") }
    var yaoi by remember { mutableStateOf(false) }
    var yuri by remember { mutableStateOf(false) }
    var hentai by remember { mutableStateOf(false) }
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }
        Text(text = "Настройки профиля", fontSize = 18.sp, color = md_theme_dark_onSurfaceVariant)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp, end = 23.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Никнейм", fontSize = 18.sp, color = md_theme_dark_onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TextField(
                    value = nickName,
                    onValueChange = { newText -> nickName = newText },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .onFocusEvent { event ->
                            if (event.isFocused) {
                                coroutineScope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                            Navbar.setNavbarVisible(!event.isFocused)
                        },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {focusManager.clearFocus()}
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Звание", fontSize = 18.sp, color = md_theme_dark_onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                DropdownTextField(
                    listOf(
                        "Читатель F ранга",
                        "Читатель D ранга",
                        "Читатель C ранга",
                        "Читатель A ранга"
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Пол", fontSize = 18.sp, color = md_theme_dark_onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                DropdownTextField(listOf("Мужской", "Женский"))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Изменить аватар",
                modifier = Modifier,
                color = md_theme_dark_onSurfaceVariant
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(md_theme_dark_secondaryContainer)
            )
            {
                Icon(Icons.Default.Edit, "")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Изменить фон профиля",
                modifier = Modifier,
                color = md_theme_dark_onSurfaceVariant
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(md_theme_dark_secondaryContainer)
            )
            {
                Icon(Icons.Default.Edit, "")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Скрыть яой", modifier = Modifier, color = md_theme_dark_onSurfaceVariant)
            Switch(checked = yaoi, onCheckedChange = { yaoi = !yaoi })
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Скрыть хентай",
                modifier = Modifier,
                color = md_theme_dark_onSurfaceVariant
            )
            Switch(checked = hentai, onCheckedChange = { hentai = !hentai })
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Скрыть йури", modifier = Modifier, color = md_theme_dark_onSurfaceVariant)
            Switch(checked = yuri, onCheckedChange = { yuri = !yuri })
        }
    }
}

@Composable
fun DropdownTextField(items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items.firstOrNull()) }
    var iconAfter by remember {
        mutableStateOf(Icons.Default.ArrowRight)
    }
    var iconBefore by remember {
        mutableStateOf(Icons.Default.ArrowDropDown)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .background(md_theme_dark_surface_container_higher),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                modifier = Modifier
                    .height(60.dp)
                    .padding(top = 20.dp, start = 15.dp),
                value = TextFieldValue(selectedItem ?: ""),
                enabled = false,
                onValueChange = { /* Disable editing */ },
                textStyle = TextStyle(
                    color = md_theme_dark_onSurfaceVariant,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
            )
            Icon(
                if (expanded) iconBefore else iconAfter, "", modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                thickness = 1.dp,
                color = md_theme_dark_onSurfaceVariant
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth()
                .background(md_theme_dark_surface_container_higher),
        ) {
            items.forEach { item ->
                TextButton(
                    onClick = {
                        selectedItem = item
                        expanded = false
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    colors = ButtonColors(
                        md_theme_dark_surface_container_higher,
                        md_theme_dark_onSurfaceVariant,
                        Color.White,
                        Color.White
                    )

                ) {
                    Text(text = item)
                }
            }
        }
    }
}