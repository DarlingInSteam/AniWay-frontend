package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClient
import com.shadow_shift_studio.aniway.data.data_class.Credentials
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse
import com.shadow_shift_studio.aniway.data.secure_data.KeyStore
import com.shadow_shift_studio.aniway.domain.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

/**
 * Класс UserAuthentication предоставляет методы для аутентификации пользователя и обработки полученных токенов.
 * Он позволяет выполнять вход пользователя и зашифровывать полученные токены для безопасного хранения.
 */
class UserAuthentication : UserRepository {

    /**
     * Выполняет процесс входа пользователя.
     *
     * @param context Контекст приложения, необходимый для создания Keystore.
     */
    override suspend fun loginUser(context: Context, username: String, password: String) {
        val backendService = HttpClient.backendService

        val credentials = Credentials(username, password)

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

                            Log.e("test123", responseBody.accessToken)
                        } catch (e: Exception) {
                            Log.e("login pars request error", "An error occurred while encrypting tokens: ${e.message}")
                        }
                    }
                } else {
                    Log.e("Login Error", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.e("Network client error", t.message ?: "HTTP client failed to connect")
            }
        })
    }
}
