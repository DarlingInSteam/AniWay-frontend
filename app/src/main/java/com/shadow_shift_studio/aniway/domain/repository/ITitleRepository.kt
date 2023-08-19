package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus

interface ITitleRepository {
    suspend fun getTitle(context: Context, id: Long): Title
    suspend fun setTitleReadingStatus(context: Context, username: String, titleId: Long, status: ReadingStatus): String

    suspend fun getBookmarksTitles(context: Context, username: String, readingStatus: ReadingStatus): List<TitlePreview>
}