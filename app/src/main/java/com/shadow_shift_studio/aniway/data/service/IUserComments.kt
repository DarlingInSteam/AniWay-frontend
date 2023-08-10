package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IUserComments {
    @GET("/comment/get_comments/{username}")
    fun commentsByUsername(@Path("username") username: String): Call<List<Comment>>
}