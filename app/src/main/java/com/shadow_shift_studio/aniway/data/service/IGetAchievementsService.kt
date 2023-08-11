package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Achievement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGetAchievementsService {
    @GET("/achievement/get_achievements/{username}")
    fun achievementByUsername(@Path("username") username: String, @Query("received") received: Boolean): Call<List<Achievement>>
}