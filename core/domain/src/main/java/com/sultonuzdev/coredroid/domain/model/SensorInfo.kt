package com.sultonuzdev.coredroid.domain.model

import timber.log.Timber

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
        get() {
            try {
                Timber.d("formattedValues - Sensor: $name, Type: $type, Values size: ${values.size}, Values: $values")

                return when {
                    values.isEmpty() -> {
                        Timber.d("formattedValues - Empty values, returning 'No data'")
                        "No data"
                    }
                    values.size == 1 -> {
                        Timber.d("formattedValues - Single value: ${values[0]}")
                        "%.2f".format(values[0])
                    }
                    values.size == 3 -> {
                        Timber.d("formattedValues - Three values: x=${values[0]}, y=${values[1]}, z=${values[2]}")
                        "x: %.2f, y: %.2f, z: %.2f".format(values[0], values[1], values[2])
                    }
                    else -> {
                        Timber.d("formattedValues - Multiple values (${values.size}): $values")
                        values.joinToString(", ") { "%.2f".format(it) }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "ERROR in formattedValues - Sensor: $name, Type: $type, Values size: ${values.size}, Values: $values")
                return "Error: ${e.message}"
            }
        }
}