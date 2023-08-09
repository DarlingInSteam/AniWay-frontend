package com.shadow_shift_studio.aniway.data.service

import com.shadow_shift_studio.aniway.model.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Интерфейс `UserByUsernameService` определяет метод для выполнения HTTP-запроса к получению информации о пользователе
 * по его имени пользователя.
 */
interface UserByIdUsername {

    /**
     * Выполняет GET-запрос на эндпоинт `/user/username/{username}` для получения информации о пользователе
     * по его имени пользователя.
     *
     * @param username Имя пользователя, для которого необходимо получить информацию.
     * @return Объект типа `Call<User>`, представляющий результат запроса.
     */
    @GET("/user/username/{username}")
    fun userByUsername(@Path("username") username: String): Call<User>

    @GET("/user/username/{id}")
    fun userById(@Path("id") id: String): Call<User>
}

