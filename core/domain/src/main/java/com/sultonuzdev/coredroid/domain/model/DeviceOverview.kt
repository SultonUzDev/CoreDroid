package com.sultonuzdev.coredroid.domain.model


data class DeviceOverview(
    val batteryLevel: Int,
    val availableStorage: String,
    val availableRam: String,
    val androidVersion: String,
    val deviceModel: String,
    val isCharging: Boolean,
    val storageUsagePercentage: Int,
    val ramUsagePercentage: Int
)