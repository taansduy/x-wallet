package com.example.mywallet.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import org.jetbrains.anko.defaultSharedPreferences

object PrefsUtil {
    const val APP_THEME = "APP_THEME"
    private var shared: SharedPreferences? = null

    fun init(context: Context) {
        shared = context.defaultSharedPreferences
    }

    val ref: SharedPreferences
        get() = shared!!

}

inline fun <reified T> SharedPreferences.getObject(key: String): T? {
    val jsonStr = getString(key, null)
    if (jsonStr != null) {
        return Gson().fromJson(jsonStr, T::class.java)
    }
    return null
}

inline fun <reified T> SharedPreferences.setObject(key: String, value: T){
    edit().putString(key, Gson().toJson(value)).apply()
}