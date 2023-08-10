package com.shadow_shift_studio.aniway.model.entity

import com.shadow_shift_studio.aniway.model.enum.Sex
import java.util.Date

/**
 * The `User` class represents information about the user.
 *
 * @property id The unique identifier of the user.
 * @property username Username of the user.
 * @property sex The user's gender.
 * @property xp User experience.
 * @property pass_xp Experience for the next level.
 * @property balance User balance.
 * @property createdAt The date and time the user's account was created.
 * @property chapters Number of chapters of the user.
 * @property likes Number of likes of the user.
 * @property commentsCount The number of comments the user has.
 * @property avatarUrl The URL of the user's avatar.
 * @property backgroundUrl URL of the user's background.
 * @constructor Creates an instance of the `User` class.
 */
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
    var backgroundUrl: String?
)


