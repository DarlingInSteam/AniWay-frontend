package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetAchievementsRepository
import com.shadow_shift_studio.aniway.model.entity.Achievement

/**
 * The `GetAchievementsUseCase` class represents a use case for retrieving user achievements.
 *
 * @property getAchievement Repository for retrieving user achievements.
 * @constructor Creates an instance of the `GetAchievementsUseCase` class.
 */
class GetAchievementsUseCase(private val getAchievement: IGetAchievementsRepository) {
    /**
     * Retrieves a list of user achievements by username and received status.
     *
     * @param context The application context.
     * @param username The username of the user for whom you want to retrieve achievements.
     * @param received The received status indicating whether to retrieve received or pending achievements.
     * @return A list of user achievements.
     */
    suspend fun getAchievementsByUsername(context: Context, username: String, received: Boolean): List<Achievement> {
        return getAchievement.getAchievementByUsername(context, username, received)
    }
}
