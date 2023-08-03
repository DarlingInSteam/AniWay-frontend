package com.shadow_shift_studio.aniway.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _firstVisibleItemIndex = mutableStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableStateOf(0)
    var firstVisibleItemIndex = _firstVisibleItemIndex

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.value = index
    }


    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.value = offset
    }
}