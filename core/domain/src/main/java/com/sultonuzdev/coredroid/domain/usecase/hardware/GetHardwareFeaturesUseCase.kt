package com.sultonuzdev.coredroid.domain.usecase.hardware

import com.sultonuzdev.coredroid.domain.model.HardwareFeaturesInfo
import com.sultonuzdev.coredroid.domain.repository.HardwareFeaturesRepository
import kotlinx.coroutines.flow.Flow

class GetHardwareFeaturesUseCase(
    private val hardwareFeaturesRepository: HardwareFeaturesRepository
) {
    operator fun invoke(): Flow<HardwareFeaturesInfo> {
        return hardwareFeaturesRepository.getHardwareFeatures()
    }
}
