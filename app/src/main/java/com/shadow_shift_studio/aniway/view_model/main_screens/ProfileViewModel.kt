package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetAchievementsRequest
import com.shadow_shift_studio.aniway.data.api_request.GetUserRequest
import com.shadow_shift_studio.aniway.data.api_request.GetUserCommentsRequest
import com.shadow_shift_studio.aniway.data.search_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.GetAchievementsUseCase
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.use_case.GetUserUseCase
import com.shadow_shift_studio.aniway.domain.use_case.GetUserCommentsUseCase
import com.shadow_shift_studio.aniway.model.entity.Achievement
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {
    val userByUsernameLiveData: MutableLiveData<User> = MutableLiveData()
    val userCommentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val userAchievementsLiveData: MutableLiveData<List<Achievement>> = MutableLiveData()


    private val getUserByUsernameUseCase: GetUserUseCase =
        GetUserUseCase(GetUserRequest())

    private val getUserCommentsUseCase: GetUserCommentsUseCase =
        GetUserCommentsUseCase(GetUserCommentsRequest())

    private val getAchievementsUseCase: GetAchievementsUseCase =
        GetAchievementsUseCase(GetAchievementsRequest())

    suspend fun getUserByUsername() {
        viewModelScope.launch {
            val user = getUserByUsernameUseCase.userByUsername(context, AuthorizedUser.username)
            userByUsernameLiveData.value = user
            AuthorizedUser.user = user
        }.join()
    }

    suspend fun getUserComments() {
        viewModelScope.launch {
            val comments = getUserCommentsUseCase.getUserCommentsByUsername(context, AuthorizedUser.username)
            userCommentsLiveData.value = comments
        }.join()
    }

    suspend fun getAchievement() {
        viewModelScope.launch {
            val achievements = getAchievementsUseCase.getAchievementsByUsername(context, AuthorizedUser.username, true)
            userAchievementsLiveData.value = achievements
        }.join()
    }
}