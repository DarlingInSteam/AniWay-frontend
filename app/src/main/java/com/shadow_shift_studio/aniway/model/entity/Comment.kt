package com.shadow_shift_studio.aniway.model.entity

import java.util.Date

/**
 * The `Comment` class represents the user's comment.
 *
 * @property text The text of the comment.
 * @property username The name of the user who left the comment.
 * @property titleName The name of the title to which the comment was left.
 * @property avatarUrl The URL of the user's avatar.
 * @property createdAt The date and time the comment was left.
 * @constructor Creates an instance of the `Comment` class.
 */
data class Comment(
    var text: String?,
    var username: String?,
    var titleName: String?,
    var avatarUrl: String?,
    var createdAt: Date?
)
