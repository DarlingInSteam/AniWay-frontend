package com.shadow_shift_studio.aniway.data.secure_data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.Cipher
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * Класс KeyStore предоставляет методы для работы с Android Keystore.
 * Он позволяет создавать и управлять ключами для шифрования и дешифрования данных.
 *
 * @property context Контекст приложения, необходимый для инициализации Keystore.
 */
class KeyStore(private val context: Context) {

    private val keyStore: java.security.KeyStore =
        java.security.KeyStore.getInstance("AndroidKeyStore")

    init {
        // Инициализация KeyStore при создании экземпляра класса
        keyStore.load(null)
    }

    /**
     * Создает секретный ключ с заданным псевдонимом (алиасом), если его еще нет.
     *
     * @param keyAlias Псевдоним ключа.
     */
    fun createSecretKey(keyAlias: String) {
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
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

    /**
     * Шифрует данные с использованием заданного ключа.
     *
     * @param keyAlias Псевдоним ключа.
     * @param dataToEncrypt Данные для шифрования.
     * @return Зашифрованные данные.
     */
    fun encryptData(keyAlias: String, dataToEncrypt: ByteArray): ByteArray {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val iv = generateRandomIv() // Генерация случайного IV
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))

        val encryptedData = cipher.doFinal(dataToEncrypt)

        // Конкатенируйте IV и зашифрованные данные для передачи
        return iv + encryptedData
    }

    /**
     * Расшифровывает данные с использованием заданного ключа.
     *
     * @param keyAlias Псевдоним ключа.
     * @param encryptedData Зашифрованные данные.
     * @return Расшифрованные данные.
     */
    fun decryptData(keyAlias: String, encryptedData: ByteArray): String {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val iv = encryptedData.copyOfRange(0, 16) // Получите IV из начала зашифрованных данных
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        return String(cipher.doFinal(
            encryptedData,
            16,
            encryptedData.size - 16
        ) )// Проигнорируйте первые 16 байт (IV)
    }

    fun getTokenAsByteArray(tokenAlias: String): ByteArray? {
        val encryptedToken = keyStore.getKey(tokenAlias, null) as? SecretKey
        var a = encryptedToken?.encoded
        return a
    }

    /**
     * Генерирует случайный инициализационный вектор (IV) для шифрования.
     *
     * @return Случайный инициализационный вектор.
     */
    private fun generateRandomIv(): ByteArray {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }
}

