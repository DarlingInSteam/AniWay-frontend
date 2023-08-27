package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForCreateComment
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForUpdateComment
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.repository.ICommentsRepository
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Implementation of the [ICommentsRepository] interface providing a method to retrieve a list of user comments by username.
 */
class CommentsRequest : ICommentsRepository {

    /**
     * Retrieves a list of user comments by their username.
     *
     * @param context The application context.
     * @param username The username for which to retrieve comments.
     * @return The list of user comments.
     */
    override suspend fun getUserCommentsByUsername(
        context: Context,
        username: String,
        page: Int
    ): List<Comment> {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty list of comments for potential error handling.
        val userForErrorResponse = listOf<Comment>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.commentsByUsername(username, page)

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

    /**
     * Asynchronously fetches a paginated list of user comments for a specific title identified by its ID.
     *
     * @param context The application context.
     * @param titleId The unique ID of the title for which comments are to be fetched.
     * @param page The page number of comments to be retrieved.
     * @return A list of Comment objects representing the user comments for the specified title and page.
     *         An empty list will be returned in case of errors or lack of comments.
     */
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


    /**
     * Asynchronously creates a new user comment for a specific title and chapter identified by their IDs.
     *
     * @param context The application context.
     * @param titleId The unique ID of the title for which the comment is being created.
     * @param chapterId The unique ID of the chapter to which the comment belongs.
     * @param text The content of the comment to be created.
     * @return A string representing the result of the comment creation process. In case of success,
     *         the response might contain additional information or identifiers related to the created comment.
     *         If an error occurs, an empty string is returned.
     */
    override suspend fun createComment(
        context: Context,
        titleId: Long,
        chapterId: Long,
        text: String
    ): String {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty string for potential error handling.
        val userForErrorResponse = ""

        try {
            // Use a coroutine for asynchronous comment creation.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.createComment(
                    CredentialsForCreateComment(AuthorizedUser.username.toString(), titleId, chapterId, text)
                )

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

        // Returning an empty string in case of an error.
        return userForErrorResponse
    }


    override suspend fun deleteComment(
        context: Context,
        username: String,
        id: Long
    ): String {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty string for potential error handling.
        val userForErrorResponse = ""

        try {
            // Use a coroutine for asynchronous comment creation.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.deleteComment(username, id)

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
                            Log.e("Comments delete error", response.errorBody().toString())
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

        // Returning an empty string in case of an error.
        return userForErrorResponse
    }


    override suspend fun updateComment(
        context: Context,
        username: String,
        comment_id: Long,
        text: String
    ): String {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.CommentsService

        // An empty string for potential error handling.
        val userForErrorResponse = ""

        try {
            // Use a coroutine for asynchronous comment creation.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.updateComment(
                    CredentialsForUpdateComment(username, comment_id, text)
                )

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
                            Log.e("Comments update error", response.errorBody().toString())
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

        // Returning an empty string in case of an error.
        return userForErrorResponse
    }
}

