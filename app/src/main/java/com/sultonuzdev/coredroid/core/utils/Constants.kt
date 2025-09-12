package com.sultonuzdev.coredroid.core.utils

object Constants {

    // App Info
    const val APP_NAME = "CoreDroid"
    const val APP_VERSION = "1.0.0"

    // Permissions
    const val PERMISSION_READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE
    const val PERMISSION_ACCESS_WIFI_STATE = android.Manifest.permission.ACCESS_WIFI_STATE
    const val PERMISSION_ACCESS_NETWORK_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE
    const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
    const val PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    // File Export
    const val EXPORT_FILE_NAME = "device_report"
    const val EXPORT_MIME_TYPE_PDF = "application/pdf"
    const val EXPORT_MIME_TYPE_TXT = "text/plain"

    // Sensor Update Intervals
    const val SENSOR_UPDATE_INTERVAL_MS = 1000L
    const val BATTERY_UPDATE_INTERVAL_MS = 5000L
    const val NETWORK_UPDATE_INTERVAL_MS = 3000L

    // Database
    const val DATABASE_NAME = "coredroid_database"
    const val DATABASE_VERSION = 1
}