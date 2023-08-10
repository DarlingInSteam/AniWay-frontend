package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetUser
import com.shadow_shift_studio.aniway.data.api_request.GetUserComments
import com.shadow_shift_studio.aniway.data.client.AuthorizedUser
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.use_case.GetUserByIdUsernameUseCase
import com.shadow_shift_studio.aniway.domain.use_case.GetUserCommentsUseCase
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {
    val userByUsernameLiveData: MutableLiveData<User> = MutableLiveData()
    val userCommentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()


    private val getUserByUsernameUseCase: GetUserByIdUsernameUseCase =
        GetUserByIdUsernameUseCase(GetUser())

    private val getUserCommentsUseCase: GetUserCommentsUseCase =
        GetUserCommentsUseCase(GetUserComments())

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
}