package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.CommentsRequest
import com.shadow_shift_studio.aniway.data.api_request.TitleRequest
import com.shadow_shift_studio.aniway.data.api_request.UserRequest
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.CommentsUseCase
import com.shadow_shift_studio.aniway.domain.use_case.TitleUseCase
import com.shadow_shift_studio.aniway.domain.use_case.UserUseCase
import com.shadow_shift_studio.aniway.model.entity.Achievement
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {
    val userByUsernameLiveData: MutableLiveData<User> = MutableLiveData()
    val userCommentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val userAchievementsLiveData: MutableLiveData<List<Achievement>> = MutableLiveData()
    val userTitlesLiveData: MutableLiveData<List<TitlePreview>> = MutableLiveData()
    val tabTitles = listOf("Любимое", "Ачивки", "Комментарии", "Баланс")
    var selectedTabIndex = mutableStateOf(0)
    var id: Long = 0
    var page = 0

    private val userUseCase: UserUseCase =
        UserUseCase(UserRequest())

    private val getUserCommentsUseCase: CommentsUseCase =
        CommentsUseCase(CommentsRequest())

    private val getUserManga: TitleUseCase =
        TitleUseCase(TitleRequest())

    suspend fun getUserByUsername() {
        viewModelScope.launch {
            val user = userUseCase.userByUsername(context, AuthorizedUser.username)
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
            val achievements = userUseCase.getAchievementsByUsername(context, AuthorizedUser.username, true)
            userAchievementsLiveData.value = achievements
        }.join()
    }

    suspend fun getUserManga(readingStatus: ReadingStatus) {
        viewModelScope.launch {
            val titles = getUserManga.getFavouritesTitles(context, AuthorizedUser.username, readingStatus)
            userTitlesLiveData.value = titles
        }.join()
    }
}