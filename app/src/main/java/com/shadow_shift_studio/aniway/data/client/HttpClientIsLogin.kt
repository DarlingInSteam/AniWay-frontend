package com.shadow_shift_studio.aniway.data.client

import com.shadow_shift_studio.aniway.data.service.IUserByIdUsername
import com.shadow_shift_studio.aniway.data.service.IUserComments
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Объект `HttpClientIsLogin` предоставляет экземпляр `Retrofit`, настроенный для выполнения HTTP-запросов
 * к удаленному серверу с авторизационным заголовком Bearer token.
 */
object HttpClientIsLogin {
    private val retrofit: Retrofit = Retrofit.Builder()
        //нужно поставить ip своего компа
        .baseUrl("http://192.168.0.7:8080") // Базовый URL удаленного сервера
        .addConverterFactory(GsonConverterFactory.create()) // Конвертер для обработки JSON
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            // Добавление авторизационного заголовка Bearer token к запросу
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${KeyStoreManager.getDecryptAccessKey("1")}")
                .build()
            chain.proceed(request) // Продолжение выполнения запроса с добавленным заголовком
        }.build())
        .build()

    // Создание сервиса для выполнения запросов
    val getUserService: IUserByIdUsername = retrofit.create(IUserByIdUsername::class.java)
    val getUserCommentsService: IUserComments = retrofit.create(IUserComments::class.java)
}
