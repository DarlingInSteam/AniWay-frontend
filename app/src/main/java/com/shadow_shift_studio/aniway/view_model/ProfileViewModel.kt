package com.shadow_shift_studio.aniway.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetUser
import com.shadow_shift_studio.aniway.data.client.AuthorizedUser
import com.shadow_shift_studio.aniway.model.entity.User
import com.shadow_shift_studio.aniway.domain.use_case.GetUserByIdUsername
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {
    val userByUsernameLiveData: MutableLiveData<User> = MutableLiveData()

    private val getUserByUsernameUseCase: GetUserByIdUsername =
        GetUserByIdUsername(GetUser())

    suspend fun getUserByUsername() {
        viewModelScope.launch {
            val user = getUserByUsernameUseCase.userByUsername(context, AuthorizedUser.username)
            userByUsernameLiveData.value = user
        }.join()
    }
}