package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogCategoriesRepository
import com.shadow_shift_studio.aniway.model.entity.Category

/**
 * The `GetCatalogCategoriesUseCase` class represents a use case for retrieving catalog categories.
 *
 * @property categories Repository for retrieving catalog categories.
 * @constructor Creates an instance of the `GetCatalogCategoriesUseCase` class.
 */
class GetCatalogCategoriesUseCase(private val categories: IGetCatalogCategoriesRepository) {
    /**
     * Retrieves a list of catalog categories.
     *
     * @param context The application context.
     * @return A list of catalog categories.
     */
    suspend fun getCatalogCategories(context: Context): List<Category> {
        return categories.getCatalogCategories(context)
    }
}
