package com.shadow_shift_studio.aniway.data.singleton_object

import com.shadow_shift_studio.aniway.model.entity.Badge
import com.shadow_shift_studio.aniway.model.entity.User

object AuthorizedUser {
    var username = ""
    var id: Long = 0
    var user: User? = null
    var userBadges = listOf <Badge>()
}