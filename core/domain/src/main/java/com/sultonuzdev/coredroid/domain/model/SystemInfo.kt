package com.sultonuzdev.coredroid.domain.model


data class SystemInfo(
    val androidVersion: String,
    val apiLevel: Int,
    val manufacturer: String,
    val model: String,
    val brand: String,
    val device: String,
    val buildNumber: String,
    val kernelVersion: String,
    val bootloader: String,
    val baseband: String,
    val securityPatch: String,
    val isRooted: Boolean,
    val serialNumber: String
) {
    companion object {
        fun empty() = SystemInfo(
            androidVersion = "Unknown",
            apiLevel = 0,
            manufacturer = "Unknown",
            model = "Unknown",
            brand = "Unknown",
            device = "Unknown",
            buildNumber = "Unknown",
            kernelVersion = "Unknown",
            bootloader = "Unknown",
            baseband = "Unknown",
            securityPatch = "Unknown",
            isRooted = false,
            serialNumber = "Unknown"
        )
    }
}