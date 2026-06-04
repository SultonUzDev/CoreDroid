package com.sultonuzdev.coredroid.domain.usecase.battery

import com.sultonuzdev.coredroid.domain.model.BatteryInfo
import com.sultonuzdev.coredroid.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow

class MonitorBatteryUseCase(
    private val batteryRepository: BatteryRepository
) {
    operator fun invoke(): Flow<BatteryInfo> {
        return batteryRepository.monitorBatteryChanges()
    }
}