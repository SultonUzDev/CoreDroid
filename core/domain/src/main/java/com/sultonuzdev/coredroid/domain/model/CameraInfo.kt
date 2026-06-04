package com.sultonuzdev.coredroid.domain.model


data class CameraInfo(
    val rearCameraMegapixels: Int,
    val frontCameraMegapixels: Int,
    val hasFlash: Boolean,
    val apiLevel: String,
    val cameraCount: Int
) {
    companion object {
        fun empty() = CameraInfo(
            rearCameraMegapixels = 0,
            frontCameraMegapixels = 0,
            hasFlash = false,
            apiLevel = "Unknown",
            cameraCount = 0
        )
    }
}