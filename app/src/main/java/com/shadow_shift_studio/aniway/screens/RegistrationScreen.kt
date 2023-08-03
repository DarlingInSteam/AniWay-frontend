package com.shadow_shift_studio.aniway.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.CreateLoginHint
import com.shadow_shift_studio.aniway.EnterEmailHint
import com.shadow_shift_studio.aniway.EnterLoginHint
import com.shadow_shift_studio.aniway.EnterPasswordHint
import com.shadow_shift_studio.aniway.LoginButtonText
import com.shadow_shift_studio.aniway.RegisterButtonText
import com.shadow_shift_studio.aniway.RepeatPasswordHint

@Composable
fun Registration(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack, "", modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(11.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 23.dp, end = 23.dp),
            verticalArrangement = Arrangement.Top
        )
        {
            TextField(
                value = login,
                onValueChange = { newText -> login = newText },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = { Text(CreateLoginHint) },
                label = { Text(CreateLoginHint) }
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = email,
                onValueChange = { newText -> email = newText },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = { Text(EnterEmailHint) },
                label = { Text(EnterEmailHint) }
            )
            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(EnterPasswordHint)

            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(RepeatPasswordHint)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.popBackStack() },
                content = { Text(text = RegisterButtonText, fontSize = 18.sp) }
            )
        }
    }
}