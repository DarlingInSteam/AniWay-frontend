package com.shadow_shift_studio.aniway.domain.repository

import android.content.Context

/**
 * Интерфейс `LoginRepository` определяет метод для аутентификации пользователя.
 */
interface ILoginRepository {

    /**
     * Аутентифицирует пользователя с использованием имени пользователя и пароля.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя для входа.
     * @param password Пароль пользователя для входа.
     * @return `true`, если аутентификация прошла успешно, иначе `false`.
     */
    suspend fun loginUser(context: Context, username: String, password: String): Boolean
}
