package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Comment

/**
 * The `ICommentsRepository` interface defines methods for interacting with user comments through a repository pattern.
 */
interface ICommentsRepository {
    /**
     * Retrieves a list of comments for the specified user's username and page number.
     *
     * @param context The application context.
     * @param username The username of the user for whom to retrieve comments.
     * @param page The page number of comments to retrieve.
     * @return A list of user comments.
     */
    suspend fun getUserCommentsByUsername(context: Context, username: String, page: Int): List<Comment>

    /**
     * Retrieves a list of comments for the specified title ID and page number.
     *
     * @param context The application context.
     * @param titleId The ID of the title for which to retrieve comments.
     * @param page The page number of comments to retrieve.
     * @return A list of title comments.
     */
    suspend fun getTitleComments(context: Context, titleId: Long, page: Int): List<Comment>

    /**
     * Creates a new comment for a specific title's chapter.
     *
     * @param context The application context.
     * @param titleId The ID of the title for which the comment is being created.
     * @param chapterId The ID of the chapter to which the comment is associated.
     * @param text The text content of the comment.
     * @return A result string indicating the success or failure of the comment creation.
     */
    suspend fun createComment(context: Context, titleId: Long, chapterId: Long, text: String): String

    suspend fun deleteComment(context: Context, username: String, id: Long): String

    suspend fun updateComment(context: Context, username: String, comment_Id: Long, text: String): String
}


