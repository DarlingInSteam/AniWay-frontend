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

class GetAchievementsRequest : IGetAchievementsRepository {
    override suspend fun getAchievementByUsername(context: Context, username: String, received: Boolean): List<Achievement> {
        // Initialize the HTTP client to fetch user comments.
        val backendService = HttpClientIsLogin.getUserAchievementsService

        // An empty list of comments for potential error handling.
        val achievementForErrorResponse = listOf<Achievement>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
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
                            Log.e("Comments get error", response.errorBody().toString())
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

        // Returning an empty list of comments in case of an error.
        return achievementForErrorResponse
    }
}