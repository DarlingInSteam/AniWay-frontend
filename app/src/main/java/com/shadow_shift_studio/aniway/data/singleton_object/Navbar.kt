package com.shadow_shift_studio.aniway.data.singleton_object

import androidx.compose.runtime.mutableStateOf

object Navbar {
    private val isNavbarVisible = mutableStateOf(true)

    fun setNavbarVisible(index: Boolean) {
        isNavbarVisible.value = index
    }

    fun getNavbarVisible(): Boolean {
        return isNavbarVisible.value
    }
}