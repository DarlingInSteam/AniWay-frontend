package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetAchievements
import com.shadow_shift_studio.aniway.model.entity.Achievement

class GetAchievementsUseCase(private val getAchievement: IGetAchievements) {
    suspend fun getAchievementsByUsername(context: Context, username: String, received: Boolean): List<Achievement> {
        return getAchievement.getAchievementByUsername(context, username, received)
    }
}