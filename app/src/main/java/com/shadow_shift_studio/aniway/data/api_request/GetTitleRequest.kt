package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.domain.repository.IGetTitle
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class GetTitleRequest : IGetTitle {
    override suspend fun getTitle(context: Context, id: Long): Title {
        // Create an instance of the remote service caller
        val backendService = HttpClientIsLogin.getTitle
        // Create an instance with empty fields to use in case of an error
        val titleForErrorResponse = Title(null, null, null,  null, null, null, null, null, null, null, null, null)

        try {
            // Use suspendCancellableCoroutine for handling asynchronous code
            return suspendCancellableCoroutine { continuation ->
                // Create a call to the remote service
                val call = backendService.getTitle(id)

                // Handling a successful response from the server
                call.enqueue(object : Callback<Title> {
                    override fun onResponse(call: Call<Title>, response: Response<Title>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                // Create a User object based on the received data
                                val title = responseBody
                                continuation.resume(title) // Resume the coroutine with the received user
                            } else {
                                continuation.resume(titleForErrorResponse) // Return an empty user object in case of missing data in the response
                            }
                        } else {
                            Log.e("Get User error", response.errorBody().toString())
                            continuation.resume(titleForErrorResponse) // Return an empty user object in case of an error response
                        }
                    }

                    // Handling an error while making the request
                    override fun onFailure(call: Call<Title>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(titleForErrorResponse) // Return an empty user object in case of an error
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

        return titleForErrorResponse
    }
}