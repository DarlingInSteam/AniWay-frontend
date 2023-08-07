package com.shadow_shift_studio.aniway.data.data_class

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.Cipher
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class KeyStore(private val context: Context) {
    private val keyStore: java.security.KeyStore = java.security.KeyStore.getInstance("AndroidKeyStore")

    init {
        keyStore.load(null)
    }

    fun createSecretKey(keyAlias: String) {
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setRandomizedEncryptionRequired(false)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }

    fun encryptData(keyAlias: String, dataToEncrypt: ByteArray): ByteArray {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val iv = generateRandomIv() // Генерация случайного IV
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))

        val encryptedData = cipher.doFinal(dataToEncrypt)

        // Конкатенируйте IV и зашифрованные данные для передачи
        return iv + encryptedData
    }

    fun decryptData(keyAlias: String, encryptedData: ByteArray): ByteArray {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val iv = encryptedData.copyOfRange(0, 16) // Получите IV из начала зашифрованных данных
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        return cipher.doFinal(encryptedData, 16, encryptedData.size - 16) // Проигнорируйте первые 16 байт (IV)
    }

    private fun generateRandomIv(): ByteArray {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }
}
