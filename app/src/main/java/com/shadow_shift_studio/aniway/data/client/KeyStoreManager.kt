package com.shadow_shift_studio.aniway.data.client

import android.content.Context
import com.shadow_shift_studio.aniway.data.secure_data.TokensStore

/**
 * The `KeyStoreManager` object manages access to the key store (KeyStore) and stores access and refresh tokens.
 */
object KeyStoreManager {
    var keyStore: TokensStore? = null // Key store instance
    var accessToken: ByteArray = ByteArray(16) // Access token as a byte array
    var refreshToken: ByteArray = ByteArray(16) // Refresh token as a byte array

    /**
     * Returns the instance of the key store (KeyStore).
     *
     * @param context The application context.
     * @return The instance of the key store.
     */
    fun getKeyStore(context: Context): TokensStore {
        if (keyStore == null) {
            keyStore = TokensStore(context) // Create the key store if it hasn't been created yet
        }
        return keyStore!! // Return the existing key store
    }

    /**
     * Decrypts and returns the access token using the specified key alias.
     *
     * @param keyAlias The key alias.
     * @return The decrypted access token as a string.
     */
    fun getDecryptAccessKey(keyAlias: String): String {
        return keyStore?.decryptData(keyAlias, accessToken) ?: ""
    }

    /**
     * Decrypts and returns the refresh token using the specified key alias.
     *
     * @param keyAlias The key alias.
     * @return The decrypted refresh token as a string.
     */
    fun getDecryptKey(keyAlias: String): String {
        return keyStore?.decryptData(keyAlias, refreshToken) ?: ""
    }
}


