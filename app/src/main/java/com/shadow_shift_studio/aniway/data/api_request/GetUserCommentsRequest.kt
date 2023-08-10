package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetUserComments
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Implementation of the [IGetUserComments] interface providing a method to retrieve a list of user comments by username.
 */
class GetUserCommentsRequest : IGetUserComments {

    /**
     * Retrieves a list of user comments by their username.
     *
     * @param context The application context.
     * @param username The username for which to retrieve comments.
     * @return The list of user comments.
     */
    override suspend fun getUserCommentsByUsername(context: Context, username: String): List<Comment> {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.getUserCommentsService

        // An empty list of comments for potential error handling.
        val userForErrorResponse = listOf<Comment>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.commentsByUsername(username)

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<Comment>> {
                    override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(userForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Comments error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(userForErrorResponse)
                    }
                })

                // Canceling the request in case of coroutine cancellation.
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            // Handling potential exceptions.
            Log.e("Error", e.toString())
        }

        // Returning an empty list of comments in case of an error.
        return userForErrorResponse
    }
}

