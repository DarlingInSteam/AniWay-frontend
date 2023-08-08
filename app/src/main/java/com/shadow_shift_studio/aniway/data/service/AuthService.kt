package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.data_class.CredentialsForLogin
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

interface LoginService {
    //Путь к API методу
    @POST("/auth/login")
    //TokenResponse - класс, который соответствует ответу сервера
    fun login(@Body credentials: CredentialsForLogin): Call<TokenResponse>
}
