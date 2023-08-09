package com.shadow_shift_studio.aniway.model.enum

enum class LoginStates(val value: Int)
{
    INVALID_LENGTH(0),
    INVALID_CHARACTERS(1),
    VALID(2),
    EMPTY(3)
}