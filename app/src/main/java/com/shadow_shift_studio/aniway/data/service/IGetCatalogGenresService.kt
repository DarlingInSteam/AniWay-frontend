package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Genre
import retrofit2.Call
import retrofit2.http.GET

/**
 * The `IGetCatalogGenresService` interface provides a method for retrieving catalog genres through an API.
 */
interface IGetCatalogGenresService {
    /**
     * Retrieves a list of catalog genres.
     *
     * @return A `Call` object representing the asynchronous request to retrieve catalog genres.
     */
    @GET("/genre/all")
    fun getCatalogGenres(): Call<List<Genre>>
}
