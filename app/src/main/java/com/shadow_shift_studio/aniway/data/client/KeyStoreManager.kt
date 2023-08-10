package com.shadow_shift_studio.aniway.data.client

import android.content.Context
import com.shadow_shift_studio.aniway.data.secure_data.KeyStore

/**
 * Объект `KeyStoreManager` управляет доступом к хранилищу ключей (KeyStore) и хранит токены доступа и обновления.
 */
object KeyStoreManager {
    var keyStore: KeyStore? = null // Экземпляр хранилища ключей
    var accessToken : ByteArray = ByteArray(16)  // Токен доступа
    var token : ByteArray = ByteArray(16)   // Токен обновления

    /**
     * Возвращает экземпляр хранилища ключей (KeyStore).
     *
     * @param context Контекст приложения.
     * @return Экземпляр хранилища ключей.
     */
    fun getKeyStore(context: Context): KeyStore {
        if (keyStore == null) {
            keyStore = KeyStore(context) // Создаем хранилище ключей, если оно еще не было создано
        }
        return keyStore!! // Возвращаем существующее хранилище ключей
    }

    fun getDecryptAccessKey(keyAlies: String) : String {
        return keyStore?.decryptData(keyAlies, accessToken) ?: ""
    }

    fun getDecryptKey(keyAlies: String) : String {
        return keyStore?.decryptData(keyAlies, token) ?: ""
    }
}

