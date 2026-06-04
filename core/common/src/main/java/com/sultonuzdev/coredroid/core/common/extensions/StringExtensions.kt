package com.sultonuzdev.coredroid.core.common.extensions


fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}

fun String.truncate(maxLength: Int, suffix: String = "..."): String {
    return if (length <= maxLength) this
    else "${take(maxLength - suffix.length)}$suffix"
}

fun String.removeSpaces(): String = replace(" ", "")

fun String.isValidMacAddress(): Boolean {
    val macPattern = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$"
    return matches(macPattern.toRegex())
}