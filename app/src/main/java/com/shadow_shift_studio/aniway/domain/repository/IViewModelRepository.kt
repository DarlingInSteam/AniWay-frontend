package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context

interface IViewModelRepository {
    suspend fun removeTrailingSpaces(input: String): String
}