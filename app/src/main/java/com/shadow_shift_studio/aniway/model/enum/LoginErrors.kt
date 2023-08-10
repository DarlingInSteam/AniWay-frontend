package com.shadow_shift_studio.aniway.model.enum

/**
 * Перечисление `LoginStates` представляет возможные состояния проверки имени пользователя при входе в систему.
 *
 * @property value Числовое значение, соответствующее состоянию.
 * @constructor Создает экземпляр перечисления `LoginStates`.
 */
enum class LoginStates(val value: Int) {
    /**
     * Имя пользователя имеет недопустимую длину.
     */
    INVALID_LENGTH(0),

    /**
     * Имя пользователя содержит недопустимые символы.
     */
    INVALID_CHARACTERS(1),

    /**
     * Имя пользователя является допустимым.
     */
    VALID(2),

    /**
     * Имя пользователя пустое.
     */
    EMPTY(3)
}
