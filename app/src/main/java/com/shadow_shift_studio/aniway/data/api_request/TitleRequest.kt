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

class TitleRequest : ITitleRepository {
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
                                // Create a User object based on the received data
                                val title = responseBody
                                continuation.resume(title) // Resume the coroutine with the received user
                            } else {
                                continuation.resume(titleForErrorResponse) // Return an empty user object in case of missing data in the response
                            }
                        } else {
                            Log.e("Get User error", response.errorBody().toString())
                            continuation.resume(titleForErrorResponse) // Return an empty user object in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<Title>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(titleForErrorResponse) // Return an empty user object in case of an error
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
                                // Create a User object based on the received data
                                val title = responseBody
                                continuation.resume(title) // Resume the coroutine with the received user
                            } else {
                                continuation.resume(titleForErrorResponse) // Return an empty user object in case of missing data in the response
                            }
                        } else {
                            Log.e("Get User error", response.errorBody().toString())
                            continuation.resume(titleForErrorResponse) // Return an empty user object in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(titleForErrorResponse) // Return an empty user object in case of an error
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

    override suspend fun getBookmarksTitles(
        context: Context,
        username: String,
        readingStatus: ReadingStatus
    ): List<TitlePreview> {
        val backendService = HttpClientIsLogin.titleService

        // An empty list of comments for potential error handling.
        val titleForErrorResponse = listOf<TitlePreview>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
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
                            Log.e("Comments get error", response.errorBody().toString())
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

        // Returning an empty list of comments in case of an error.
        return titleForErrorResponse
    }
}