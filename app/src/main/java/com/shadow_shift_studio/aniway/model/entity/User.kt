package com.shadow_shift_studio.aniway.model.entity

import com.shadow_shift_studio.aniway.model.enum.Sex
import java.util.Date

data class User(
    var id: Long?,
    var username: String?,
    var sex: Sex?,
    var xp: Int?,
    var pass_xp: Int?,
    var balance: Int?,
    var createdAt: Date?,
    var chapters: Int?,
    var likes: Int?,
    var commentsCount: Int?,
    var avatarUrl: String?,
    var backgroundUrl: String?,
)

