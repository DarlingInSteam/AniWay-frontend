package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogGenresRepository
import com.shadow_shift_studio.aniway.model.entity.Genre

/**
 * The `GetCatalogGenresUseCase` class represents a use case for retrieving catalog genres.
 *
 * @property getGenres Repository for retrieving catalog genres.
 * @constructor Creates an instance of the `GetCatalogGenresUseCase` class.
 */
class GetCatalogGenresUseCase(private val getGenres: IGetCatalogGenresRepository) {
    /**
     * Retrieves a list of catalog genres.
     *
     * @param context The application context.
     * @return A list of catalog genres.
     */
    suspend fun getCatalogGenres(context: Context): List<Genre> {
        return getGenres.getCatalogGenres(context)
    }
}
