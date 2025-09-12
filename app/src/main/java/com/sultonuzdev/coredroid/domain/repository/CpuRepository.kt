package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.CpuInfo
import kotlinx.coroutines.flow.Flow

interface CpuRepository {
    fun getCpuInfo(): Flow<CpuInfo>
    fun monitorCpuUsage(): Flow<CpuInfo>
}
