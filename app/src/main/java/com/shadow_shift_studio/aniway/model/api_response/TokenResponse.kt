package com.shadow_shift_studio.aniway.model.api_response

/**
 * The `TokenResponse` class represents the response from the server with access and update tokens.
 *
 * @property accessToken The access token.
 * @property token Update token.
 * @constructor Creates an instance of the `TokenResponse` class.
 */
data class TokenResponse(var accessToken: String, var token: String)

