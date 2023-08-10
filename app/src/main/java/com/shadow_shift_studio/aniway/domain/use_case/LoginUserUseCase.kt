package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.ILoginRepository

/**
 * Класс `LoginUserUseCase` представляет собой use case (слой бизнес-логики),
 * который обеспечивает аутентификацию пользователя с использованием имени и пароля.
 *
 * @constructor Создает экземпляр класса `LoginUserUseCase`.
 * @param userRepository Репозиторий для аутентификации пользователя.
 */
class LoginUserUseCase(private val userRepository: ILoginRepository) {

    /**
     * Выполняет аутентификацию пользователя с использованием имени и пароля.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя для входа.
     * @param password Пароль пользователя для входа.
     * @return `true`, если аутентификация прошла успешно, иначе `false`.
     */
    suspend fun execute(context: Context, username: String, password: String): Boolean {
        return userRepository.loginUser(context, username, password)
    }
}
