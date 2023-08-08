package com.shadow_shift_studio.aniway.data.client

import com.shadow_shift_studio.aniway.data.service.BackendService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val backendService: BackendService = retrofit.create(BackendService::class.java)
}