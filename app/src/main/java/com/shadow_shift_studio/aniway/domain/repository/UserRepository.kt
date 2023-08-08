package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.data.data_class.TokenResponse

interface UserRepository {
    suspend fun loginUser(context: Context, username: String, password: String)
}