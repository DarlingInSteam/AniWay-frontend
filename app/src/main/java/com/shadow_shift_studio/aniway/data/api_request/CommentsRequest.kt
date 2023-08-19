package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForCreateComment
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.repository.IComments
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Implementation of the [IComments] interface providing a method to retrieve a list of user comments by username.
 */
class CommentsRequest : IComments {

    /**
     * Retrieves a list of user comments by their username.
     *
     * @param context The application context.
     * @param username The username for which to retrieve comments.
     * @return The list of user comments.
     */
    override suspend fun getUserCommentsByUsername(
        context: Context,
        username: String
    ): List<Comment> {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty list of comments for potential error handling.
        val userForErrorResponse = listOf<Comment>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.commentsByUsername(username)

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<Comment>> {
                    override fun onResponse(
                        call: Call<List<Comment>>,
                        response: Response<List<Comment>>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(userForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Comments get error", response.errorBody().toString())
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
            Log.e("Unknown Error", e.toString())
        }

        // Returning an empty list of comments in case of an error.
        return userForErrorResponse
    }

    override suspend fun getTitleComments(
        context: Context,
        titleId: Long,
        page: Int
    ): List<Comment> {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty list of comments for potential error handling.
        val commentsForErrorResponse = listOf<Comment>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.titleComments(titleId, page)

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<Comment>> {
                    override fun onResponse(
                        call: Call<List<Comment>>,
                        response: Response<List<Comment>>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(commentsForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Title Comments get error", response.errorBody().toString())
                            continuation.resume(commentsForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(commentsForErrorResponse)
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
        return commentsForErrorResponse
    }

    override suspend fun createComment(
        context: Context,
        titleId: Long,
        chapterId: Long,
        text: String
    ): String {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty list of comments for potential error handling.
        val userForErrorResponse = ""

        try {
            // Use a coroutine for asynchronous fetching of comments.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.createComment(CredentialsForCreateComment(AuthorizedUser.id, titleId, chapterId, text))

                // Handling successful response from the server.
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(userForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Comments create error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<String>, t: Throwable) {
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
            Log.e("Unknown Error", e.toString())
        }

        // Returning an empty list of comments in case of an error.
        return userForErrorResponse
    }
}

