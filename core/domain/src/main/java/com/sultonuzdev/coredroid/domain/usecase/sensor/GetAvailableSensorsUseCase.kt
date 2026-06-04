package com.sultonuzdev.coredroid.domain.usecase.sensor

import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.domain.repository.SensorRepository
import kotlinx.coroutines.flow.Flow

class GetAvailableSensorsUseCase(
    private val sensorRepository: SensorRepository
) {
    operator fun invoke(): Flow<List<SensorInfo>> {
        return sensorRepository.getAvailableSensors()
    }
}