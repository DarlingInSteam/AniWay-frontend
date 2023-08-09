package com.shadow_shift_studio.aniway.view_model.authentication

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.UserAuthentication
import com.shadow_shift_studio.aniway.data.client.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.LoginUserUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context): ViewModel() {
    var login: MutableState<String> = mutableStateOf("DarlingInSteam")
    var password: MutableState<String> = mutableStateOf("artem11112003")
    val loginStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val loginUserUseCase: LoginUserUseCase =
        LoginUserUseCase(UserAuthentication())

    suspend fun loginUser() {
        viewModelScope.launch {
            val status = loginUserUseCase.execute(context, login.value, password.value)
            loginStatusLiveData.value = status

            if(status) {
                AuthorizedUser.username = login.value
            }
        }.join()
    }
}