package com.sultonuzdev.coredroid.domain.usecase.cpu


import com.sultonuzdev.coredroid.domain.model.CpuInfo
import com.sultonuzdev.coredroid.domain.repository.CpuRepository
import kotlinx.coroutines.flow.Flow

class MonitorCpuUsageUseCase(
    private val cpuRepository: CpuRepository
) {
    operator fun invoke(): Flow<CpuInfo> {
        return cpuRepository.monitorCpuUsage()
    }
}