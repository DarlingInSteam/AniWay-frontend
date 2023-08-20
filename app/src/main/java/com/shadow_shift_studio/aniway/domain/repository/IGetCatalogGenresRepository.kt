package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Genre

/**
 * The `IGetCatalogGenresRepository` interface defines a method for retrieving catalog genres.
 */
interface IGetCatalogGenresRepository {
    /**
     * Retrieves a list of catalog genres.
     *
     * @param context The application context.
     * @return A list of catalog genres.
     */
    suspend fun getCatalogGenres(context: Context): List<Genre>
}
