package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogRepository
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType

class GetCatalogUseCase(private val catalog : IGetCatalogRepository) {
    suspend fun getCatalog(
        context: Context,
        genres: List<String>,
        statuses: List<TitleStatus>,
        types: List<TitleType>,
        categories: List<String>,
        ageRatings: List<AgeRating>,
        page: Int
    ): List<TitlePreview> {
        return catalog.getCatalog(context, genres, statuses, types, categories, ageRatings, page)
    }
}