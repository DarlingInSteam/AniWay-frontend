package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shadow_shift_studio.aniway.data.api_request.TitleRequest
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.TitleUseCase
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus

class MyViewModel(private val context: Context) : ViewModel() {
    private val _firstVisibleItemIndex = mutableIntStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableIntStateOf(0)
    val titlesLiveData: MutableLiveData<List<TitlePreview>> = MutableLiveData()
    var id: Long = 0

    var firstVisibleItemIndex = _firstVisibleItemIndex

    private val title: TitleUseCase =
        TitleUseCase(TitleRequest())

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.intValue = index
    }


    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.intValue = offset
    }

    suspend fun getTitles(readingStatus: ReadingStatus) {
        val titles = title.getFavouritesTitles(context, AuthorizedUser.username, readingStatus)
        titlesLiveData.value = titles
    }
}