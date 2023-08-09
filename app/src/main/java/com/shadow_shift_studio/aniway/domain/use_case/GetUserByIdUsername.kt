package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.repository.GetUserIdUsername

/**
 * Класс `GetUserByUsernameUseCase` представляет собой use case (слой бизнес-логики),
 * который обеспечивает получение информации о пользователе по его имени пользователя.
 *
 * @constructor Создает экземпляр класса `GetUserByUsernameUseCase`.
 * @param getUserByUsernameRepository Репозиторий для получения информации о пользователе по имени пользователя.
 */
class GetUserByIdUsername(private val getUserByUsernameRepository: GetUserIdUsername) {

    /**
     * Выполняет запрос на получение информации о пользователе по его имени пользователя.
     *
     * @param context Контекст приложения.
     * @param username Имя пользователя, для которого необходимо получить информацию.
     * @return Объект типа `User`, содержащий информацию о пользователе.
     */
    suspend fun userByUsername(context: Context, username: String): User {
        return getUserByUsernameRepository.getUserByUsername(context, username)
    }

    suspend fun userById(context: Context, id: String): User {
        return getUserByUsernameRepository.getUserById(context, id)
    }
}
