package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Achievement
import com.shadow_shift_studio.aniway.model.entity.Badge
import com.shadow_shift_studio.aniway.model.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The `IUserByIdUsername` interface defines methods for making HTTP requests to retrieve user information
 * based on the username or ID.
 */
interface IUserService {

    /**
     * Executes a GET request to the `/user/username/{username}` endpoint to retrieve user information
     * based on the username.
     *
     * @param username The username for which to retrieve information.
     * @return An object of type `Call<User>`, representing the request's result.
     */
    @GET("/user/username/{username}")
    fun userByUsername(@Path("username") username: String): Call<User>

    /**
     * Executes a GET request to the `/user/username/{id}` endpoint to retrieve user information
     * based on the ID.
     *
     * @param id The ID of the user for which to retrieve information.
     * @return An object of type `Call<User>`, representing the request's result.
     */
    @GET("/user/username/{id}")
    fun userById(@Path("id") id: String): Call<User>

    /**
     * Retrieves a list of achievements for the specified user's username and received status.
     *
     * @param username The username of the user for whom to retrieve achievements.
     * @param received The received status indicating whether to retrieve received or pending achievements.
     * @return A `Call` object representing the asynchronous request to retrieve achievements.
     */
    @GET("/achievement/get_achievements/{username}")
    fun achievementByUsername(
        @Path("username") username: String,
        @Query("received") received: Boolean
    ): Call<List<Achievement>>

    @GET("/user/badges")
    fun userBadges(
        @Query("username") username: String
    ): Call<List<Badge>>

    @POST("/user/set_badge")
    fun setBadge(
        @Query("username") username: String,
        @Query("badge_id") badgeId: Long
    ): Call<String>
}


