package com.shadow_shift_studio.aniway.data.credentials

data class CredentialsForCreateComment(
    val author: Long,
    val title_id: Long,
    val chapter_id: Long,
    val text: String
)