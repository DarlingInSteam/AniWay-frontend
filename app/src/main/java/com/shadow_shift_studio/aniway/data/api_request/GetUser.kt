package com.shadow_shift_studio.aniway.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.aniway.data.client.HttpClientIsLogin
import com.shadow_shift_studio.aniway.data.client.KeyStoreManager
import com.shadow_shift_studio.aniway.data.data_class.CredentialsForUserByUsername
import com.shadow_shift_studio.aniway.data.data_class.User
import com.shadow_shift_studio.aniway.domain.repository.UserByUsernameRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume


class GetUser : UserByUsernameRepository {
    override suspend fun getUserByUsername(context: Context, username: String) : User {
        val backendService = HttpClientIsLogin.userByUsernameService
        val credentials = CredentialsForUserByUsername(username)

        var accessToken = KeyStoreManager.accessToken
        try {
            return suspendCancellableCoroutine { continuation ->

                val call = backendService.userByUsername(username)

                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                val user = User(
                                    id = responseBody.id,
                                    username = responseBody.username,
                                    sex = responseBody.sex,
                                    xp = responseBody.xp,
                                    pass_xp = responseBody.pass_xp,
                                    balance = responseBody.balance,
                                    createdAt = responseBody.createdAt
                                )
                                continuation.resume(user)
                            } else {
                                continuation.resume(User(null, null,null,null,null,null,null))
                            }
                        } else {
                            Log.e("Login Error", response.errorBody().toString())
                            continuation.resume(User(null, null,null,null,null,null,null))
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(User(null, null,null,null,null,null,null))
                    }
                })

                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("сука заебал", e.toString())
        }
        return User(null, null,null,null,null,null,null)
    }
}