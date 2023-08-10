package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.User

interface IGetUserComments {
    suspend fun getUserCommentsByUsername(context: Context, username: String): List<Comment>

}