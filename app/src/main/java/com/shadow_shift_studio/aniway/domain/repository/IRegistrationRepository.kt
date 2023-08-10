package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.enum.Sex

/**
 * The `IRegistrationRepository` interface defines a method to register a user.
 */
interface IRegistrationRepository {
    /**
     * Registers the user with the provided data.
     *
     * @param context Application context.
     * @param username Username.
     * @param email The user's email.
     * @param password User password.
     * @param sex The user's gender.
     * @return `true` if registration is successful, otherwise `false`.
     */
    suspend fun registerUser(context: Context, username: String, email: String, password: String, sex: Sex): Boolean
}
