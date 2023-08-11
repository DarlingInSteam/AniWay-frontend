package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Achievement

interface IGetAchievements {
    suspend fun getAchievementByUsername(context: Context, username: String, received: Boolean): List<Achievement>
}