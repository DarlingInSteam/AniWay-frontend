package com.shadow_shift_studio.aniway.view_model

import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils


enum class LoginStates(val value: Int)
{
    INVALID_LENGTH(0),
    INVALID_CHARACTERS(1),
    VALID(2),
    EMPTY(3)
}
class RegistrationViewModel {
    fun IsLoginValid(login: String): LoginStates {
        var res: LoginStates = LoginStates.VALID
        if (login.isEmpty())
            res = LoginStates.EMPTY
        else
            if (login.length < 3 || login.length > 20)
                res = LoginStates.INVALID_LENGTH
            else
                if (login.all { it.isLetter() } == false)
                    res = LoginStates.INVALID_CHARACTERS
        return res
    }

    fun IsEmailValid(email: String): Boolean {
        return !(TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun IsPasswordValid(password: String): Boolean
    {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }
}