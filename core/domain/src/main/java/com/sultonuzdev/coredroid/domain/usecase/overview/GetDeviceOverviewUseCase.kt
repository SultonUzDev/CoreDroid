package com.sultonuzdev.coredroid.domain.usecase.overview


import com.sultonuzdev.coredroid.core.common.extensions.formatBytes
import com.sultonuzdev.coredroid.domain.model.DeviceOverview
import com.sultonuzdev.coredroid.domain.repository.BatteryRepository
import com.sultonuzdev.coredroid.domain.repository.CpuRepository
import com.sultonuzdev.coredroid.domain.repository.StorageRepository
import com.sultonuzdev.coredroid.domain.repository.SystemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDeviceOverviewUseCase(
    private val batteryRepository: BatteryRepository,
    private val storageRepository: StorageRepository,
    private val cpuRepository: CpuRepository,
    private val systemRepository: SystemRepository
) {
    operator fun invoke(): Flow<DeviceOverview> {
        return combine(
            batteryRepository.getBatteryInfo(),
            storageRepository.getStorageInfo(),
            cpuRepository.getCpuInfo(),
            systemRepository.getSystemInfo()
        ) { battery, storage, cpu, system ->
            DeviceOverview(
                batteryLevel = battery.level,
                availableStorage = storage.externalAvailable.formatBytes(),
                availableRam = cpu.availableRam.formatBytes(),
                androidVersion = "Android ${system.androidVersion}",
                deviceModel = "${system.manufacturer} ${system.model}",
                isCharging = battery.isCharging,
                storageUsagePercentage = storage.usageExternalStoragePercentage,
                ramUsagePercentage = cpu.ramUsagePercentage
            )
        }
    }
}