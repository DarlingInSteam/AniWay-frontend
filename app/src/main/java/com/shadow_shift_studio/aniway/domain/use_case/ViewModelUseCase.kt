package com.shadow_shift_studio.aniway.domain.use_case

import com.shadow_shift_studio.aniway.domain.repository.IViewModelRepository

class ViewModelUseCase(): IViewModelRepository{
     override fun removeTrailingSpaces(input: String): String {
        return input.trimEnd { it.isWhitespace() }
    }
}