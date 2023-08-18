package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Title
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IGetTitleService {
    @GET("/title/{id}")
    fun getTitle(@Path("id") id: Long): Call<Title>
}