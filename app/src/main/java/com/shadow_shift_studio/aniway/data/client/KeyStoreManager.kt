package com.shadow_shift_studio.aniway.data.client

import android.content.Context
import com.shadow_shift_studio.aniway.data.secure_data.KeyStore

object KeyStoreManager {
    var keyStore: KeyStore? = null
    var accessToken = ""
    var token = ""

    fun getKeyStore(context: Context): KeyStore {
        if (keyStore == null) {
            keyStore = KeyStore(context)
        }
        return keyStore!!
    }
}
