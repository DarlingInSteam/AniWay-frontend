package com.shadow_shift_studio.aniway.data.credentials

import com.shadow_shift_studio.aniway.model.enum.Sex

/**
 * The `CredentialsForRegistration` class represents user credentials for the registration process.
 *
 * @property username The username of the user.
 * @property email The email of the user.
 * @property password The password of the user.
 * @property sex The gender of the user.
 * @constructor Creates an instance of the `CredentialsForRegistration` class.
 */
data class CredentialsForRegistration(val username: String, val email: String, val password: String, val sex: Sex)


