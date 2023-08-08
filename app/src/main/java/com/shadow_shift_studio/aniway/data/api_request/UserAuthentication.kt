package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.data.client.HttpClientNotLogin
import com.shadow_shift_studio.aniway.data.client.KeyStoreManager
import com.shadow_shift_studio.aniway.data.data_class.CredentialsForLogin
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse
import com.shadow_shift_studio.aniway.domain.repository.LoginRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Класс UserAuthentication предоставляет методы для аутентификации пользователя и обработки полученных токенов.
 * Он позволяет выполнять вход пользователя и зашифровывать полученные токены для безопасного хранения.
 */
class UserAuthentication : LoginRepository {
    /**
     * Выполняет процесс входа пользователя.
     *
     * @param context Контекст приложения, необходимый для создания Keystore.
     */

    override suspend fun loginUser(context: Context, username: String, password: String) : Boolean {
        val backendService = HttpClientNotLogin.loginService
        val credentials = CredentialsForLogin(username, password)

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.login(credentials)
            call.enqueue(object : retrofit2.Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val keyStore = KeyStoreManager.getKeyStore(context)
                            keyStore.createSecretKey("1")
                            keyStore.createSecretKey("2")
                            val encryptedAccessToken = keyStore.encryptData("1", responseBody.accessToken.toByteArray())
                            val encryptedRefreshToken = keyStore.encryptData("2", responseBody.token.toByteArray())

                            KeyStoreManager.accessToken = responseBody.accessToken
                            KeyStoreManager.token = responseBody.token

                            Log.e("token syka", KeyStoreManager.accessToken)

                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    } else {
                        Log.e("Login Error", response.errorBody().toString())
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

}
