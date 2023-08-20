package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogGenresRepository
import com.shadow_shift_studio.aniway.model.entity.Genre
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Repository implementation for fetching catalog genres.
 *
 * This repository utilizes an HTTP client to communicate with the backend service and retrieve catalog genres.
 *
 * @constructor Creates an instance of GetCatalogGenresRequest.
 */
class GetCatalogGenresRequest : IGetCatalogGenresRepository {

    /**
     * Asynchronously retrieves a list of catalog genres.
     *
     * @param context The application context.
     * @return A list of Genre objects representing the catalog genres.
     *         An empty list will be returned in case of errors or lack of genres.
     */
    override suspend fun getCatalogGenres(context: Context): List<Genre> {
        // Initialize the HTTP client to fetch catalog genres.
        val backendService = HttpClientIsLogin.getCatalogGenresService

        // An empty list of genres for potential error handling.
        val genresForErrorResponse = listOf<Genre>()

        try {
            // Use a coroutine for asynchronous genre fetching.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.getCatalogGenres()

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<Genre>> {
                    override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(genresForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Genres get error", response.errorBody().toString())
                            continuation.resume(genresForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(genresForErrorResponse)
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

        // Returning an empty list of genres in case of an error.
        return genresForErrorResponse
    }

}
