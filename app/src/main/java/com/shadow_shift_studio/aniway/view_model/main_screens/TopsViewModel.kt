package com.shadow_shift_studio.aniway.view_model.main_screens

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class TopsViewModel : ViewModel() {
    private val _firstVisibleItemIndex = mutableIntStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableIntStateOf(0)
    var firstVisibleItemIndex = _firstVisibleItemIndex

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.intValue = index
    }

    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.intValue = offset
    }
}