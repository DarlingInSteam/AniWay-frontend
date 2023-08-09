package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.RegistrationRepository
import com.shadow_shift_studio.aniway.model.enum.Sex

class RegisterUserUseCase(private val userRepository: RegistrationRepository) {
    suspend fun execute(context: Context, username: String, email: String, password: String, sex: Sex): Boolean {
        return userRepository.registerUser(context, username, email, password, sex)
    }
}