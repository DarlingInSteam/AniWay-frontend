package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGetTitleService {
    @GET("/title/{id}")
    fun getTitle(@Path("id") id: Long): Call<Title>
}