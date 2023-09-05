package com.shadow_shift_studio.aniway.model.entity

import com.shadow_shift_studio.aniway.model.enum.AgeRating
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import com.shadow_shift_studio.aniway.model.enum.TitleStatus
import com.shadow_shift_studio.aniway.model.enum.TitleType

data class Title(
    var id : Long?,
    var name : String?,
    var originalName : String?,
    var year : Int?,
    var description : String?,
    var status : TitleStatus?,
    var type : TitleType?,
    var views : Int?,
    var rating: Double?,
    var userRating: Int?,
    var backgroundUrl : String?,
    var categories: List<Category>?,
    var genres: List<Genre>?,
    var ageRating: AgeRating?,
    var readingStatus: ReadingStatus?,
    var ratings: Map<Int, Int>?
)
