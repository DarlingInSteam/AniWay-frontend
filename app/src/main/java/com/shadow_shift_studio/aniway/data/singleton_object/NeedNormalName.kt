package com.shadow_shift_studio.aniway.data.singleton_object

import androidx.compose.runtime.mutableStateOf

object NeedNormalName {
    var text: String = ""
    val IsCommentMenuVisible =  mutableStateOf(false)
    val IsEditCommentVisible =  mutableStateOf(false)
    var reportsBottomSheetVisible = mutableStateOf(false)
    var needCopyText = mutableStateOf(false)
    var snackbarVisible = mutableStateOf(false)
}