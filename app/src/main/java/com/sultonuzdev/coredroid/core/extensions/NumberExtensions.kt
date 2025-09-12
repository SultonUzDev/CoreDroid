package com.sultonuzdev.coredroid.core.extensions


import kotlin.math.pow

fun Long.formatBytes(): String {
    if (this < 1024) return "$this B"

    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var size = this.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }

    return "%.1f %s".format(size, units[unitIndex])
}

fun Int.formatFrequency(): String {
    return when {
        this >= 1_000_000 -> "%.1f GHz".format(this / 1_000_000.0)
        this >= 1_000 -> "%.0f MHz".format(this / 1_000.0)
        else -> "$this Hz"
    }
}

fun Float.formatTemperature(): String = "%.1f°C".format(this)

fun Int.formatPercentage(): String = "$this%"

fun Double.formatDecimal(places: Int = 1): String = "%.${places}f".format(this)
