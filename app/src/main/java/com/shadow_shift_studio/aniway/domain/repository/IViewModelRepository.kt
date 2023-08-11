package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context

interface IViewModelRepository {
    fun removeTrailingSpaces(input: String): String
}