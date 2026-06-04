package com.sultonuzdev.coredroid.data.repository


import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.BatteryDataSource
import com.sultonuzdev.coredroid.domain.model.BatteryInfo
import com.sultonuzdev.coredroid.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class BatteryRepositoryImpl(
    private val batteryDataSource: BatteryDataSource
) : BaseRepository(), BatteryRepository {

    override fun getBatteryInfo(): Flow<BatteryInfo> {
        return safeFlowCall {
            batteryDataSource.getBatteryInfo()
        }
    }

    override fun monitorBatteryChanges(): Flow<BatteryInfo> {
        return safeMonitoring(
            intervalMs = 5000, // Update every 5 seconds
            flowCall = { batteryDataSource.getBatteryInfo() },
            onError = { error ->
                Timber.w("Battery monitoring failed: ${error.message}")
                when (error) {
                    is SecurityException -> {
                        Timber.e("Battery permission denied")
                        BatteryInfo.empty().copy(health = "Permission Denied")
                    }

                    else -> BatteryInfo.empty().copy(health = "Read Error")
                }
            }
        )
    }
}