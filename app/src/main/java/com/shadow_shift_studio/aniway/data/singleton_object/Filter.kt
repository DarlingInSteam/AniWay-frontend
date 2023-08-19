package com.shadow_shift_studio.aniway.data.singleton_object

import androidx.compose.runtime.mutableStateListOf
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre
import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleSorting
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType

object Filter {
    val selectedCategories = mutableStateListOf<String>()
    val selectedGenres = mutableStateListOf<String>()
    val selectedAgeRatings = mutableStateListOf<AgeRating>()
    val selectedTitleType = mutableStateListOf<TitleType>()
    val selectedTitleStatus = mutableStateListOf<TitleStatus>()
    val selectedSorting = mutableStateListOf<TitleSorting>()
    var page = 0

    fun resetAllSelected() {
        selectedCategories.clear()
        selectedGenres.clear()
        selectedAgeRatings.clear()
        selectedTitleType.clear()
        selectedTitleStatus.clear()
    }
}