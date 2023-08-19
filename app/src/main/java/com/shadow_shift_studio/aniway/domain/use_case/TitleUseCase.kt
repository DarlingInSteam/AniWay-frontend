package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.ITitleRepository
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus

class TitleUseCase(private val title: ITitleRepository) {
    suspend fun getTitle(context: Context, id: Long): Title {
        return title.getTitle(context, id)
    }

    suspend fun setTitleReadingStatus(context: Context, username: String, titleId: Long, status: ReadingStatus): String {
        return title.setTitleReadingStatus(context, username, titleId, status)
    }

    suspend fun getFavouritesTitles(context: Context, username: String, readingStatus: ReadingStatus): List<TitlePreview> {
        return title.getBookmarksTitles(context, username, readingStatus)
    }
}