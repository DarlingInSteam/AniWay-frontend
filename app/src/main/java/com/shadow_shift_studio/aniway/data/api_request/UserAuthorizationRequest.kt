package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientNotLogin
import com.shadow_shift_studio.aniway.data.client.KeyStoreManager
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForAuthorization
import com.shadow_shift_studio.aniway.data.credentials.CredentialsForRefreshToken
import com.shadow_shift_studio.aniway.domain.repository.ILoginRepository
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume


/**
 * The `UserAuthentication` class provides a method to authenticate a user using their username
 * and password. This class implements the `LoginRepository` interface.
 *
 * @constructor Creates an instance of the `UserAuthentication` class.
 */
class UserAuthorizationRequest : ILoginRepository {

    /**
     * Authenticates a user using their username and password.
     *
     * @param context The application context.
     * @param username The user's username for login.
     * @param password The user's password for login.
     * @return `true` if authentication is successful, otherwise `false`.
     */
    override suspend fun loginUser(context: Context, username: String, password: String): Boolean {
        // Create an instance of the remote service caller
        val backendService = HttpClientNotLogin.loginService
        // Create an instance with user credentials
        val credentials = CredentialsForAuthorization(username, password)

        // Use suspendCancellableCoroutine for handling asynchronous code
        return suspendCancellableCoroutine { continuation ->
            // Create a call to the remote service
            val call = backendService.login(credentials)

            // Handling a successful response from the server
            call.enqueue(object : retrofit2.Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.e("123456", responseBody.accessToken)

                            KeyStoreManager.putAccessToken(responseBody.accessToken)
                            KeyStoreManager.putUpdateToken(responseBody.token)
                            KeyStoreManager.putIsLogin()
                            KeyStoreManager.putUsername(username)

                            continuation.resume(true) // Resume the coroutine with a successful result
                        } else {
                            continuation.resume(false) // Return `false` in case of missing data in the response
                        }
                    } else {
                        Log.e("Authorization error", response.errorBody().toString())
                        continuation.resume(false) // Return `false` in case of an error response
                    }
                }

                // Handling an error while making the request
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false) // Return `false` in case of an error
                }
            })

            // Cancel the call when the coroutine is canceled
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun getRefresh(): Boolean {
        // Create an instance of the remote service caller
        val backendService = HttpClientNotLogin.loginService
        // Create an instance with user credentials
        val credentials = CredentialsForRefreshToken(KeyStoreManager.getUpdateToken().toString())
        Log.e("qwewqe", KeyStoreManager.getUpdateToken().toString())
        // Use suspendCancellableCoroutine for handling asynchronous code
        return suspendCancellableCoroutine { continuation ->
            // Create a call to the remote service
            val call = backendService.getRefresh(credentials)

            // Handling a successful response from the server
            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.e("123456", responseBody.accessToken)

                            KeyStoreManager.putAccessToken(responseBody.accessToken)
                            KeyStoreManager.putUpdateToken(responseBody.token)

                            continuation.resume(true) // Resume the coroutine with a successful result
                        } else {
                            Log.e("123456", "ыыыыыыыыыыыыыыы")

                            continuation.resume(false) // Return `false` in case of missing data in the response
                        }
                    } else {
                        Log.e("Authorization error", response.errorBody().toString())
                        continuation.resume(false) // Return `false` in case of an error response
                    }
                }

                // Handling an error while making the request
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false) // Return `false` in case of an error
                }
            })

            // Cancel the call when the coroutine is canceled
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}

