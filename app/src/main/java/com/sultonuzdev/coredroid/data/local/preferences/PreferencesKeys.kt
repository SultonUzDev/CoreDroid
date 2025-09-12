package com.sultonuzdev.coredroid.data.local.preferences


import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")
    val AUTO_REFRESH = booleanPreferencesKey("auto_refresh")
    val REFRESH_INTERVAL = stringPreferencesKey("refresh_interval")
    val EXPORT_FORMAT = stringPreferencesKey("export_format")
}