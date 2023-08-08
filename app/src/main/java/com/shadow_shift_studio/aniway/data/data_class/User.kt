package com.shadow_shift_studio.aniway.data.data_class

import com.shadow_shift_studio.aniway.data.enum.Sex
import java.util.Date

data class User(
    val id: Long,
    val username: String,
    val sex: Sex,
    val xp: Int,
    val pass_xp: Int,
    val balance: Int,
    val createdAt: Date
)

data class Credentials(val username: String, val password: String)