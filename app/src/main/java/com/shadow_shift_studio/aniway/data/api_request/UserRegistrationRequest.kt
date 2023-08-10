package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientNotLogin
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForRegistration
import com.shadow_shift_studio.aniway.domain.repository.IRegistrationRepository
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import com.shadow_shift_studio.aniway.model.enum.Sex
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * The `UserRegistrationRequest` class implements the `IRegistrationRepository` interface and provides
 * a method for user registration.
 *
 * @constructor Creates an instance of the `UserRegistrationRequest` class.
 */
class UserRegistrationRequest : IRegistrationRepository {

    /**
     * Registers a user with the specified data.
     *
     * @param context The application context.
     * @param username The user's username.
     * @param email The user's email address.
     * @param password The user's password.
     * @param sex The user's gender.
     * @return `true` if the registration is successful, otherwise `false`.
     */
    override suspend fun registerUser(context: Context, username: String, email: String, password: String, sex: Sex): Boolean {

        // Initialize an HTTP client for user registration.
        val backendService = HttpClientNotLogin.registrationService

        // Create an object with user credentials.
        val credentials = CredentialsForRegistration(username, email, password, sex)

        // Use a coroutine for asynchronous registration.
        return suspendCancellableCoroutine { continuation ->
            val call = backendService.register(credentials)

            // Handling a successful response from the server.
            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(true) // Registration successful.
                        } else {
                            continuation.resume(false) // Error in the server response.
                        }
                    } else {
                        Log.e("Registration error", response.errorBody().toString())
                        continuation.resume(false) // Server response error.
                    }
                }

                // Handling an error while making the request.
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false) // Connection error.
                }
            })

            // Cancel the request when the coroutine is canceled.
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}


