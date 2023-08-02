package com.shadow_shift_studio.aniway.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.EnterLoginHint
import com.shadow_shift_studio.aniway.EnterPasswordHint
import com.shadow_shift_studio.aniway.ForgotPasswordText
import com.shadow_shift_studio.aniway.LoginButtonText

@Composable
fun Authorization(navController: NavController)
{
    val navControllerAu = rememberNavController()
    var login by remember { mutableStateOf("")}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 23.dp, end = 23.dp),
        verticalArrangement = Arrangement.Center)
    {
        TextField(
            value = login,
            onValueChange = { newText -> login = newText },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            placeholder = { Text(EnterLoginHint) },
            label = {Text(EnterLoginHint)}
        )
        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField()

        Spacer(modifier = Modifier.height(11.dp))

        Text(text = ForgotPasswordText,
            modifier = Modifier.clickable { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {/*TODO*/},
            content = {Text( text = LoginButtonText)}
        )
    }
}


@Composable
fun PasswordTextField()
{
    var password by rememberSaveable { mutableStateOf("")}
    var passwordVisability by remember { mutableStateOf(false)}

    TextField(
        value = password,
        onValueChange = { newText -> password = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(EnterPasswordHint) },
        label = {Text(EnterPasswordHint)},
        trailingIcon = {
            IconButton(onClick = { passwordVisability = !passwordVisability }) {
                Icon(if(passwordVisability) Icons.Default.Visibility else Icons.Default.VisibilityOff, "")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if(passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}


