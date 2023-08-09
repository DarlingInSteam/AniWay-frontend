package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.data.data_class.User
import com.shadow_shift_studio.aniway.domain.repository.UserByUsernameRepository

/**
 * Класс `GetUserByUsernameUseCase` представляет собой use case (слой бизнес-логики),
 * который обеспечивает получение информации о пользователе по его имени пользователя.
 *
 * @constructor Создает экземпляр класса `GetUserByUsernameUseCase`.
 * @param getUserByUsernameRepository Репозиторий для получения информации о пользователе по имени пользователя.
 */
class GetUserByUsernameUseCase(private val getUserByUsernameRepository: UserByUsernameRepository) {

    /**
     * Выполняет запрос на получение информации о пользователе по его имени пользователя.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя, для которого необходимо получить информацию.
     * @return Объект типа `User`, содержащий информацию о пользователе.
     */
    suspend fun execute(context: Context, username: String): User {
        return getUserByUsernameRepository.getUserByUsername(context, username)
    }
}
