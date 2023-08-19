package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForSetReadingStatus
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ITitleService {
    @GET("/title/{id}")
    fun getTitle(@Path("id") id: Long): Call<Title>

    @POST("/title/set_reading_status")
    fun setTitleReadingStatus(@Body credentialsForSetReadingStatus: CredentialsForSetReadingStatus): Call<String>

    @GET("/title/get_user_titles/{username}")
    fun getUserTitles(@Path("username") username: String, @Query("readingStatus") readingStatus: ReadingStatus): Call<List<TitlePreview>>
}