package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForLogin
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

/**
 * Интерфейс `LoginService` определяет методы для выполнения HTTP-запросов связанных с аутентификацией.
 */
interface LoginService {

    /**
     * Выполняет POST-запрос на эндпоинт `/auth/login` для аутентификации пользователя.
     *
     * @param credentials Объект, содержащий учетные данные пользователя.
     * @return Объект типа `Call<TokenResponse>`, представляющий результат запроса.
     */
    @POST("/auth/login")
    fun login(@Body credentials: CredentialsForLogin): Call<TokenResponse>
}

