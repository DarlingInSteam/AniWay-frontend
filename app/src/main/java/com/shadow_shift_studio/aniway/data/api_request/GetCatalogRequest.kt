package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetTitlesListRepository
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Repository implementation for fetching titles in the catalog based on specified parameters.
 *
 * This repository utilizes an HTTP client to communicate with the backend service and retrieve catalog titles.
 *
 * @constructor Creates an instance of GetCatalogRequest.
 */
class GetCatalogRequest : IGetTitlesListRepository {

    /**
     * Asynchronously retrieves a list of title previews from the catalog based on specified filters.
     *
     * @param context The application context.
     * @param genres A list of genre names for filtering titles.
     * @param statuses A list of title statuses for filtering titles.
     * @param types A list of title types for filtering titles.
     * @param categories A list of category names for filtering titles.
     * @param ageRatings A list of age ratings for filtering titles.
     * @param page The page number of titles to be retrieved.
     * @return A list of TitlePreview objects representing the filtered titles in the catalog.
     *         An empty list will be returned in case of errors or lack of titles.
     */
    override suspend fun getCatalog(
        context: Context,
        genres: List<String>,
        statuses: List<TitleStatus>,
        types: List<TitleType>,
        categories: List<String>,
        ageRatings: List<AgeRating>,
        page: Int
    ): List<TitlePreview> {

        val backendService = HttpClientIsLogin.getCatalogService

        // An empty list of title previews for potential error handling.
        val titleForErrorResponse = listOf<TitlePreview>()

        try {
            // Use a coroutine for asynchronous fetching of titles.
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.getCatalogTitles(genres, statuses, types, categories, ageRatings, page)

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
                            Log.e("Titles get error", response.errorBody().toString())
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

        // Returning an empty list of title previews in case of an error.
        return titleForErrorResponse

    }
}
