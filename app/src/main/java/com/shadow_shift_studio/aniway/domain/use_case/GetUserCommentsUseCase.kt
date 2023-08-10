package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IGetUserComments
import com.shadow_shift_studio.aniway.model.entity.Comment

class GetUserCommentsUseCase(private val getUserComment: IGetUserComments) {
    suspend fun getUserCommentsByUsername(context: Context, username: String): List<Comment> {
        return getUserComment.getUserCommentsByUsername(context, username)
    }
}