package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogCategoriesRepository
import com.shadow_shift_studio.aniway.model.entity.Category

class GetCatalogCategoriesUseCase(private val categories : IGetCatalogCategoriesRepository) {
    suspend fun getCatalogCategories(context: Context) : List<Category> {
        return categories.getCatalogCategories(context)
    }
}