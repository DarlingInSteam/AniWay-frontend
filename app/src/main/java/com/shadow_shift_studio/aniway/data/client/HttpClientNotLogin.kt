package com.shadow_shift_studio.aniway.data.client

import com.shadow_shift_studio.aniway.data.service.LoginService
import com.shadow_shift_studio.aniway.data.service.UserByUsernameService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClientNotLogin {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val userByUsernameService: UserByUsernameService = retrofit.create(UserByUsernameService::class.java)
}