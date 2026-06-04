package com.sultonuzdev.coredroid.data.repository

import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.CpuDataSource
import com.sultonuzdev.coredroid.domain.model.CpuInfo
import com.sultonuzdev.coredroid.domain.repository.CpuRepository
import kotlinx.coroutines.flow.Flow

class CpuRepositoryImpl(
    private val cpuDataSource: CpuDataSource
) : BaseRepository(), CpuRepository {

    override fun getCpuInfo(): Flow<CpuInfo> {
        return safeFlowCall {
            cpuDataSource.getCpuInfo()
        }
    }

    override fun monitorCpuUsage(): Flow<CpuInfo> {
        return safeMonitoring(
            intervalMs = 2000L,
            flowCall = { cpuDataSource.getCpuInfo() },
            onError = { CpuInfo.empty() }
        )
    }
}