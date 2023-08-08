package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.LoginRepository

class LoginUserUseCase(private val userRepository: LoginRepository) {
    suspend fun execute(context: Context, username: String, password: String) : Boolean {
        return userRepository.loginUser(context, username, password)
    }
}