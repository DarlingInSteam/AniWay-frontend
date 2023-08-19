package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetAchievementsRequest
import com.shadow_shift_studio.aniway.data.api_request.GetUserRequest
import com.shadow_shift_studio.aniway.data.api_request.CommentsRequest
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.GetAchievementsUseCase
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.use_case.GetUserUseCase
import com.shadow_shift_studio.aniway.domain.use_case.CommentsUseCase
import com.shadow_shift_studio.aniway.model.entity.Achievement
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {
    val userByUsernameLiveData: MutableLiveData<User> = MutableLiveData()
    val userCommentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val userAchievementsLiveData: MutableLiveData<List<Achievement>> = MutableLiveData()
    var page = 0

    private val getUserByUsernameUseCase: GetUserUseCase =
        GetUserUseCase(GetUserRequest())

    private val getUserCommentsUseCase: CommentsUseCase =
        CommentsUseCase(CommentsRequest())

    private val getAchievementsUseCase: GetAchievementsUseCase =
        GetAchievementsUseCase(GetAchievementsRequest())

    suspend fun getUserByUsername() {
        viewModelScope.launch {
            val user = getUserByUsernameUseCase.userByUsername(context, AuthorizedUser.username)
            userByUsernameLiveData.value = user
            AuthorizedUser.user = user
            AuthorizedUser.id = user.id!!
        }.join()
    }

    suspend fun getUserComments() {
        viewModelScope.launch {
            val comments = getUserCommentsUseCase.getUserCommentsByUsername(context, AuthorizedUser.username, page)

            val currentComments = userCommentsLiveData.value ?: emptyList()
            val updatedTitles = currentComments.toMutableList()
            updatedTitles.addAll(comments)

            userCommentsLiveData.value = updatedTitles

            page += 1
        }.join()
    }

    suspend fun getAchievement() {
        viewModelScope.launch {
            val achievements = getAchievementsUseCase.getAchievementsByUsername(context, AuthorizedUser.username, true)
            userAchievementsLiveData.value = achievements
        }.join()
    }
}