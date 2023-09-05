package com.shadow_shift_studio.aniway.model.enum

enum class TitleStatus {
    ONGOING,
    FINISHED,
    FREEZED,
    ANNOUNCED
}

fun TitleStatus.toFormattedString(): String {
    return when (this) {
        TitleStatus.ONGOING -> "Онгоинг"
        TitleStatus.FINISHED -> "Закончен"
        TitleStatus.FREEZED -> "Заморожен"
        TitleStatus.ANNOUNCED -> "Анонсирован"
    }
}