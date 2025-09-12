package com.sultonuzdev.coredroid.data.local.database.entities

import androidx.room.Entity


import androidx.room.PrimaryKey

@Entity(tableName = "device_info")
data class DeviceInfoEntity(
    @PrimaryKey
    val id: String,
    val timestamp: Long,
    val manufacturer: String,
    val model: String,
    val androidVersion: String,
    val apiLevel: Int,
    val batteryLevel: Int,
    val availableStorage: Long,
    val totalStorage: Long,
    val availableRam: Long,
    val totalRam: Long
)