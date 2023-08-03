package com.shadow_shift_studio.aniway.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BottomNavBarViewModel : ViewModel() {
    private val firstVisibleItemIndex = mutableStateOf(true)

    fun setFirstVisibleItemIndex(index: Boolean) {
        firstVisibleItemIndex.value = index
    }

    fun getFirstVisibleItemIndex(): Boolean {
        return firstVisibleItemIndex.value
    }
}