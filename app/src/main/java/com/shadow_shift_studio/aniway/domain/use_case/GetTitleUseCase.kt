package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetTitle
import com.shadow_shift_studio.aniway.model.entity.Title

class GetTitleUseCase(private val title: IGetTitle) {
    suspend fun getTitle(context: Context, id: Long): Title {
        return title.getTitle(context, id)
    }
}