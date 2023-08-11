package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IViewModelRepository
import com.shadow_shift_studio.aniway.model.enum.Sex

class ViewModelUseCase(private val vmRepository: IViewModelRepository) {
    suspend fun removeTrailingSpaces(str: String): String {
        return vmRepository.removeTrailingSpaces(str)
    }
}