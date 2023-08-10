package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.repository.IGetUser

/**
 * The `GetUserByUsernameUseCase` class is a use case (business logic layer),
 * which provides information about a user by username.
 *
 * @constructor Creates an instance of the `GetUserByUsernameUseCase` class.
 * @param getUserByUsernameRepository A repository for retrieving user information by username.
 */
class GetUserUseCase(private val getUserByUsernameRepository: IGetUser) {

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
}
