package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.SensorInfo
import kotlinx.coroutines.flow.Flow

interface SensorRepository {
    fun getAvailableSensors(): Flow<List<SensorInfo>>
    fun monitorSensor(sensorType: Int): Flow<SensorInfo>
    fun monitorAllSensors(): Flow<List<SensorInfo>>
}