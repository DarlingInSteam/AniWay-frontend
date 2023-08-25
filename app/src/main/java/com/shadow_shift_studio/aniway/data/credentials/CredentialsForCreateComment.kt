package com.shadow_shift_studio.aniway.data.credentials

data class CredentialsForCreateComment(
    val username: String,
    val title_id: Long,
    val chapter_id: Long,
    val text: String
)