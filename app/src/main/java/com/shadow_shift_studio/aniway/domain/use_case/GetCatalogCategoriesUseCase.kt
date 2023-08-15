package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogCategories
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre

class GetCatalogCategoriesUseCase(private val categories : IGetCatalogCategories) {
    suspend fun getCatalogCategories(context: Context) : List<Category> {
        return categories.getCatalogCategories(context)
    }
}