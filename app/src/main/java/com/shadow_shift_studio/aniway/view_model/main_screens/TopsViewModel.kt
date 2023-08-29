package com.shadow_shift_studio.aniway.view_model.main_screens

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TopsViewModel : ViewModel() {
    private val _firstVisibleItemIndex = mutableIntStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableIntStateOf(0)
    var firstVisibleItemIndex = _firstVisibleItemIndex
    val tabTitles = listOf("Новинки", "Топ месяца", "Топ недели", "Топ дня")
    var selectedTabIndex = mutableStateOf(0)

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.intValue = index
    }

    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.intValue = offset
    }
}