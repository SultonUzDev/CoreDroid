package com.sultonuzdev.coredroid.data.repository

import com.sultonuzdev.coredroid.core.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.CameraDataSource
import com.sultonuzdev.coredroid.domain.model.CameraInfo
import com.sultonuzdev.coredroid.domain.repository.CameraRepository
import kotlinx.coroutines.flow.Flow

class CameraRepositoryImpl(
    private val cameraDataSource: CameraDataSource
) : BaseRepository(), CameraRepository {

    override fun getCameraInfo(): Flow<CameraInfo> {
        return safeFlowCall(
            fallback = CameraInfo.empty().copy(
                apiLevel = "Safe Call Fallback"
            )
        ) {
            cameraDataSource.getCameraInfo()
        }
    }


}