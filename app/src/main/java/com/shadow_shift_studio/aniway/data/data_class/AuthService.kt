package com.shadow_shift_studio.aniway.data.data_class

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import java.util.Date

enum class Sex {
    MALE,
    FEMALE
}

data class TokenResponse(val accessToken: String, val token: String)
data class Credentials(val username: String, val password: String)
data class User(
    val id: Long,
    val username: String,
    val sex: Sex,
    val xp: Int,
    val pass_xp: Int,
    val balance: Int,
    val createdAt: Date
)
interface BackendService {
    @POST("/auth/login") // Здесь укажите путь к API методу для аутентификации
    fun login(@Body credentials: Credentials): Call<TokenResponse> // Замените YourResponseModel на класс, который соответствует ответу от вашего бэкенда
}
