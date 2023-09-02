package com.shadow_shift_studio.aniway.view.secondary_screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.Cancel
import com.shadow_shift_studio.aniway.Save
import com.shadow_shift_studio.aniway.data.singleton_object.NeedNormalName
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_primary
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_higher
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.CommentsViewModel
import kotlinx.coroutines.launch

@Composable
fun SafetySettings(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SafetySettingsButtons(navController = navController)
    }
    if(NeedNormalName.IsChangePasswordVisible.value)
        ChangePassword()
}

@Composable
fun SafetySettingsButtons(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }
        Text(text = "Настройки безопасности", fontSize = 18.sp)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp, end = 23.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Изменить пароль", modifier = Modifier)
            IconButton(onClick = { NeedNormalName.IsChangePasswordVisible.value = true }) {
                Icon(Icons.Default.LockReset, "")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Привязанные аккаунты", fontSize = 18.sp)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Вконтаке", modifier = Modifier)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, "")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Google", modifier = Modifier)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, "")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Yandex", modifier = Modifier)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Add, "")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChangePassword(){
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()
    var newPassword by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {
        NeedNormalName.IsChangePasswordVisible.value = false
    }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(md_theme_dark_surface_container_higher)
                .fillMaxWidth()
        )
        {
            Text(
                com.shadow_shift_studio.aniway.ChangePassword,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 23.dp, top = 10.dp),
                color = md_theme_dark_onSurfaceVariant
            )
            Row() {
                TextField(
                    modifier = Modifier
                        .padding(start = 23.dp, end = 23.dp)
                        .onFocusEvent { event ->
                            if (event.isFocused) {
                                coroutineScope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                    maxLines = 3,
                    value = oldPassword,
                    enabled = true,
                    onValueChange = { oldPassword = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    placeholder = { Text(text = "Старый пароль", color = Color.Gray) }
                )
            }
            Row() {
                TextField(
                    modifier = Modifier
                        .padding(start = 23.dp, end = 23.dp)
                        .onFocusEvent { event ->
                            if (event.isFocused) {
                                coroutineScope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                    maxLines = 3,
                    value = newPassword,
                    enabled = true,
                    onValueChange = { newPassword = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    placeholder = { Text(text = "Новый пароль", color = Color.Gray) }
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center){
                Button(
                    onClick = { NeedNormalName.IsEditCommentVisible.value = false },
                    border = BorderStroke(1.dp, md_theme_dark_primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = md_theme_dark_primary)
                ) {
                    Text(text= Cancel)
                }
                Spacer(modifier = Modifier.width(15.dp))
                Button(onClick = {
                }) {
                    Text(text = Save)
                }
            }
        }
    }
}