package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import retrofit2.Call
import retrofit2.http.GET

interface IGetCatalogCategoriesService {
    @GET("/category/all")
    fun getCatalogCategories() : Call<List<Category>>
}