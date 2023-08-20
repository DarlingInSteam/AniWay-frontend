package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.TitlesListRequest
import com.shadow_shift_studio.aniway.data.singleton_object.Filter
import com.shadow_shift_studio.aniway.domain.use_case.TitlesListUseCase
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import com.shadow_shift_studio.aniway.model.entity.TitlePreview
import kotlinx.coroutines.launch

class CatalogViewModel(private val context: Context) : ViewModel() {
    private val _firstVisibleItemIndex = mutableIntStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableIntStateOf(0)
    var page = Filter.page
    val catalogGenresLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    val catalogCategoriesLiveData: MutableLiveData<List<Category>> = MutableLiveData()
    val catalogTitles: MutableLiveData<List<TitlePreview>> = MutableLiveData()
    var firstVisibleItemIndex = _firstVisibleItemIndex
    var initCatalog = false


    private val getCatalog: TitlesListUseCase =
        TitlesListUseCase(TitlesListRequest())

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.intValue = index
    }

    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.intValue = offset
    }

    suspend fun getGenres() {
        viewModelScope.launch {
            val genres = getCatalog.getCatalogGenres(context)
            catalogGenresLiveData.value = genres
        }.join()
    }

    suspend fun getCategories() {
        viewModelScope.launch {
            val categories = getCatalog.getCatalogCategories(context)
            catalogCategoriesLiveData.value = categories
        }.join()
    }

    suspend fun getCatalog() {
        viewModelScope.launch {
            val titles = getCatalog.getCatalog(
                context,
                Filter.selectedGenres,
                Filter.selectedTitleStatus,
                Filter.selectedTitleType,
                Filter.selectedCategories,
                Filter.selectedAgeRatings,
                page
            )

            if (page != 0) {
                val currentTitles = catalogTitles.value ?: emptyList()
                val updatedTitles = currentTitles.toMutableList()
                updatedTitles.addAll(titles)

                catalogTitles.value = updatedTitles
            } else {
                catalogTitles.value = titles
            }
            page += 1
        }.join()
    }
}
