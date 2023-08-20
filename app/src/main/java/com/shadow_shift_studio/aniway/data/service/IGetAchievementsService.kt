package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.Achievement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The `IGetAchievementsService` interface provides methods for retrieving user achievements through an API.
 */
interface IGetAchievementsService {
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
}
