package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.ILoginRepository

/**
 * The `LoginUserUseCase` class is a use case (business logic layer),
 * which provides user authentication using username and password.
 *
 * @constructor Creates an instance of the `LoginUserUseCase` class.
 * @param userRepository The repository for user authentication.
 */
class LoginUserUseCase(private val userRepository: ILoginRepository) {

    /**
     * Performs user authentication using username and password.
     *
     * @param context Application context.
     * @param username Username of the user to log in.
     * @param password The password of the user to log in.
     * @return `true` if authentication is successful, otherwise `false`.
     */
    suspend fun execute(context: Context, username: String, password: String): Boolean {
        return userRepository.loginUser(context, username, password)
    }
}
