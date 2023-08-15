package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Category

interface IGetCatalogCategories {
    suspend fun getCatalogCategories(context: Context) : List<Category>
}