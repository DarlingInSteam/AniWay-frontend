package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetAchievementsRepository
import com.shadow_shift_studio.aniway.model.entity.Achievement
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Repository implementation for fetching achievements associated with a specific user by their username.
 *
 * This repository utilizes an HTTP client to communicate with the backend service and retrieve achievements.
 *
 * @constructor Creates an instance of GetAchievementsRequest.
 */
class GetAchievementsRequest : IGetAchievementsRepository {

    /**
     * Asynchronously retrieves a list of achievements associated with a user identified by their username.
     *
     * @param context The application context.
     * @param username The username of the user whose achievements are being fetched.
     * @param received Indicates whether to fetch received achievements (true) or not-received ones (false).
     * @return A list of Achievement objects representing the user's achievements based on the received status.
     *         An empty list will be returned in case of errors or lack of achievements.
     */
    override suspend fun getAchievementByUsername(context: Context, username: String, received: Boolean): List<Achievement> {
        // Initialize the HTTP client to fetch user achievements.
        val backendService = HttpClientIsLogin.getUserAchievementsService

        // An empty list of achievements for potential error handling.
        val achievementForErrorResponse = listOf<Achievement>()

        try {
            // Use a coroutine for asynchronous achievement fetching.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.achievementByUsername(username, received)

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<Achievement>> {
                    override fun onResponse(call: Call<List<Achievement>>, response: Response<List<Achievement>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(achievementForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Achievements get error", response.errorBody().toString())
                            continuation.resume(achievementForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<List<Achievement>>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(achievementForErrorResponse)
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

        // Returning an empty list of achievements in case of an error.
        return achievementForErrorResponse
    }
}
