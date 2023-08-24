package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForCreateComment
import com.shadow_shift_studio.aniway.model.entity.Comment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The `ICommentsService` interface provides methods for retrieving and creating user comments.
 */
interface ICommentsService {
    /**
     * Retrieves a list of comments for the specified user's username and page number.
     *
     * @param username The username of the user for whom to retrieve comments.
     * @param page The page number of comments to retrieve.
     * @return A `Call` object representing the asynchronous request to retrieve comments.
     */
    @GET("/comment/get_comments/{username}")
    fun commentsByUsername(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Call<List<Comment>>

    /**
     * Retrieves a list of comments for the specified title ID and page number.
     *
     * @param titleId The ID of the title for which to retrieve comments.
     * @param page The page number of comments to retrieve.
     * @return A `Call` object representing the asynchronous request to retrieve comments.
     */
    @GET("/comment/title")
    fun titleComments(
        @Query("titleId") titleId: Long,
        @Query("page") page: Int
    ): Call<List<Comment>>

    /**
     * Creates a new comment using the provided credentials.
     *
     * @param credentialsForCreateComment The credentials required to create a new comment.
     * @return A `Call` object representing the asynchronous request to create a comment.
     */
    @POST("/comment/create")
    fun createComment(@Body credentialsForCreateComment: CredentialsForCreateComment): Call<String>

    @DELETE("/comment/delete")
    fun deleteComment(
        @Query("username") username: String,
        @Query("id") id: Long
    ): Call<String>
}


