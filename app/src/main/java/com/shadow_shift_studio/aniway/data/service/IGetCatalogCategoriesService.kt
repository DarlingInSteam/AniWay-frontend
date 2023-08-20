package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import retrofit2.Call
import retrofit2.http.GET

/**
 * The `IGetCatalogCategoriesService` interface provides a method for retrieving catalog categories through an API.
 */
interface IGetCatalogCategoriesService {
    /**
     * Retrieves a list of catalog categories.
     *
     * @return A `Call` object representing the asynchronous request to retrieve catalog categories.
     */
    @GET("/category/all")
    fun getCatalogCategories(): Call<List<Category>>
}
