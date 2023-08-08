package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.data.client.UserForSearch
import com.shadow_shift_studio.aniway.data.data_class.CredentialsForUserByUsername
import com.shadow_shift_studio.aniway.data.data_class.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserByUsernameService {
    //Путь к API методу
    @GET("/user/username/{username}")
    //TokenResponse - класс, который соответствует ответу сервера
    fun userByUsername(@Path("username") username: String): Call<User>
}
