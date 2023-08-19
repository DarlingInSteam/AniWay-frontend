package com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetTitleRequest
import com.shadow_shift_studio.aniway.domain.use_case.GetTitleUseCase
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import kotlinx.coroutines.launch

class MangaPageViewModel(private val context: Context) : ViewModel() {
    var id = mutableLongStateOf(0)
    val titleLiveData: MutableLiveData<Title> = MutableLiveData()

    private val getTitle: GetTitleUseCase =
        GetTitleUseCase(GetTitleRequest())

    suspend fun getTitle() {
        viewModelScope.launch {
            val title = getTitle.getTitle(context, id.longValue)
            titleLiveData.value = title
        }.join()
    }
}