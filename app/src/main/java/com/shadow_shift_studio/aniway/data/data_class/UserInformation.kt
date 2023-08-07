package com.shadow_shift_studio.aniway.data.data_class

enum class Gender {
    MALE,
    FEMALE
}

data class UserInformation(
    var password: String,
    var username: String,
    var email: String,
    var gender: Gender,
    var xp: Int,
)
