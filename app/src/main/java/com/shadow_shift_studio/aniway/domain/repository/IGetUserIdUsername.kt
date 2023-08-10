package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.User

/**
 * Интерфейс `UserByUsernameRepository` определяет метод для получения информации о пользователе по его имени пользователя.
 */
interface IGetUserIdUsername {

    /**
     * Получает информацию о пользователе по его имени пользователя.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя, для которого необходимо получить информацию.
     * @return Объект типа `User`, содержащий информацию о пользователе.
     */
    suspend fun getUserByUsername(context: Context, username: String): User
    suspend fun getUserById(context: Context, id: String): User
}
