package com.sultonuzdev.coredroid.navigation.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import timber.log.Timber

inline fun <reified T : Any> createCustomNavArgument(
    serializer: KSerializer<T>,
    isNullableAllowed: Boolean = false
): NavType<T?> {
    return object : NavType<T?>(isNullableAllowed = isNullableAllowed) {
        override fun get(bundle: Bundle, key: String): T? {
            val jsonString = bundle.getString(key) ?: return null
            if (jsonString == "null") return null
            return try {
                Json.decodeFromString(serializer, jsonString)
            } catch (e: Exception) {
                Timber.tag("createCustomNavArgument").d(e)
                return null
            }
        }

        override fun parseValue(value: String): T? {
            if (value == "null" || value.isEmpty()) return null
            return try {
                Json.decodeFromString(serializer, Uri.decode(value))
            } catch (e: Exception) {
                Timber.tag("createCustomNavArgument").w(e, "Failed to parse value: $value")
                null
            }
        }

        override fun put(bundle: Bundle, key: String, value: T?) {
            val jsonString = if (value != null) {
                Json.encodeToString(serializer, value)
            } else {
                "null"
            }
            bundle.putString(key, jsonString)
        }

        override fun serializeAsValue(value: T?): String {
            return if (value != null) {
                Uri.encode(Json.encodeToString(serializer, value))
            } else {
                "null"
            }
        }
    }
}

