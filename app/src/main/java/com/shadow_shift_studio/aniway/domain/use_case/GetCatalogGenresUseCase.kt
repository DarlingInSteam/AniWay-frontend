package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogGenres
import com.shadow_shift_studio.aniway.model.entity.Genre

class GetCatalogGenresUseCase(private val getGenres : IGetCatalogGenres) {
    suspend fun getCatalogGenres(context: Context) : List<Genre> {
        return getGenres.getCatalogGenres(context)
    }
}