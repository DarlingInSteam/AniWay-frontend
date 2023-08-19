package com.shadow_shift_studio.aniway.view_model.main_screens

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.GetCatalogCategoriesRequest
import com.shadow_shift_studio.aniway.data.api_request.GetCatalogGenresRequest
import com.shadow_shift_studio.aniway.data.api_request.GetCatalogRequest
import com.shadow_shift_studio.aniway.data.singleton_object.Filter
import com.shadow_shift_studio.aniway.domain.use_case.GetCatalogCategoriesUseCase
import com.shadow_shift_studio.aniway.domain.use_case.GetCatalogGenresUseCase
import com.shadow_shift_studio.aniway.domain.use_case.GetCatalogUseCase
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import com.shadow_shift_studio.aniway.model.entity.Title
import kotlinx.coroutines.launch

class CatalogViewModel(private val context: Context) : ViewModel() {
    private val _firstVisibleItemIndex = mutableIntStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableIntStateOf(0)
    var page = Filter.page
    val catalogGenresLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    val catalogCategoriesLiveData: MutableLiveData<List<Category>> = MutableLiveData()
    val catalogTitles: MutableLiveData<List<Title>> = MutableLiveData()
    var firstVisibleItemIndex = _firstVisibleItemIndex
    var initCatalog = false




    private val getCatalogGenresUseCase: GetCatalogGenresUseCase =
        GetCatalogGenresUseCase(GetCatalogGenresRequest())

    private val getCatalogCategoriesUseCase: GetCatalogCategoriesUseCase =
        GetCatalogCategoriesUseCase(GetCatalogCategoriesRequest())

    private val getCatalog: GetCatalogUseCase =
        GetCatalogUseCase(GetCatalogRequest())

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

    suspend fun getCategories() {
        viewModelScope.launch {
            val categories = getCatalogCategoriesUseCase.getCatalogCategories(context)
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
