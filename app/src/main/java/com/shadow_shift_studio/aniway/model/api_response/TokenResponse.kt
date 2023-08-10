package com.shadow_shift_studio.aniway.model.api_response

/**
 * Класс `TokenResponse` представляет ответ от сервера с токенами доступа и обновления.
 *
 * @property accessToken Токен доступа.
 * @property token Токен обновления.
 * @constructor Создает экземпляр класса `TokenResponse`.
 */
data class TokenResponse(val accessToken: String, val token: String)

