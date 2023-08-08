package com.shadow_shift_studio.aniway.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetUser
import com.shadow_shift_studio.aniway.data.api_request.UserAuthentication
import com.shadow_shift_studio.aniway.data.client.UserIsAuth
import com.shadow_shift_studio.aniway.data.data_class.User
import com.shadow_shift_studio.aniway.domain.use_case.GetUserByUsernameUseCase
import com.shadow_shift_studio.aniway.domain.use_case.LoginUserUseCase
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {
    val userByUsernameLiveData: MutableLiveData<User> = MutableLiveData()

    private val getUserByUsernameUseCase: GetUserByUsernameUseCase =
        GetUserByUsernameUseCase(GetUser())

    suspend fun getUserByUsername() {
        viewModelScope.launch {
            val user = getUserByUsernameUseCase.execute(context, UserIsAuth.username)
            userByUsernameLiveData.value = user
        }.join()
    }
}