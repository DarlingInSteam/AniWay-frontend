package com.shadow_shift_studio.aniway.data.client

import com.shadow_shift_studio.aniway.data.service.LoginService
import com.shadow_shift_studio.aniway.data.service.RegisterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Объект `HttpClientNotLogin` предоставляет экземпляр `Retrofit`, настроенный для выполнения HTTP-запросов
 * к удаленному серверу без авторизации.
 */
object HttpClientNotLogin {
    private val retrofit: Retrofit = Retrofit.Builder()
        //нужно поставить ip своего компа
        .baseUrl("http://10.0.2.2:8080") // Базовый URL удаленного сервера
        .addConverterFactory(GsonConverterFactory.create()) // Конвертер для обработки JSON
        .build()

    // Создание сервиса для выполнения запросов
    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val registrationService: RegisterService = retrofit.create(RegisterService::class.java)
}
