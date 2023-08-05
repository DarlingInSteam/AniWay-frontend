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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberRichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.CreateLoginHint
import com.shadow_shift_studio.aniway.EnterEmailHint
import com.shadow_shift_studio.aniway.EnterLoginHint
import com.shadow_shift_studio.aniway.EnterPasswordHint
import com.shadow_shift_studio.aniway.InputErrorMessage
import com.shadow_shift_studio.aniway.LoginButtonText
import com.shadow_shift_studio.aniway.RegisterButtonText
import com.shadow_shift_studio.aniway.RepeatPasswordHint
import com.shadow_shift_studio.aniway.loginErrors
import com.shadow_shift_studio.aniway.passwordRules
import com.shadow_shift_studio.aniway.screens.secondary_screens.settings.DropdownTextField
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_errorContainer
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onError
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.ui.theme.md_theme_light_error
import com.shadow_shift_studio.aniway.view_model.BottomNavBarViewModel
import com.shadow_shift_studio.aniway.view_model.LoginStates
import com.shadow_shift_studio.aniway.view_model.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun Registration(navController: NavController) {

    val viewModelRegistration: RegistrationViewModel by lazy { RegistrationViewModel() }


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
            LoginTextField(viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            DropdownTextField(listOf("Мужской", "Женский"))

            Spacer(modifier = Modifier.height(20.dp))

            EmailTextField(viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            RegPasswordField(EnterPasswordHint, viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(RepeatPasswordHint)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { },
                content = { Text(text = RegisterButtonText, fontSize = 18.sp) }
            )
        }
    }
}

@Composable
fun LoginTextField(viewModelRegistration: RegistrationViewModel)
{
    var login by remember { mutableStateOf("") }
    var isLoginError by remember { mutableStateOf(false) }
    var loginErrorMessage by remember { mutableStateOf("") }

    TextField(
        value = login,
        onValueChange = {
            login = it
            var res = viewModelRegistration.IsLoginValid(login)
            if (res == LoginStates.VALID)
                isLoginError = false
            else if (res == LoginStates.INVALID_CHARACTERS) {
                isLoginError = true
                loginErrorMessage = loginErrors[LoginStates.INVALID_CHARACTERS.value]
            } else if (res == LoginStates.INVALID_LENGTH) {
                isLoginError = true
                loginErrorMessage = loginErrors[LoginStates.INVALID_LENGTH.value]
            }
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(CreateLoginHint) },
        label = { Text(CreateLoginHint) },
        supportingText = {
            if (isLoginError && !login.isEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = loginErrorMessage,
                    color = md_theme_light_error
                )
            }
        }
    )
}

@Composable
fun EmailTextField(viewModelRegistration: RegistrationViewModel)
{
    var email by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }

    TextField(
        value = email,
        onValueChange = {
            email = it
            isEmailError = !viewModelRegistration.IsEmailValid(email)},
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(EnterEmailHint) },
        label = { Text(EnterEmailHint) },
        supportingText = {
            if (isEmailError && email.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = InputErrorMessage,
                    color = md_theme_light_error
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegPasswordField(Hint: String, viewModelRegistration: RegistrationViewModel)
{
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = {
            password = it
            isPasswordError = !viewModelRegistration.IsPasswordValid(password)},
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(Hint) },
        label = { Text(Hint) },
        trailingIcon =
        {
            val tooltipState = rememberRichTooltipState(isPersistent = true)
            val scope = rememberCoroutineScope()
            RichTooltipBox(
                action = { Text(passwordRules)},
                text = {},
                tooltipState = tooltipState,
            ) {
                IconButton(
                    onClick = {  scope.launch { tooltipState.show() } },
                    modifier = Modifier.tooltipTrigger()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation(),
        supportingText = {
            if (isPasswordError && password.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = InputErrorMessage,
                    color = md_theme_light_error
                )
            }
        }
    )
}