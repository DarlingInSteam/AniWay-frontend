package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.data.data_class.User

interface UserByUsernameRepository {
    suspend fun getUserByUsername(context: Context, username: String) : User
}