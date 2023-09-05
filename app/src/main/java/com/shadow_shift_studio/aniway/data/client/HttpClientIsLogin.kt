package com.shadow_shift_studio.aniway.data.client

import android.util.Log
import com.shadow_shift_studio.aniway.data.api_request.UserAuthorizationRequest
import com.shadow_shift_studio.aniway.data.service.IAuthorizationService
import com.shadow_shift_studio.aniway.data.service.ICommentsService
import com.shadow_shift_studio.aniway.data.service.ITitleService
import com.shadow_shift_studio.aniway.data.service.ITitlesListService
import com.shadow_shift_studio.aniway.data.service.IUserService
import com.shadow_shift_studio.aniway.domain.use_case.LoginUserUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection.HTTP_FORBIDDEN as FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED as UNAUTHORIZED

/**
 * The `HttpClientIsLogin` object provides an instance of `Retrofit` configured for making HTTP requests
 * to a remote server with a Bearer token authorization header.
 */

//саша ip 192.168.0.159 192.168.0.101
//артем 192.168.0.7
object HttpClientIsLogin {
    val loginUserUseCase: LoginUserUseCase =
        LoginUserUseCase(UserAuthorizationRequest())

    var Client =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                // Adding the Bearer token authorization header to the request
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${KeyStoreManager.getAccessToken()}")
                    .build()
                chain.proceed(request) // Continue executing the request with the added header
            }
            .addInterceptor { chain ->
                val requestt = chain.request()
                var a: Boolean? = false
                val response = chain.proceed(requestt)

                if (response.code == UNAUTHORIZED || response.code == FORBIDDEN) {
                    Log.e("qwertyasdfgh", KeyStoreManager.getAccessToken().toString())

                    GlobalScope.launch {
                        a = loginUserUseCase.getRefresh()
                    }

                    if (a == true) {
                        Log.e("qwertyasdfgh", KeyStoreManager.getAccessToken().toString())

                        val newRequest = requestt.newBuilder()
                            .addHeader("Authorization", "Bearer ${KeyStoreManager.getAccessToken()}")
                            .build()

                        // Здесь возвращаем новый запрос
                        return@addInterceptor chain.proceed(newRequest)
                    }
                }

                // Здесь возвращаем оригинальный ответ
                return@addInterceptor response
            }
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.7:8080") // Base URL of the remote server (replace with the actual URL)
        .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON processing
        .client(Client)
        .build()

    // Creating a service for making requests to the remote server
    val UserService: IUserService = retrofit.create(IUserService::class.java)
    val CommentsService: ICommentsService = retrofit.create(ICommentsService::class.java)
    val CatalogService: ITitlesListService = retrofit.create(ITitlesListService::class.java)
    val TitleService: ITitleService = retrofit.create(ITitleService::class.java)
    val loginService: IAuthorizationService = retrofit.create(IAuthorizationService::class.java)
}

