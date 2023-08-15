package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetCatalogCategories
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class GetCatalogCategoriesRequest : IGetCatalogCategories {
    override suspend fun getCatalogCategories(context: Context): List<Category> {
        val backendService = HttpClientIsLogin.getCatalogCategories

        // An empty list of comments for potential error handling.
        val genresForErrorResponse = listOf<Category>()

        try {
            // Use a coroutine for asynchronous fetching of comments.
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
                            Log.e("Comments get error", response.errorBody().toString())
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

        // Returning an empty list of comments in case of an error.
        return genresForErrorResponse
    }
}