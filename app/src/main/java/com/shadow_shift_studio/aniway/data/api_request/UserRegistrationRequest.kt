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

//задание
//сделать регистрацию с обращением к серверу и получением двух токенов
//запомнить два токена для дальнейшего использования в запросах
class UserRegistrationRequest: IRegistrationRepository {
    override suspend fun registerUser(context: Context, username: String, email: String, password: String, sex: Sex): Boolean {

        val backendService = HttpClientNotLogin.registrationService
        val credentials = CredentialsForRegistration(username, email, password, sex)

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.register(credentials)

            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    } else {
                        Log.e("Ошибка регистрации", response.errorBody().toString())
                        continuation.resume(false)
                    }
                }
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Ошибка сети", t.message ?: "Ошибка подключения HTTP-клиента")
                    continuation.resume(false)
                }
            })
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}
