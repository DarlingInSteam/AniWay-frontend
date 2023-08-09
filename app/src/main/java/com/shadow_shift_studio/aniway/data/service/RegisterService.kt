package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForRegistration
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("/auth/register")
    fun register(@Body credentials: CredentialsForRegistration): Call<TokenResponse>
}