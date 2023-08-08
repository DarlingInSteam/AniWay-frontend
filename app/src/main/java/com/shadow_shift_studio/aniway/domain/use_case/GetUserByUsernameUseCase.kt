package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.data.data_class.User
import com.shadow_shift_studio.aniway.domain.repository.UserByUsernameRepository

class GetUserByUsernameUseCase(private val getUserByUsernameRepository: UserByUsernameRepository) {
    suspend fun execute(context: Context, username: String) : User {
        return getUserByUsernameRepository.getUserByUsername(context, username)
    }
}