package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IComments
import com.shadow_shift_studio.aniway.model.entity.Comment

/**
 * The `GetUserCommentsUseCase` class represents a script (use case) to retrieve user comments.
 *
 * @property comment Repository for retrieving user comments.
 * @constructor Creates an instance of the `GetUserCommentsUseCase` class.
 */
class CommentsUseCase(private val comment: IComments) {
    /**
     * Gets a list of user comments by user name.
     *
     * @param context Application context.
     * @param username The name of the user for whom you want to retrieve comments.
     * @return List of user's comments.
     */
    suspend fun getUserCommentsByUsername(context: Context, username: String): List<Comment> {
        return comment.getUserCommentsByUsername(context, username)
    }
    suspend fun getTitleComments(context: Context, titleId: Long, page: Int): List<Comment> {
        return comment.getTitleComments(context, titleId, page)
    }

    suspend fun createComment(context: Context, titleId: Long, chapterId: Long, text: String) : String {
        return comment.createComment(context, titleId, chapterId, text)
    }
}