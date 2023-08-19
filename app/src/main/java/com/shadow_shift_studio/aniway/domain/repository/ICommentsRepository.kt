package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Comment

/**
 * The `IGetUserComments` interface defines a method for retrieving user comments by their username.
 */
interface ICommentsRepository {
    /**
     * Retrieves a list of comments for the user based on their username.
     *
     * @param context The application context.
     * @param username The username of the user for whom to retrieve comments.
     * @return A list of user comments.
     */
    suspend fun getUserCommentsByUsername(context: Context, username: String, page: Int): List<Comment>
    suspend fun getTitleComments(context: Context, titleId: Long, page: Int): List<Comment>

    suspend fun createComment(context: Context, titleId: Long, chapterId: Long, text: String): String
}

