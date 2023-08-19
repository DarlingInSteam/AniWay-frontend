package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGetCatalogService {
    @JvmSuppressWildcards
    @GET("/titles/catalog")
    fun getCatalogTitles(
        @Query("genres") genres: List<String>?,
        @Query("statuses") statuses: List<TitleStatus>?,
        @Query("types") types: List<TitleType>?,
        @Query("categories") categories: List<String>?,
        @Query("ageRatings")  ageRatings: List<AgeRating>?,
        @Query("page") page: Int
    ): Call<List<TitlePreview>>
}