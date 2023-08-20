package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The `IGetCatalogService` interface provides a method for retrieving catalog titles with specified filters through an API.
 */
interface IGetCatalogService {
    /**
     * Retrieves a list of catalog titles based on specified filters.
     *
     * @param genres A list of genre names for filtering titles. Can be null or empty.
     * @param statuses A list of title statuses for filtering titles. Can be null or empty.
     * @param types A list of title types for filtering titles. Can be null or empty.
     * @param categories A list of category names for filtering titles. Can be null or empty.
     * @param ageRatings A list of age ratings for filtering titles. Can be null or empty.
     * @param page The page number of titles to retrieve.
     * @return A `Call` object representing the asynchronous request to retrieve catalog titles.
     */
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
