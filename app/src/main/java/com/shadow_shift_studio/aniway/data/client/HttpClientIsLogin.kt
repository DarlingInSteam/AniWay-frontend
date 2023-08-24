package com.shadow_shift_studio.aniway.data.client

import com.shadow_shift_studio.aniway.data.service.ICommentsService
import com.shadow_shift_studio.aniway.data.service.ITitleService
import com.shadow_shift_studio.aniway.data.service.ITitlesListService
import com.shadow_shift_studio.aniway.data.service.IUserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The `HttpClientIsLogin` object provides an instance of `Retrofit` configured for making HTTP requests
 * to a remote server with a Bearer token authorization header.
 */

//саша ip 192.168.0.159 192.168.0.101
//артем 192.168.0.7
object HttpClientIsLogin {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.7:8080") // Base URL of the remote server (replace with the actual URL)
        .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON processing
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            // Adding the Bearer token authorization header to the request
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${KeyStoreManager.getDecryptAccessKey("1")}")
                .build()
            chain.proceed(request) // Continue executing the request with the added header
        }.build())
        .build()

    // Creating a service for making requests to the remote server
    val UserService: IUserService = retrofit.create(IUserService::class.java)
    val CommentsService: ICommentsService = retrofit.create(ICommentsService::class.java)
    val CatalogService: ITitlesListService = retrofit.create(ITitlesListService::class.java)
    val TitleService: ITitleService = retrofit.create(ITitleService::class.java)
}