package com.shadow_shift_studio.aniway.model.entity

import com.shadow_shift_studio.aniway.model.enum.TitleType

data class TitlePreview(
    var id : Long?,
    var name : String?,
    var originalName: String?,
    var type : TitleType?,
    var backgroundUrl : String?,
    var rating: Double?
)
