package com.shadow_shift_studio.aniway.view_model

import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


enum class LoginStates(val value: Int)
{
    INVALID_LENGTH(0),
    INVALID_CHARACTERS(1),
    VALID(2),
    EMPTY(3)
}
class RegistrationViewModel : ViewModel() {

    var login: MutableState<String> = mutableStateOf("")
    var email: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")
    var repeatPassword: MutableState<String> = mutableStateOf("")
    fun IsLoginValid(login: String): LoginStates {
        val pattern = Regex("^[a-zA-Z0-9!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]*$")
        var res: LoginStates = LoginStates.VALID
        if (login.isEmpty())
            res = LoginStates.EMPTY
        else
            if (login.length < 3 || login.length > 20)
                res = LoginStates.INVALID_LENGTH
            else
                if (pattern.matches(login) == false)
                    res = LoginStates.INVALID_CHARACTERS
        return res
    }

    fun IsEmailValid(email: String): Boolean {
        return !(TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun IsPasswordsMatch(): Boolean
    {
        return repeatPassword.value.equals(password.value)
    }
    fun IsPasswordValid(password: String): Boolean
    {
        var res = true
        val pattern = Regex("^[a-zA-Z0-9!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]*$")

        if (password.length < 8)
            res = false
        else if (pattern.matches(password) == false)
            res = false
        else if (password.filter { it.isDigit() }.firstOrNull() == null)
            res = false
        else if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null)
            res = false
        else if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null)
            res = false
        else if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null)
            res = false

        return res
    }

    fun IsAllDataEntered():Boolean
    {
        var res = false
        if(IsLoginValid(login.value) == LoginStates.VALID
            && IsPasswordValid(password.value)
            && IsEmailValid(email.value)
            && IsPasswordsMatch())
            res = true
        return res
    }
}

