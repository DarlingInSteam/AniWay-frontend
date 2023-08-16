package com.shadow_shift_studio.aniway.data.singleton_object

import androidx.compose.runtime.mutableStateListOf
import com.shadow_shift_studio.aniway.model.entity.Category
import com.shadow_shift_studio.aniway.model.entity.Genre

object Filter {
    val selectedCategories =  mutableStateListOf<Category>()
    val selectedGenres = mutableStateListOf<Genre>()
}