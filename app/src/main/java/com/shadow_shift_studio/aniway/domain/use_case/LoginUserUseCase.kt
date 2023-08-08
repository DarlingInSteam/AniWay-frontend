package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.UserRepository

class LoginUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(context: Context, username: String, password: String) {
        userRepository.loginUser(context, username, password)
    }
}