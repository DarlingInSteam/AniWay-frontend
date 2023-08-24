package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IUserRepository
import com.shadow_shift_studio.aniway.model.entity.Achievement
import com.shadow_shift_studio.aniway.model.entity.Badge
import com.shadow_shift_studio.aniway.model.entity.User

/**
 * The `GetUserByUsernameUseCase` class is a use case (business logic layer),
 * which provides information about a user by username.
 *
 * @constructor Creates an instance of the `GetUserByUsernameUseCase` class.
 * @param getUserByUsernameRepository A repository for retrieving user information by username.
 */
class UserUseCase(private val getUserByUsernameRepository: IUserRepository) {

    /**
     * Performs a query to get user information about a user by username.
     *
     * @param context Application context.
     * @param username The username for which information is to be retrieved.
     * @return An object of type `User` containing information about the user.
     */
    suspend fun userByUsername(context: Context, username: String): User {
        return getUserByUsernameRepository.getUserByUsername(context, username)
    }


    /**
     * Executes a query to retrieve user information by user Id.
     *
     * @param context Application context.
     * @param id Id for which information is to be retrieved.
     * @return An object of type `User` containing information about the user.
     */
    suspend fun userById(context: Context, id: String): User {
        return getUserByUsernameRepository.getUserById(context, id)
    }

    /**
     * Retrieves a list of user achievements by username and received status.
     *
     * @param context The application context.
     * @param username The username of the user for whom you want to retrieve achievements.
     * @param received The received status indicating whether to retrieve received or pending achievements.
     * @return A list of user achievements.
     */
    suspend fun getAchievementsByUsername(context: Context, username: String, received: Boolean): List<Achievement> {
        return getUserByUsernameRepository.getAchievementByUsername(context, username, received)
    }

    suspend fun getUserBadges(context: Context, username: String): List<Badge> {
        return getUserByUsernameRepository.getUserBadges(context, username)
    }

    suspend fun setUserBadge(context: Context, username: String, badgeId: Long): String {
        return getUserByUsernameRepository.setUserBadge(context, username, badgeId)
    }
}
