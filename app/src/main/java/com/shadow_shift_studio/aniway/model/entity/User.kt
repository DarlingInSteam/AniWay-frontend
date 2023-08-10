package com.shadow_shift_studio.aniway.model.entity

import com.shadow_shift_studio.aniway.model.enum.Sex
import java.util.Date

/**
 * Класс `User` представляет информацию о пользователе.
 *
 * @property id Уникальный идентификатор пользователя.
 * @property username Имя пользователя.
 * @property sex Пол пользователя.
 * @property xp Опыт пользователя.
 * @property pass_xp Опыт для следующего уровня.
 * @property balance Баланс пользователя.
 * @property createdAt Дата и время создания аккаунта пользователя.
 * @property chapters Количество глав пользователя.
 * @property likes Количество лайков пользователя.
 * @property commentsCount Количество комментариев пользователя.
 * @property avatarUrl URL аватара пользователя.
 * @property backgroundUrl URL фона пользователя.
 * @constructor Создает экземпляр класса `User`.
 */
data class User(
    var id: Long?,
    var username: String?,
    var sex: Sex?,
    var xp: Int?,
    var pass_xp: Int?,
    var balance: Int?,
    var createdAt: Date?,
    var chapters: Int?,
    var likes: Int?,
    var commentsCount: Int?,
    var avatarUrl: String?,
    var backgroundUrl: String?
)


