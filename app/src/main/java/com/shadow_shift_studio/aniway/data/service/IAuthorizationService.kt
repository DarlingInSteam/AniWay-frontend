package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.credentials.CredentialsForAuthorization
import com.shadow_shift_studio.aniway.model.api_response.TokenResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

/**
 * The `LoginService` interface defines methods for making HTTP requests related to authentication.
 */
interface IAuthorizationService{

    /**
     * Makes a POST request to the `/auth/login` endpoint to authenticate the user.
     *
     * @param credentials An object containing the user's credentials.
     * @return An object of type `Call<TokenResponse>` representing the request result.
     */
    @POST("/auth/login")
    fun login(@Body credentials: CredentialsForAuthorization): Call<TokenResponse>
}


