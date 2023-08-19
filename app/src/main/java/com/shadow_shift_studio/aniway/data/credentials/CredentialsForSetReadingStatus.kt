package com.shadow_shift_studio.aniway.data.credentials

import com.shadow_shift_studio.aniway.model.enum.ReadingStatus

data class CredentialsForSetReadingStatus(val username: String, val titleId: Long, val status: ReadingStatus)
