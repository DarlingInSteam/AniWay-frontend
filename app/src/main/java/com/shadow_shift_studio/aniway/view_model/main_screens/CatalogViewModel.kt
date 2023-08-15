package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetCatalogGenresRequest
import com.shadow_shift_studio.aniway.data.api_request.GetUserRequest
import com.shadow_shift_studio.aniway.data.search_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.GetCatalogGenresUseCase
import com.shadow_shift_studio.aniway.domain.use_case.GetUserUseCase
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Genre
import kotlinx.coroutines.launch

class CatalogViewModel(private val context: Context) : ViewModel() {
    private val _firstVisibleItemIndex = mutableIntStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableIntStateOf(0)
    val catalogGenresLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    var firstVisibleItemIndex = _firstVisibleItemIndex

    private val getCatalogGenresUseCase: GetCatalogGenresUseCase =
        GetCatalogGenresUseCase(GetCatalogGenresRequest())

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.intValue = index
    }

    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.intValue = offset
    }

    suspend fun getGenres() {
        viewModelScope.launch {
            val genres = getCatalogGenresUseCase.getCatalogGenres(context)
            catalogGenresLiveData.value = genres
        }.join()
    }
}
