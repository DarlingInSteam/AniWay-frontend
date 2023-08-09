package com.shadow_shift_studio.aniway.data.credentials

import com.shadow_shift_studio.aniway.model.enum.Sex

data class CredentialsForRegistration(val username: String, val email: String, val password: String, val sex: Sex)
