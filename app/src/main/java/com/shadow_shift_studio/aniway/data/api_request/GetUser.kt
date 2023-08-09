package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.repository.GetUserIdUsername
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume


/**
 * Класс `GetUser` предоставляет метод для получения информации о пользователе по его имени пользователя.
 * Этот класс реализует интерфейс `UserByUsernameRepository`.
 *
 * @constructor Создает экземпляр класса `GetUser`.
 */
class GetUser : GetUserIdUsername {

    /**
     * Получает информацию о пользователе по его имени пользователя.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя, для которого необходимо получить информацию.
     * @return Объект `User`, содержащий информацию о пользователе. В случае ошибки возвращается
     *         объект `User` с пустыми полями или значениями по умолчанию.
     */
    override suspend fun getUserByUsername(context: Context, username: String): User {
        // Создаем объект для вызова удаленного сервиса
        val backendService = HttpClientIsLogin.getUserService
        // Создаем объект с пустыми полями для использования в случае ошибки
        val userForErrorResponse = User(null, null, null, null, null, null, null, null, null, null, null, null)

        try {
            // Используем suspendCancellableCoroutine для работы с асинхронным кодом
            return suspendCancellableCoroutine { continuation ->
                // Создаем вызов к удаленному сервису
                val call = backendService.userByUsername(username)

                // Обработка успешного ответа от сервера
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Создаем объект User на основе полученных данных
                                val user = User(
                                    id = responseBody.id,
                                    username = responseBody.username,
                                    sex = responseBody.sex,
                                    xp = responseBody.xp,
                                    pass_xp = responseBody.pass_xp,
                                    balance = responseBody.balance,
                                    createdAt = responseBody.createdAt,
                                    chapters = responseBody.chapters,
                                    likes = responseBody.likes,
                                    comments = responseBody.comments,
                                    avatarUrl = responseBody.avatarUrl,
                                    backgroundUrl = responseBody.backgroundUrl
                                )
                                continuation.resume(user) // Возобновляем выполнение корутины с полученным пользователем
                            } else {
                                continuation.resume(userForErrorResponse) // В случае отсутствия данных в ответе, возвращаем пустого пользователя
                            }
                        } else {
                            Log.e("Login Error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse) // В случае ошибки в ответе, возвращаем пустого пользователя
                        }
                    }

                    // Обработка ошибки при выполнении запроса
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(userForErrorResponse) // В случае ошибки, возвращаем пустого пользователя
                    }
                })

                // Отменяем вызов при отмене корутины
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("Ошибка", e.toString()) // Обработка общей ошибки и логирование
        }

        return userForErrorResponse // Возвращаем пустого пользователя в случае возникновения ошибки
    }

    override suspend fun getUserById(context: Context, id: String): User {
        // Создаем объект для вызова удаленного сервиса
        val backendService = HttpClientIsLogin.getUserService
        // Создаем объект с пустыми полями для использования в случае ошибки
        val userForErrorResponse = User(null, null, null, null, null, null, null, null, null, null, null, null)

        try {
            // Используем suspendCancellableCoroutine для работы с асинхронным кодом
            return suspendCancellableCoroutine { continuation ->
                // Создаем вызов к удаленному сервису
                val call = backendService.userById(id)

                // Обработка успешного ответа от сервера
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Создаем объект User на основе полученных данных
                                val user = User(
                                    id = responseBody.id,
                                    username = responseBody.username,
                                    sex = responseBody.sex,
                                    xp = responseBody.xp,
                                    pass_xp = responseBody.pass_xp,
                                    balance = responseBody.balance,
                                    createdAt = responseBody.createdAt,
                                    chapters = responseBody.chapters,
                                    likes = responseBody.likes,
                                    comments = responseBody.comments,
                                    avatarUrl = responseBody.avatarUrl,
                                    backgroundUrl = responseBody.backgroundUrl
                                )
                                continuation.resume(user) // Возобновляем выполнение корутины с полученным пользователем
                            } else {
                                continuation.resume(userForErrorResponse) // В случае отсутствия данных в ответе, возвращаем пустого пользователя
                            }
                        } else {
                            Log.e("Login Error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse) // В случае ошибки в ответе, возвращаем пустого пользователя
                        }
                    }

                    // Обработка ошибки при выполнении запроса
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(userForErrorResponse) // В случае ошибки, возвращаем пустого пользователя
                    }
                })

                // Отменяем вызов при отмене корутины
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("Ошибка", e.toString()) // Обработка общей ошибки и логирование
        }

        return userForErrorResponse // Возвращаем пустого пользователя в случае возникновения ошибки
    }
}
