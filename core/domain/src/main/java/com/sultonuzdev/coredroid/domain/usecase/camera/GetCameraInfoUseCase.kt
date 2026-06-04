package com.sultonuzdev.coredroid.domain.usecase.camera

import com.sultonuzdev.coredroid.domain.model.CameraInfo
import com.sultonuzdev.coredroid.domain.repository.CameraRepository
import kotlinx.coroutines.flow.Flow

class GetCameraInfoUseCase(
    private val cameraRepository: CameraRepository
) {
    operator fun invoke(): Flow<CameraInfo> {
        return cameraRepository.getCameraInfo()
    }
}