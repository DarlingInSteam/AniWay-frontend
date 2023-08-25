package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForSetReadingStatus
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The `ITitleService` interface provides methods for interacting with title-related actions through an API.
 */
interface ITitleService {
    /**
     * Retrieves detailed information about a title based on its ID.
     *
     * @param id The unique ID of the title being retrieved.
     * @return A `Call` object representing the asynchronous request to retrieve title information.
     */
    @GET("/title/{id}")
    fun getTitle(@Path("id") id: Long): Call<Title>

    /**
     * Sets the reading status of a specific title for a given user.
     *
     * @param credentialsForSetReadingStatus The credentials required to set the reading status of a title.
     * @return A `Call` object representing the asynchronous request to set the reading status.
     */
    @POST("/title/set/reading_status")
    fun setTitleReadingStatus(@Body credentialsForSetReadingStatus: CredentialsForSetReadingStatus): Call<String>

    /**
     * Retrieves a list of title previews for titles bookmarked by a specific user with a given reading status.
     *
     * @param username The username of the user whose bookmarked titles are being retrieved.
     * @param readingStatus The reading status of the titles to be retrieved.
     * @return A `Call` object representing the asynchronous request to retrieve bookmarked titles.
     */
    @GET("user/{username}/titles")
    fun getUserTitles(@Path("username") username: String, @Query("readingStatus") readingStatus: ReadingStatus): Call<List<TitlePreview>>
}
