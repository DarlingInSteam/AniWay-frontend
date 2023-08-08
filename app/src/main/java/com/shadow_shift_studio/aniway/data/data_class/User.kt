package com.shadow_shift_studio.aniway.data.data_class

import com.shadow_shift_studio.aniway.data.enum.Sex
import java.util.Date

data class User(
    var id: Long?,
    var username: String?,
    var sex: Sex?,
    var xp: Int?,
    var pass_xp: Int?,
    var balance: Int?,
    var createdAt: Date?
)

data class CredentialsForLogin(val username: String, val password: String)
data class CredentialsForUserByUsername(val username: String)
