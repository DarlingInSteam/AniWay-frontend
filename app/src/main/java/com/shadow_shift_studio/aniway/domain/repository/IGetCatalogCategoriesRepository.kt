package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Category

/**
 * The `IGetCatalogCategoriesRepository` interface defines a method for retrieving catalog categories.
 */
interface IGetCatalogCategoriesRepository {
    /**
     * Retrieves a list of catalog categories.
     *
     * @param context The application context.
     * @return A list of catalog categories.
     */
    suspend fun getCatalogCategories(context: Context): List<Category>
}
