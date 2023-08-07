package com.shadow_shift_studio.aniway.view_model

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.shadow_shift_studio.aniway.data.data_class.BackendService
import com.shadow_shift_studio.aniway.data.data_class.Credentials
import com.shadow_shift_studio.aniway.data.data_class.KeyStore
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse
import com.shadow_shift_studio.aniway.data.data_class.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


enum class LoginStates(val value: Int)
{
    INVALID_LENGTH(0),
    INVALID_CHARACTERS(1),
    VALID(2),
    EMPTY(3)
}
class RegistrationViewModel(private val context: Context) : ViewModel() {

    var login: MutableState<String> = mutableStateOf("DarlingInSteam")
    var email: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("artem11112003")
    var repeatPassword: MutableState<String> = mutableStateOf("")
    var a: MutableState<String> = mutableStateOf("")
    var b: MutableState<String> = mutableStateOf("")

    fun isLoginValid(login: String): LoginStates {
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

    fun isEmailValid(email: String): Boolean {
        return !(TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isPasswordsMatch(): Boolean
    {
        return repeatPassword.value.equals(password.value)
    }
    fun isPasswordValid(password: String): Boolean
    {
        var res = true
        val pattern = Regex("^[a-zA-Z0-9!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]*$")

        if (password.length < 8)
            res = false
        else if (!pattern.matches(password))
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

    fun isAllDataEntered():Boolean
    {
        var res = false
        if(isLoginValid(login.value) == LoginStates.VALID
            && isPasswordValid(password.value)
            && isEmailValid(email.value)
            && isPasswordsMatch())
            res = true
        return res
    }

    fun createBackendService(): BackendService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // Замените на URL вашего локального бэкенда
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BackendService::class.java)
    }

    fun loginUser() {
        val backendService = createBackendService()

        val credentials = Credentials("DarlingInSteam", "artem11112003")

        val call = backendService.login(credentials)
        call.enqueue(object : retrofit2.Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        try {
                            val keyStore = KeyStore(context)
                            keyStore.createSecretKey("1")
                            keyStore.createSecretKey("2")

                            val encryptedAccessToken = keyStore.encryptData("1", responseBody.accessToken.toByteArray())
                            val encryptedRefreshToken = keyStore.encryptData("2", responseBody.token.toByteArray())

                            val decryptedAccessToken = String(keyStore.decryptData("1", encryptedAccessToken))
                            val decryptedRefreshToken = String(keyStore.decryptData("2", encryptedRefreshToken))


                        } catch (e: Exception) {
                            Log.e("login pars request error", "An error occurred while encrypting tokens: ${e.message}")
                        }


                    }
                    // Обработайте ответ от бэкенда, например, сохраните его куда-либо
                } else {
                    Log.e("Login Error", response.errorBody().toString())

                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.e("Login Error", t.message ?: "Unknown Error")
            }
        })
    }
}

