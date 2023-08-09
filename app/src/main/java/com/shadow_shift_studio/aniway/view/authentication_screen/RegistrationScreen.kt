package com.shadow_shift_studio.aniway.view.authentication_screen

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberRichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.CreateLoginHint
import com.shadow_shift_studio.aniway.EnterEmailHint
import com.shadow_shift_studio.aniway.EnterPasswordHint
import com.shadow_shift_studio.aniway.FillAllFields
import com.shadow_shift_studio.aniway.InputErrorMessage
import com.shadow_shift_studio.aniway.PasswordsDontMatch
import com.shadow_shift_studio.aniway.RegisterButtonText
import com.shadow_shift_studio.aniway.RepeatPasswordHint
import com.shadow_shift_studio.aniway.model.enum.LoginStates
import com.shadow_shift_studio.aniway.loginErrors
import com.shadow_shift_studio.aniway.passwordRules
import com.shadow_shift_studio.aniway.view.secondary_screens.settings.DropdownTextField
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_error
import com.shadow_shift_studio.aniway.view_model.authentication.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun Registration(navController: NavController) {
    val context = LocalContext.current
    val viewModelRegistration: RegistrationViewModel = RegistrationViewModel(context)
    var isTextVisible by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.height(30.dp))

            EmailTextField(viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            RegPasswordField(EnterPasswordHint, viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            RepeatPasswordField(RepeatPasswordHint, viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {if(viewModelRegistration.isAllDataEntered()) navController.popBackStack() else isTextVisible = true},
                content = { Text(text = RegisterButtonText, fontSize = 18.sp) }
            )

            if(isTextVisible) {
                Text(
                    text = FillAllFields,
                    color = md_theme_light_error,
                    modifier = Modifier.fillMaxWidth().padding(start = 23.dp, end = 23.dp),
                    textAlign = TextAlign.Justify
                    )
            }
        }
    }
}

@Composable
fun LoginTextField(viewModelRegistration: RegistrationViewModel)
{
    var isLoginError by remember { mutableStateOf(false) }
    var loginErrorMessage by remember { mutableStateOf("") }

    TextField(
        value = viewModelRegistration.login.value,
        onValueChange = {
            viewModelRegistration.login.value = it
            var res = viewModelRegistration.isLoginValid(viewModelRegistration.login.value)
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
            if (isLoginError && !viewModelRegistration.login.value.isEmpty()) {
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

    var isEmailError by remember { mutableStateOf(false) }

    TextField(
        value = viewModelRegistration.email.value,
        onValueChange = {
            viewModelRegistration.email.value = it
            isEmailError = !viewModelRegistration.isEmailValid(viewModelRegistration.email.value)},
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(EnterEmailHint) },
        label = { Text(EnterEmailHint) },
        supportingText = {
            if (isEmailError && viewModelRegistration.email.value.isNotEmpty()) {
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
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    TextField(
        value = viewModelRegistration.password.value,
        onValueChange = {
            viewModelRegistration.password.value = it
            isPasswordError = !viewModelRegistration.isPasswordValid(viewModelRegistration.password.value)},
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
                        tint = if(isPasswordError == true) md_theme_light_error else md_theme_dark_onSurface,
                        contentDescription = ""
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation(),
        supportingText = {
            if (isPasswordError && viewModelRegistration.password.value.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = InputErrorMessage,
                    color = md_theme_light_error
                )
            }
        }
    )
}

@Composable
fun RepeatPasswordField(Hint: String, viewModelRegistration: RegistrationViewModel)
{
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordsEqual by remember { mutableStateOf(false) }

    TextField(
        value = viewModelRegistration.repeatPassword.value,
        onValueChange = { viewModelRegistration.repeatPassword.value = it
            isPasswordsEqual =viewModelRegistration.isPasswordsMatch()},
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(Hint) },
        label = { Text(Hint) },
        trailingIcon = {
            IconButton(onClick = { passwordVisability = !passwordVisability }) {
                Icon(
                    if (passwordVisability) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        supportingText = {
            if (isPasswordsEqual == false && viewModelRegistration.repeatPassword.value.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = PasswordsDontMatch,
                    color = md_theme_light_error
                )
            }
        },
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

