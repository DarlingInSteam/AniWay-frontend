package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.enum.Sex

interface IRegistrationRepository {
    suspend fun registerUser(context: Context, username: String, email:String, password: String, sex: Sex): Boolean
}