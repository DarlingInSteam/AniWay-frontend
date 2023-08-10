package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context

/**
 * The `ILoginRepository` interface defines a method for user authentication.
 */
interface ILoginRepository {
    /**
     * Authenticates a user using the provided username and password.
     *
     * @param context The application context.
     * @param username The username for login.
     * @param password The user's password for login.
     * @return `true` if authentication is successful, otherwise `false`.
     */
    suspend fun loginUser(context: Context, username: String, password: String): Boolean
}

