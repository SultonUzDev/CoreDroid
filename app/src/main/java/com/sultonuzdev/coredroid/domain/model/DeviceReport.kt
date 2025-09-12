package com.sultonuzdev.coredroid.domain.model


data class DeviceReport(
    val batteryInfo: BatteryInfo,
    val storageInfo: StorageInfo,
    val networkInfo: NetworkInfo,
    val displayInfo: DisplayInfo,
    val cpuInfo: CpuInfo,
    val cameraInfo: CameraInfo,
    val systemInfo: SystemInfo,
    val sensors: List<SensorInfo>,
    val generatedAt: Long = System.currentTimeMillis()
)