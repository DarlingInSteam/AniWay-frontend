package com.shadow_shift_studio.aniway.domain.use_case

import android.content.Context
import com.shadow_shift_studio.aniway.domain.repository.IViewModelRepository
import com.shadow_shift_studio.aniway.model.enum.Sex

class ViewModelUseCase(): IViewModelRepository{
     override fun removeTrailingSpaces(input: String): String {
        return input.trimEnd { it.isWhitespace() }
    }
}