package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Genre

interface IGetCatalogGenresRepository {
    suspend fun getCatalogGenres(context: Context) : List<Genre>
}