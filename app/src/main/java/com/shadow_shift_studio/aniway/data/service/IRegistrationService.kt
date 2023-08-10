package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForRegistration
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * The `IRegisterService` interface defines methods for registering a user.
 */
interface IRegistrationService {
    /**
     * Registers a user using the provided credentials.
     *
     * @param credentials User credentials for registration.
     * @return A `Call` with an expected response of type `TokenResponse`.
     */
    @POST("/auth/register")
    fun register(@Body credentials: CredentialsForRegistration): Call<TokenResponse>
}

