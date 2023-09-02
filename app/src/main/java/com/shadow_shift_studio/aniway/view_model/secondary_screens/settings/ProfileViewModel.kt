package com.shadow_shift_studio.aniway.view_model.secondary_screens.settings

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.UserRequest
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.UserUseCase
import com.shadow_shift_studio.aniway.model.entity.Badge
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context): ViewModel() {
    val userBadges: MutableLiveData<List<Badge>> = MutableLiveData()

    private val userUseCase: UserUseCase =
        UserUseCase(UserRequest())

    suspend fun userBadges() {
        viewModelScope.launch {
            val badges = userUseCase.getUserBadges(context, AuthorizedUser.username)
            userBadges.value = badges
        }.join()
    }


}