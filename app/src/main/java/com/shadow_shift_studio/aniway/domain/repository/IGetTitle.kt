package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview

interface IGetTitle {
    suspend fun getTitle(context: Context, id: Long): Title
}