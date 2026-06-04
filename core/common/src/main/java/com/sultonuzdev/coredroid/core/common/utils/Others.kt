package com.sultonuzdev.coredroid.core.common.utils

// Helper functions for sensor data formatting
 fun getSensorTypeName(type: Int): String {
    return when (type) {
        1 -> "Accelerometer"
        2 -> "Magnetic Field"
        3 -> "Orientation"
        4 -> "Gyroscope"
        5 -> "Light"
        6 -> "Pressure"
        7 -> "Temperature"
        8 -> "Proximity"
        9 -> "Gravity"
        10 -> "Linear Acceleration"
        11 -> "Rotation Vector"
        12 -> "Relative Humidity"
        13 -> "Ambient Temperature"
        14 -> "Magnetic Field Uncalibrated"
        15 -> "Game Rotation Vector"
        16 -> "Gyroscope Uncalibrated"
        17 -> "Significant Motion"
        18 -> "Step Detector"
        19 -> "Step Counter"
        20 -> "Geomagnetic Rotation Vector"
        21 -> "Heart Rate"
        22 -> "Tilt Detector"
        23 -> "Wake Gesture"
        24 -> "Glance Gesture"
        25 -> "Pick Up Gesture"
        26 -> "Wrist Tilt Gesture"
        else -> "Type $type"
    }
}

 fun getAccuracyText(accuracy: Int): String {
    return when (accuracy) {
        0 -> "Unreliable"
        1 -> "Low"
        2 -> "Medium"
        3 -> "High"
        else -> "Unknown"
    }
}

 fun formatResolution(resolution: Float): String {
    return if (resolution > 0) {
        "%.4f".format(resolution)
    } else {
        "N/A"
    }
}
