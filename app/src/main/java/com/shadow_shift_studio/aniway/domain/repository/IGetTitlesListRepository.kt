package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType

/**
 * The `IGetTitlesListRepository` interface defines a method for retrieving a list of titles with specified filters.
 */
interface IGetTitlesListRepository {
    /**
     * Retrieves a list of titles based on specified filters.
     *
     * @param context The application context.
     * @param genres A list of genre names for filtering titles.
     * @param statuses A list of title statuses for filtering titles.
     * @param types A list of title types for filtering titles.
     * @param categories A list of category names for filtering titles.
     * @param ageRatings A list of age ratings for filtering titles.
     * @param page The page number of titles to retrieve.
     * @return A list of title previews.
     */
    suspend fun getCatalog(
        context: Context,
        genres: List<String>,
        statuses: List<TitleStatus>,
        types: List<TitleType>,
        categories: List<String>,
        ageRatings: List<AgeRating>,
        page: Int
    ): List<TitlePreview>
}
