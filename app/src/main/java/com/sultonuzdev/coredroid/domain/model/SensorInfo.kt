package com.sultonuzdev.coredroid.domain.model


data class SensorInfo(
    val name: String,
    val type: Int,
    val vendor: String,
    val version: Int,
    val power: Float,
    val resolution: Float,
    val maximumRange: Float,
    val values: List<Float>,
    val accuracy: Int,
    val timestamp: Long
) {
    val formattedValues: String
        get() = when (values.size) {
            1 -> "%.2f".format(values[0])
            3 -> "x: %.2f, y: %.2f, z: %.2f".format(values[0], values[1], values[2])
            else -> values.joinToString(", ") { "%.2f".format(it) }
        }
}