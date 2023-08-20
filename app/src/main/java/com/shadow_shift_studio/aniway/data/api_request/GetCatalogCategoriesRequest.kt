package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogCategoriesRepository
import com.shadow_shift_studio.aniway.model.entity.Category
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Repository implementation for fetching catalog categories.
 *
 * This repository uses an HTTP client to communicate with the backend service and retrieve catalog categories.
 *
 * @constructor Creates an instance of GetCatalogCategoriesRequest.
 */
class GetCatalogCategoriesRequest : IGetCatalogCategoriesRepository {

    /**
     * Asynchronously retrieves a list of catalog categories.
     *
     * @param context The application context.
     * @return A list of Category objects representing the catalog categories.
     *         An empty list will be returned in case of errors or lack of categories.
     */
    override suspend fun getCatalogCategories(context: Context): List<Category> {
        val backendService = HttpClientIsLogin.getCatalogCategoriesService

        // An empty list of categories for potential error handling.
        val genresForErrorResponse = listOf<Category>()

        try {
            // Use a coroutine for asynchronous category fetching.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.getCatalogCategories()

                // Handling successful response from the server.
                call.enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(genresForErrorResponse)
                            }
                        } else {
                            // Handling error response from the server.
                            Log.e("Categories get error", response.errorBody().toString())
                            continuation.resume(genresForErrorResponse)
                        }
                    }

                    // Handling error while executing the request.
                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
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

        // Returning an empty list of categories in case of an error.
        return genresForErrorResponse
    }
}
