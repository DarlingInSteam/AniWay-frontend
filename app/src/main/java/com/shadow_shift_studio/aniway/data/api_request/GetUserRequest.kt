package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetUserRepository
import com.shadow_shift_studio.aniway.model.entity.User
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume


/**
 * The `GetUserRequest` class provides methods for retrieving user information
 * by their username or ID. This class implements the `IGetUserIdUsername` and `IGetUserById` interfaces.
 *
 * @constructor Creates an instance of the `GetUserRequest` class.
 */
class GetUserRequest : IGetUserRepository {

    /**
     * Retrieves user information by their username.
     *
     * @param context The application context.
     * @param username The username for which to retrieve information.
     * @return A `User` object containing user information. In case of an error, a
     *         `User` object with empty fields or default values is returned.
     */
    override suspend fun getUserByUsername(context: Context, username: String): User {
        // Create an instance of the remote service caller
        val backendService = HttpClientIsLogin.getUserService
        // Create an instance with empty fields to use in case of an error
        val userForErrorResponse = User(null, null, null,  null, null, null, null, null, null, null, null, null)

        try {
            // Use suspendCancellableCoroutine for handling asynchronous code
            return suspendCancellableCoroutine { continuation ->
                // Create a call to the remote service
                val call = backendService.userByUsername(username)

                // Handling a successful response from the server
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Create a User object based on the received data
                                val user = User(
                                    id = responseBody.id,
                                    username = responseBody.username,
                                    sex = responseBody.sex,
                                    xp = responseBody.xp,
                                    pass_xp = responseBody.pass_xp,
                                    balance = responseBody.balance,
                                    createdAt = responseBody.createdAt,
                                    chapters = responseBody.chapters,
                                    likes = responseBody.likes,
                                    commentsCount = responseBody.commentsCount,
                                    avatarUrl = responseBody.avatarUrl,
                                    backgroundUrl = responseBody.backgroundUrl,
                                )
                                continuation.resume(user) // Resume the coroutine with the received user
                            } else {
                                continuation.resume(userForErrorResponse) // Return an empty user object in case of missing data in the response
                            }
                        } else {
                            Log.e("Get User error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse) // Return an empty user object in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(userForErrorResponse) // Return an empty user object in case of an error
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

        return userForErrorResponse // Return an empty user object in case of an error
    }


    /**
     * Retrieves user information by their ID.
     *
     * @param context The application context.
     * @param id The ID of the user for which to retrieve information.
     * @return A `User` object containing user information. In case of an error, a
     *         `User` object with empty fields or default values is returned.
     */
    override suspend fun getUserById(context: Context, id: String): User {
        // Create an instance of the remote service caller
        val backendService = HttpClientIsLogin.getUserService
        // Create an instance with empty fields to use in case of an error
        val userForErrorResponse = User(null, null, null, null, null, null, null, null, null, null,  null, null)

        try {
            // Use suspendCancellableCoroutine for handling asynchronous code
            return suspendCancellableCoroutine { continuation ->
                // Create a call to the remote service
                val call = backendService.userById(id)

                // Handling a successful response from the server
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Create a User object based on the received data
                                val user = User(
                                    id = responseBody.id,
                                    username = responseBody.username,
                                    sex = responseBody.sex,
                                    xp = responseBody.xp,
                                    pass_xp = responseBody.pass_xp,
                                    balance = responseBody.balance,
                                    createdAt = responseBody.createdAt,
                                    chapters = responseBody.chapters,
                                    likes = responseBody.likes,
                                    commentsCount = responseBody.commentsCount,
                                    avatarUrl = responseBody.avatarUrl,
                                    backgroundUrl = responseBody.backgroundUrl,
                                )
                                continuation.resume(user) // Resume the coroutine with the received user
                            } else {
                                continuation.resume(userForErrorResponse) // Return an empty user object in case of missing data in the response
                            }
                        } else {
                            Log.e("User get error", response.errorBody().toString())
                            continuation.resume(userForErrorResponse) // Return an empty user object in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(userForErrorResponse) // Return an empty user object in case of an error
                    }
                })

                // Cancel the call when the coroutine is canceled
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("Unknown Error", e.toString()) // Handle a general error and log it
        }

        return userForErrorResponse // Return an empty user object in case of an error
    }
}

