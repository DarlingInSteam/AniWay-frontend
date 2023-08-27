package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.ICommentsRepository
import com.shadow_shift_studio.aniway.model.entity.Comment

/**
 * The `CommentsUseCase` class represents a use case for retrieving and creating user comments.
 *
 * @property comment Repository for retrieving and creating user comments.
 * @constructor Creates an instance of the `CommentsUseCase` class.
 */
class CommentsUseCase(private val comment: ICommentsRepository) {
    /**
     * Retrieves a list of user comments by username and page number.
     *
     * @param context The application context.
     * @param username The username of the user for whom you want to retrieve comments.
     * @param page The page number of comments to retrieve.
     * @return A list of user comments.
     */
    suspend fun getUserCommentsByUsername(context: Context, username: String, page: Int): List<Comment> {
        return comment.getUserCommentsByUsername(context, username, page)
    }

    /**
     * Retrieves a list of comments for a specific title by title ID and page number.
     *
     * @param context The application context.
     * @param titleId The ID of the title for which you want to retrieve comments.
     * @param page The page number of comments to retrieve.
     * @return A list of title comments.
     */
    suspend fun getTitleComments(context: Context, titleId: Long, page: Int): List<Comment> {
        return comment.getTitleComments(context, titleId, page)
    }

    /**
     * Creates a new comment for a specific title's chapter.
     *
     * @param context The application context.
     * @param titleId The ID of the title for which the comment is being created.
     * @param chapterId The ID of the chapter to which the comment is associated.
     * @param text The text content of the comment.
     * @return A result string indicating the success or failure of the comment creation.
     */
    suspend fun createComment(context: Context, titleId: Long, chapterId: Long, text: String): String {
        return comment.createComment(context, titleId, chapterId, text)
    }

    suspend fun deleteComment(context: Context, username: String, id: Long): String{
        return comment.deleteComment(context, username, id)
    }

    suspend fun updateComment(context: Context, username: String, comment_id: Long, text: String): String {
        return comment.updateComment(context, username, comment_id, text)
    }
}
