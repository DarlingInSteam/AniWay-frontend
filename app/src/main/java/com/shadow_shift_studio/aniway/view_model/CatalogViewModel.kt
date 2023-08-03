package com.shadow_shift_studio.aniway.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.constraintlayout.solver.state.State
import androidx.lifecycle.ViewModel

class CatalogViewModel : ViewModel() {
    private val _firstVisibleItemIndex = mutableStateOf(0)
    private val _firstVisibleItemScrollOffset = mutableStateOf(0)
    var firstVisibleItemIndex = _firstVisibleItemIndex
    var firstVisibleItemScrollOffset = _firstVisibleItemScrollOffset

    fun setFirstVisibleItemIndex(index: Int) {
        _firstVisibleItemIndex.value = index
    }

    fun getFirstVisibleItemIndex() : Int {
        return _firstVisibleItemIndex.value
    }

    fun setFirstVisibleItemScrollOffset(offset: Int) {
        _firstVisibleItemScrollOffset.value = offset
    }

    fun getFirstVisibleItemScrollOffset() : Int {
        return _firstVisibleItemScrollOffset.value
    }
}
