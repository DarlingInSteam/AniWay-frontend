package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForSetReadingStatus
import com.shadow_shift_studio.aniway.domain.repository.ITitleRepository
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Repository implementation for managing titles and their information.
 *
 * This repository uses an HTTP client to communicate with the backend service and perform operations on titles.
 *
 * @constructor Creates an instance of TitleRequest.
 */
class TitleRequest : ITitleRepository {

    /**
     * Asynchronously retrieves detailed information about a specific title identified by its ID.
     *
     * @param context The application context.
     * @param id The unique ID of the title being retrieved.
     * @return A Title object representing the detailed information about the requested title.
     *         An instance with empty fields will be returned in case of errors or lack of data.
     */
    override suspend fun getTitle(context: Context, id: Long): Title {
        // Create an instance of the remote service caller
        val backendService = HttpClientIsLogin.titleService
        // Create an instance with empty fields to use in case of an error
        val titleForErrorResponse = Title(null, null, null,  null, null, null, null, null, null, null, null, null)

        try {
            // Use suspendCancellableCoroutine for handling asynchronous code
            return suspendCancellableCoroutine { continuation ->
                // Create a call to the remote service
                val call = backendService.getTitle(id)

                // Handling a successful response from the server
                call.enqueue(object : Callback<Title> {
                    override fun onResponse(call: Call<Title>, response: Response<Title>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Create a Title object based on the received data
                                val title = responseBody
                                continuation.resume(title) // Resume the coroutine with the received title
                            } else {
                                continuation.resume(titleForErrorResponse) // Return an empty title object in case of missing data in the response
                            }
                        } else {
                            Log.e("Get Title error", response.errorBody().toString())
                            continuation.resume(titleForErrorResponse) // Return an empty title object in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<Title>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(titleForErrorResponse) // Return an empty title object in case of an error
                    }
                })

                // Cancel the call when the coroutine is canceled
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString()) // Handle a general error and log it
        }

        return titleForErrorResponse
    }

    /**
     * Asynchronously updates the reading status of a specific title for a given user.
     *
     * @param context The application context.
     * @param username The username of the user performing the action.
     * @param titleId The unique ID of the title being updated.
     * @param status The new reading status to be set for the title.
     * @return A string indicating the result of the operation. A success message or an empty string in case of errors.
     */
    override suspend fun setTitleReadingStatus(
        context: Context,
        username: String,
        titleId: Long,
        status: ReadingStatus
    ): String {
        val backendService = HttpClientIsLogin.titleService
        // Create an instance with empty fields to use in case of an error
        val titleForErrorResponse = ""

        try {
            // Use suspendCancellableCoroutine for handling asynchronous code
            return suspendCancellableCoroutine { continuation ->
                // Create a call to the remote service
                val call = backendService.setTitleReadingStatus(CredentialsForSetReadingStatus(username, titleId, status))

                // Handling a successful response from the server
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody) // Resume the coroutine with the response message
                            } else {
                                continuation.resume(titleForErrorResponse) // Return an empty string in case of missing data in the response
                            }
                        } else {
                            Log.e("Set Reading Status error", response.errorBody().toString())
                            continuation.resume(titleForErrorResponse) // Return an empty string in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(titleForErrorResponse) // Return an empty string in case of an error
                    }
                })

                // Cancel the call when the coroutine is canceled
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString()) // Handle a general error and log it
        }

        return titleForErrorResponse
    }

    /**
     * Asynchronously retrieves a list of title previews for titles that are bookmarked by a specific user with a given reading status.
     *
     * @param context The application context.
     * @param username The username of the user whose bookmarked titles are being retrieved.
     * @param readingStatus The reading status of the titles to be retrieved.
     * @return A list of TitlePreview objects representing the bookmarked titles with the specified reading status.
     *         An empty list will be returned in case of errors or lack of titles.
     */
    override suspend fun getBookmarksTitles(
        context: Context,
        username: String,
        readingStatus: ReadingStatus
    ): List<TitlePreview> {
        val backendService = HttpClientIsLogin.titleService

        // An empty list of title previews for potential error handling.
        val titleForErrorResponse = listOf<TitlePreview>()

        try {
            // Use a coroutine for asynchronous fetching of titles.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.getUserTitles(username, readingStatus)

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<TitlePreview>> {
                    override fun onResponse(call: Call<List<TitlePreview>>, response: Response<List<TitlePreview>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(titleForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Titles get error", response.errorBody().toString())
                            continuation.resume(titleForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<List<TitlePreview>>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(titleForErrorResponse)
                    }
                })

                // Canceling the request in case of coroutine cancellation.
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            // Handling potential exceptions.
            Log.e("Unknown Error", e.toString())
        }

        // Returning an empty list of title previews in case of an error.
        return titleForErrorResponse
    }
}
