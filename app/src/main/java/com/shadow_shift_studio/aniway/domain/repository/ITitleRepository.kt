package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus

/**
 * The `ITitleRepository` interface defines methods for interacting with title-related actions and information.
 */
interface ITitleRepository {
    /**
     * Retrieves detailed information about a title based on its ID.
     *
     * @param context The application context.
     * @param id The unique ID of the title being retrieved.
     * @return A `Title` object containing detailed title information.
     */
    suspend fun getTitle(context: Context, id: Long): Title

    /**
     * Sets the reading status of a specific title for a given user.
     *
     * @param context The application context.
     * @param username The username of the user setting the reading status.
     * @param titleId The ID of the title for which the reading status is being set.
     * @param status The reading status to be set.
     * @return A result string indicating the success or failure of the operation.
     */
    suspend fun setTitleReadingStatus(context: Context, username: String, titleId: Long, status: ReadingStatus): String

    /**
     * Retrieves a list of title previews for titles bookmarked by a specific user with a given reading status.
     *
     * @param context The application context.
     * @param username The username of the user whose bookmarked titles are being retrieved.
     * @param readingStatus The reading status of the titles to be retrieved.
     * @return A list of title previews.
     */
    suspend fun getBookmarksTitles(context: Context, username: String, readingStatus: ReadingStatus): List<TitlePreview>
}
