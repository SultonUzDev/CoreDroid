package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.BatteryInfo
import kotlinx.coroutines.flow.Flow

interface BatteryRepository {
    fun getBatteryInfo(): Flow<BatteryInfo>
    fun monitorBatteryChanges(): Flow<BatteryInfo>
}

