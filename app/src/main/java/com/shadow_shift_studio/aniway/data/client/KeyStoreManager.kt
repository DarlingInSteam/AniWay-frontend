package com.shadow_shift_studio.aniway.data.client

import android.content.Context
import android.content.SharedPreferences

/**
 * The `KeyStoreManager` object manages access to the key store (KeyStore) and stores access and refresh tokens.
 */
object KeyStoreManager {
    private lateinit var preferences: SharedPreferences

    fun initialize(context: Context) {
        preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    }

    fun putAccessToken(token: String) {
        preferences.edit().putString("accessToken", token).commit()
    }

    fun getAccessToken(): String? {
        return preferences.getString("accessToken", null)
    }

    fun putUpdateToken(token: String) {
        preferences.edit().putString("updateToken", token).commit()
    }

    fun getUpdateToken(): String? {
        return preferences.getString("updateToken", null)
    }

    fun putIsLogin() {
        preferences.edit().putString("isLogin", "1").commit()
    }

    fun getIsLogin(): String? {
        return preferences.getString("isLogin", null)
    }

    fun putUsername(username: String) {
        preferences.edit().putString("username", username).commit()
    }

    fun getUsername(): String? {
        return preferences.getString("username", null)
    }
}