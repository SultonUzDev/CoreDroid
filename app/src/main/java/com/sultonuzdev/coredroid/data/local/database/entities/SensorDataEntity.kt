package com.sultonuzdev.coredroid.data.local.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensor_data")
data class SensorDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sensorName: String,
    val sensorType: Int,
    val value: String,
    val timestamp: Long,
    val accuracy: Int
)