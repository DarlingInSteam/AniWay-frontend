package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The `IUserByIdUsername` interface defines methods for making HTTP requests to retrieve user information
 * based on the username or ID.
 */
interface IGetUserService {

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
}


