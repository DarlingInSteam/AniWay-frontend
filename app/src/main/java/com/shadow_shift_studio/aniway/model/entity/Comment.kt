package com.shadow_shift_studio.aniway.model.entity

import java.util.Date

/**
 * Класс `Comment` представляет комментарий пользователя.
 *
 * @property text Текст комментария.
 * @property username Имя пользователя, оставившего комментарий.
 * @property titleName Название заголовка, к которому оставлен комментарий.
 * @property avatarUrl URL аватара пользователя.
 * @property date Дата и время оставления комментария.
 * @constructor Создает экземпляр класса `Comment`.
 */
data class Comment(
    var text: String?,
    var username: String?,
    var titleName: String?,
    var avatarUrl: String?,
    var date: Date?
)
