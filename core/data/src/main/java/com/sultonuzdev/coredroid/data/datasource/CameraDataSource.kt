package com.sultonuzdev.coredroid.data.datasource

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import com.sultonuzdev.coredroid.core.common.extensions.hasPermission
import com.sultonuzdev.coredroid.core.utils.Constants
import com.sultonuzdev.coredroid.domain.model.CameraInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CameraDataSource(private val context: Context) {

    fun getCameraInfo(): Flow<CameraInfo> = flow {
        if (!context.hasPermission(Constants.PERMISSION_CAMERA)) {
            emit(CameraInfo.empty())
            return@flow
        }

        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraIds = cameraManager.cameraIdList

        var rearCameraMegapixels = 0
        var frontCameraMegapixels = 0
        var hasFlash = false
        var apiLevel = "Camera2"

        for (cameraId in cameraIds) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
            val flashInfo = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)

            if (flashInfo == true) hasFlash = true

            val sensorSize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)
            val megapixels = if (sensorSize != null) {
                (sensorSize.width * sensorSize.height) / 1_000_000
            } else 0

            when (facing) {
                CameraCharacteristics.LENS_FACING_BACK -> rearCameraMegapixels = megapixels
                CameraCharacteristics.LENS_FACING_FRONT -> frontCameraMegapixels = megapixels
            }
        }

        emit(
            CameraInfo(
                rearCameraMegapixels = rearCameraMegapixels,
                frontCameraMegapixels = frontCameraMegapixels,
                hasFlash = hasFlash,
                apiLevel = apiLevel,
                cameraCount = cameraIds.size
            )
        )
    }.catch { exception ->
        Timber.e(exception, "Failed to get camera info")
        emit(CameraInfo.empty())
    }
}