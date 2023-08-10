package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.User

/**
 * The `IGetUserIdUsername` interface defines methods for retrieving user information by username and user ID.
 */
interface IGetUser {
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
}

