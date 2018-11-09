package de.spover.spover

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SpoverSettings<T> private constructor(val defaultValue: T, val name: String) {
    companion object {
        val SHOW_CURRENT_SPEED = SpoverSettings(true, "SHOW_CURRENT_SPEED")
        val SHOW_SPEED_LIMIT = SpoverSettings(true, "SHOW_SPEED_LIMIT")
        val OVERLAY_X = SpoverSettings(0, "")
        val OVERLAY_Y = SpoverSettings(0, "")
    }
}

class SettingsStore(activity: Activity) {
    companion object {
        const val FILE_NAME = "SpoverSettingsFile"
        private val TAG = SettingsStore::class.java.simpleName
    }

    private var preferences: SharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(setting: SpoverSettings<T>): T? {
        Log.d(TAG, "Retrieved content of ${setting.name}")
        when {
            setting.defaultValue::class == Boolean::class -> {
                return preferences.getBoolean(setting.name, setting.defaultValue as Boolean) as T
            }
            setting.defaultValue::class == Int::class -> {
                return preferences.getInt(setting.name, setting.defaultValue as Int) as T
            }
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> set(setting: SpoverSettings<T>, value: T) {
        Log.d(TAG, "Updated content of ${setting.name} to $value")
        with(preferences.edit()) {
            when {
                setting.defaultValue::class == Boolean::class -> {
                    putBoolean(setting.name, value as Boolean)
                }
                setting.defaultValue::class == Int::class -> {
                    putInt(setting.name, value as Int)
                }
            }
            apply()
        }
    }
}
