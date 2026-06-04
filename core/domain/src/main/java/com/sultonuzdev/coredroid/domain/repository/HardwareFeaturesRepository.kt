package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.HardwareFeaturesInfo
import kotlinx.coroutines.flow.Flow

interface HardwareFeaturesRepository {
    fun getHardwareFeatures(): Flow<HardwareFeaturesInfo>
}
