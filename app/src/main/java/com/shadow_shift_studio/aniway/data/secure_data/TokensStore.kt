package com.shadow_shift_studio.aniway.data.secure_data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * The `KeyStore` class provides methods for working with Android Keystore.
 * It allows you to create and manage keys for encrypting and decrypting data.
 *
 * @property context The application context required for Keystore initialization.
 */
class TokensStore(private val context: Context) {

    private val keyStore: java.security.KeyStore =
        java.security.KeyStore.getInstance("AndroidKeyStore")

    init {
        // Initialize KeyStore when an instance of the class is created
        keyStore.load(null)
    }

    /**
     * Creates a secret key with the given alias if it does not exist.
     *
     * @param keyAlias The key alias.
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
     * Encrypts data using the specified key.
     *
     * @param keyAlias The key alias.
     * @param dataToEncrypt Data to be encrypted.
     * @return Encrypted data.
     */
    fun encryptData(keyAlias: String, dataToEncrypt: ByteArray): ByteArray {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val iv = generateRandomIv() // Generate a random IV
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))

        val encryptedData = cipher.doFinal(dataToEncrypt)

        // Concatenate IV and encrypted data for transmission
        return iv + encryptedData
    }

    /**
     * Decrypts data using the specified key.
     *
     * @param keyAlias The key alias.
     * @param encryptedData Encrypted data.
     * @return Decrypted data.
     */
    fun decryptData(keyAlias: String, encryptedData: ByteArray): String {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val iv = encryptedData.copyOfRange(0, 16) // Get IV from the beginning of encrypted data
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        return String(
            cipher.doFinal(
                encryptedData,
                16,
                encryptedData.size - 16
            )
        ) // Ignore the first 16 bytes (IV)
    }

    fun getTokenAsByteArray(tokenAlias: String): ByteArray? {
        val encryptedToken = keyStore.getKey(tokenAlias, null) as? SecretKey
        return encryptedToken?.encoded
    }

    /**
     * Generates a random initialization vector (IV) for encryption.
     *
     * @return Random initialization vector.
     */
    private fun generateRandomIv(): ByteArray {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }
}


