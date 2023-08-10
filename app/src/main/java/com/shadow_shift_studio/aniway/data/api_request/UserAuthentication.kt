package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientNotLogin
import com.shadow_shift_studio.aniway.data.client.KeyStoreManager
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForLogin
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import com.shadow_shift_studio.aniway.domain.repository.LoginRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Класс `UserAuthentication` предоставляет метод для аутентификации пользователя с использованием имени
 * пользователя и пароля. Этот класс реализует интерфейс `LoginRepository`.
 *
 * @constructor Создает экземпляр класса `UserAuthentication`.
 */
class UserAuthentication : LoginRepository {

    /**
     * Аутентифицирует пользователя с использованием имени пользователя и пароля.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя для входа.
     * @param password Пароль пользователя для входа.
     * @return `true`, если аутентификация прошла успешно, иначе `false`.
     */
    override suspend fun loginUser(context: Context, username: String, password: String): Boolean {
        // Создаем объект для вызова удаленного сервиса
        val backendService = HttpClientNotLogin.loginService
        // Создаем объект с учетными данными пользователя
        val credentials = CredentialsForLogin(username, password)

        // Используем suspendCancellableCoroutine для работы с асинхронным кодом
        return suspendCancellableCoroutine { continuation ->
            // Создаем вызов к удаленному сервису
            val call = backendService.login(credentials)

            // Обработка успешного ответа от сервера
            call.enqueue(object : retrofit2.Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            // Создаем объект KeyStoreManager для управления хранилищем ключей
                            val keyStore = KeyStoreManager.getKeyStore(context)
                            // Создаем и шифруем токены доступа и обновления
                            keyStore.createSecretKey("1")
                            keyStore.createSecretKey("2")
                            val encryptedAccessToken = keyStore.encryptData("1", responseBody.accessToken.toByteArray())
                            val encryptedRefreshToken = keyStore.encryptData("2", responseBody.token.toByteArray())

                            // Сохраняем токены в KeyStoreManager для последующего использования
                            KeyStoreManager.accessToken = encryptedAccessToken
                            KeyStoreManager.token = encryptedRefreshToken

                            continuation.resume(true) // Возобновляем выполнение корутины с успешным результатом
                        } else {
                            continuation.resume(false) // В случае отсутствия данных в ответе, возвращаем `false`
                        }
                    } else {
                        Log.e("Ошибка входа", response.errorBody().toString())
                        continuation.resume(false) // В случае ошибки в ответе, возвращаем `false`
                    }
                }

                // Обработка ошибки при выполнении запроса
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Ошибка сети", t.message ?: "Ошибка подключения HTTP-клиента")
                    continuation.resume(false) // В случае ошибки, возвращаем `false`
                }
            })

            // Отменяем вызов при отмене корутины
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}

