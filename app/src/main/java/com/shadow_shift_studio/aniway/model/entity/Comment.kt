package com.shadow_shift_studio.aniway.model.entity

import java.util.Date

data class Comment (
    var text: String?,
    var username: String?,
    var titleName: String?,
    var avatarUrl: String?,
    var date: Date?
)