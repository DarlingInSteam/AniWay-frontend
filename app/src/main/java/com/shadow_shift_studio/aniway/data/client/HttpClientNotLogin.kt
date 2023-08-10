package com.shadow_shift_studio.aniway.data.client

import com.shadow_shift_studio.aniway.data.service.ILoginService
import com.shadow_shift_studio.aniway.data.service.IRegistrationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The `HttpClientNotLogin` object provides an instance of `Retrofit` configured for making HTTP requests
 * to a remote server without authentication.
 */
object HttpClientNotLogin {
    private val retrofit: Retrofit = Retrofit.Builder()
        // Replace with your computer's IP address
        .baseUrl("http://192.168.0.7:8080") // Base URL of the remote server
        .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON processing
        .build()

    // Creating services for making requests
    val loginService: ILoginService = retrofit.create(ILoginService::class.java)
    val registrationService: IRegistrationService = retrofit.create(IRegistrationService::class.java)
}

