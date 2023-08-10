package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The `IUserComments` interface defines methods for retrieving user comments.
 */
interface IGetUserCommentsService {
    /**
     * Retrieves a list of comments for the user based on their username.
     *
     * @param username The username of the user for whom to retrieve comments.
     * @return An object of type `Call<List<Comment>>`, representing the request's result.
     */
    @GET("/comment/get_comments/{username}")
    fun commentsByUsername(@Path("username") username: String): Call<List<Comment>>
}

