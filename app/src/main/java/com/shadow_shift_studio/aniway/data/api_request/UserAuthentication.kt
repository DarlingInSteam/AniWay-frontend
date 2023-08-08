package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.shadow_shift_studio.aniway.data.client.HttpClient
import com.shadow_shift_studio.aniway.data.data_class.Credentials
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse
import com.shadow_shift_studio.aniway.data.secure_data.KeyStore
import com.shadow_shift_studio.aniway.domain.repository.UserRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Класс UserAuthentication предоставляет методы для аутентификации пользователя и обработки полученных токенов.
 * Он позволяет выполнять вход пользователя и зашифровывать полученные токены для безопасного хранения.
 */
class UserAuthentication : UserRepository {
    var loginStatus = false

    /**
     * Выполняет процесс входа пользователя.
     *
     * @param context Контекст приложения, необходимый для создания Keystore.
     */

    override suspend fun loginUser(context: Context, username: String, password: String) : Boolean {
        val backendService = HttpClient.backendService
        val credentials = Credentials(username, password)

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.login(credentials)
            call.enqueue(object : retrofit2.Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val keyStore = KeyStore(context)
                            keyStore.createSecretKey("1")
                            keyStore.createSecretKey("2")
                            val encryptedAccessToken = keyStore.encryptData("1", responseBody.accessToken.toByteArray())
                            val encryptedRefreshToken = keyStore.encryptData("2", responseBody.token.toByteArray())
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
