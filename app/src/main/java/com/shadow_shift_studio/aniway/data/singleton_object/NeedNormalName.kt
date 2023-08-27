package com.shadow_shift_studio.aniway.data.singleton_object

import androidx.compose.runtime.mutableStateOf
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.CommentsViewModel

object NeedNormalName {
    var text: String = ""
    val expanded =  mutableStateOf(false)
    var reportsBottomSheetVisible = mutableStateOf(false)
    var needCopyText = mutableStateOf(false)
    var snackbarVisible = mutableStateOf(false)
}