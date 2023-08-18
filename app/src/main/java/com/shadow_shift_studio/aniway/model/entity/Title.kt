package com.shadow_shift_studio.aniway.model.entity

import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType

data class Title(
    var id : Long,
    var name : String,
    var originalName : String,
    var year : Int,
    var description : String,
    var status : TitleStatus,
    var type : TitleType,
    var views : Int,
    var backgroundUrl : String?,
    var comments : List<Comment>,
    var categories: List<Category>,
    var genres: List<Genre>,
    var ageRating: AgeRating
)
