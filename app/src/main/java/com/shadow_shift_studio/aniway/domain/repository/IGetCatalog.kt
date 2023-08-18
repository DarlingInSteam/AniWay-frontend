package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType

interface IGetCatalog {
    suspend fun getCatalog(
        context: Context,
        genres: List<String>,
        statuses: List<TitleStatus>,
        types: List<TitleType>,
        categories: List<String>,
        ageRatings: List<AgeRating>
    ): List<Title>
}