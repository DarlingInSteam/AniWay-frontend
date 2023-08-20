package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Achievement

/**
 * The `IGetAchievementsRepository` interface defines a method for retrieving user achievements.
 */
interface IGetAchievementsRepository {
    /**
     * Retrieves a list of achievements for the specified user's username and received status.
     *
     * @param context The application context.
     * @param username The username of the user for whom to retrieve achievements.
     * @param received The received status indicating whether to retrieve received or pending achievements.
     * @return A list of user achievements.
     */
    suspend fun getAchievementByUsername(context: Context, username: String, received: Boolean): List<Achievement>
}
