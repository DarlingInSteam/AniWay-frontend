package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Achievement
import com.shadow_shift_studio.aniway.model.entity.User

/**
 * The `IGetUserIdUsername` interface defines methods for retrieving user information by username and user ID.
 */
interface IUserRepository {
    /**
     * Retrieves information about a user based on their username.
     *
     * @param context The application context.
     * @param username The username of the user for whom to retrieve information.
     * @return An object of type `User` containing user information.
     */
    suspend fun getUserByUsername(context: Context, username: String): User

    /**
     * Retrieves information about a user based on their user ID.
     *
     * @param context The application context.
     * @param id The user ID for which to retrieve information.
     * @return An object of type `User` containing user information.
     */
    suspend fun getUserById(context: Context, id: String): User

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

