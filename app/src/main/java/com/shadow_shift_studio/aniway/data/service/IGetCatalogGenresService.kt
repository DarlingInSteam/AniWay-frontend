package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Genre
import retrofit2.Call
import retrofit2.http.GET

interface IGetCatalogGenresService {
    @GET("/genre/all")
    fun getCatalogGenres() : Call<List<Genre>>
}