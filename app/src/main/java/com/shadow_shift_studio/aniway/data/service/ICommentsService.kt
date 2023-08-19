package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForCreateComment
import com.shadow_shift_studio.aniway.model.entity.Comment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The `IUserComments` interface defines methods for retrieving user comments.
 */
interface ICommentsService {
    /**
     * Retrieves a list of comments for the user based on their username.
     *
     * @param username The username of the user for whom to retrieve comments.
     * @return An object of type `Call<List<Comment>>`, representing the request's result.
     */
    @GET("/comment/get_comments/{username}")
    fun commentsByUsername(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Call<List<Comment>>

    @GET("/comment/title")
    fun titleComments(
        @Query("titleId") titleId: Long,
        @Query("page") page: Int
    ): Call<List<Comment>>

    @POST("/comment/create")
    fun createComment(@Body credentialsForCreateComment: CredentialsForCreateComment): Call<String>
}

