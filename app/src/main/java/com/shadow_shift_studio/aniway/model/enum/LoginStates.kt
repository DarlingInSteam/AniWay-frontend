package com.shadow_shift_studio.aniway.model.enum

/**
 * The `LoginStates` enumeration represents the possible states of username verification at login.
 *
 * @property value The numeric value corresponding to the state.
 * @constructor Creates an instance of the `LoginStates` enumeration.
 */
enum class LoginStates(val value: Int) {
    /**
     * The username has an invalid length.
     */
    INVALID_LENGTH(0),

    /**
     * Username contains invalid characters.
     */
    INVALID_CHARACTERS(1),

    /**
     * The username is valid.
     */
    VALID(2),

    /**
      * Username is empty.
     */
    EMPTY(3)
}
