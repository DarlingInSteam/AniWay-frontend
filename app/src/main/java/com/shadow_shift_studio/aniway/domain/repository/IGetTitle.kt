package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Title

interface IGetTitle {
    suspend fun getTitle(context: Context, id: Long): Title
}