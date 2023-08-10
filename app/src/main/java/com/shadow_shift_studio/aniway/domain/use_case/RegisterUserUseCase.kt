package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IRegistrationRepository
import com.shadow_shift_studio.aniway.model.enum.Sex

/**
 * The `RegisterUserUseCase` class represents a script (use case) to register a user.
 *
 * @property userRepository Repository for user registration.
 * @constructor Creates an instance of the `RegisterUserUseCase` class.
 */
class RegisterUserUseCase(private val userRepository: IRegistrationRepository) {
    /**
     * Performs user registration with the provided data.
     *
     * @param context Application context.
     * @param username Username.
     * @param email The user's email.
     * @param password User password.
     * @param sex The user's gender.
     * @return `true` if registration is successful, otherwise `false`.
     */
    suspend fun userRegister(context: Context, username: String, email: String, password: String, sex: Sex): Boolean {
        return userRepository.registerUser(context, username, email, password, sex)
    }
}
