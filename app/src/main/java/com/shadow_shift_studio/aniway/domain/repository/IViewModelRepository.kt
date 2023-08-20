package com.shadow_shift_studio.aniway.domain.repository

interface IViewModelRepository {
    fun removeTrailingSpaces(input: String): String
}