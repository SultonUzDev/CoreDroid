package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.CameraInfo
import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    fun getCameraInfo(): Flow<CameraInfo>
}