package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.data_class.Credentials
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

interface BackendService {
    //Путь к API методу
    @POST("/auth/login")
    //TokenResponse - класс, который соответствует ответу сервера
    fun login(@Body credentials: Credentials): Call<TokenResponse>
}
