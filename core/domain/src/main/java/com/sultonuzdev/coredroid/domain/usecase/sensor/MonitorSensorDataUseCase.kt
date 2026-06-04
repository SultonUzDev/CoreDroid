package com.sultonuzdev.coredroid.domain.usecase.sensor

import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.domain.repository.SensorRepository
import kotlinx.coroutines.flow.Flow

class MonitorSensorDataUseCase(
    private val sensorRepository: SensorRepository
) {
    operator fun invoke(sensorType: Int): Flow<SensorInfo> {
        return sensorRepository.monitorSensor(sensorType)
    }

    fun monitorAllSensors(): Flow<List<SensorInfo>> {
        return sensorRepository.monitorAllSensors()
    }
}