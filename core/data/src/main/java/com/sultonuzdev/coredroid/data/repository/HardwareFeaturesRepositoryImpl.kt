package com.sultonuzdev.coredroid.data.repository

import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.HardwareFeaturesDataSource
import com.sultonuzdev.coredroid.domain.model.HardwareFeaturesInfo
import com.sultonuzdev.coredroid.domain.repository.HardwareFeaturesRepository
import kotlinx.coroutines.flow.Flow

class HardwareFeaturesRepositoryImpl(
    private val hardwareFeaturesDataSource: HardwareFeaturesDataSource
) : BaseRepository(), HardwareFeaturesRepository {

    override fun getHardwareFeatures(): Flow<HardwareFeaturesInfo> {
        return safeFlowCall(fallback = HardwareFeaturesInfo.empty()) {
            hardwareFeaturesDataSource.getHardwareFeatures()
        }
    }
}
