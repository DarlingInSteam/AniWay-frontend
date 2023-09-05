package com.shadow_shift_studio.aniway.model.enum

enum class TitleType {
    MANHWA,
    MANHUA,
    MANGA,
    CARTOON
}

fun TitleType.toFormattedString(): String {
    return when (this) {
        TitleType.MANHWA -> "Манхва"
        TitleType.MANHUA -> "Маньхуа"
        TitleType.MANGA -> "Манга"
        TitleType.CARTOON -> "Комикс"
    }
}