package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetUserComments
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class GetUserCommentsRequest : IGetUserComments {
    override suspend fun getUserCommentsByUsername(context: Context, username: String): List<Comment> {
        // Создаем объект для вызова удаленного сервиса
        val backendService = HttpClientIsLogin.getUserCommentsService
        // Создаем объект с пустыми полями для использования в случае ошибки
        val userForErrorResponse = listOf<Comment>()

        try {
            // Используем suspendCancellableCoroutine для работы с асинхронным кодом
            return suspendCancellableCoroutine { continuation ->
                // Создаем вызов к удаленному сервису
                val call = backendService.commentsByUsername(username)

                // Обработка успешного ответа от сервера
                call.enqueue(object : Callback<List<Comment>> {
                    override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody) // Возобновляем выполнение корутины с полученным пользователем
                            } else {
                                continuation.resume(userForErrorResponse) // В случае отсутствия данных в ответе, возвращаем пустого пользователя
                            }
                        } else {
                            Log.e("Login Error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse) // В случае ошибки в ответе, возвращаем пустого пользователя
                        }
                    }

                    // Обработка ошибки при выполнении запроса
                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
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