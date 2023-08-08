package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context

interface LoginRepository {
    suspend fun loginUser(context: Context, username: String, password: String) : Boolean
}