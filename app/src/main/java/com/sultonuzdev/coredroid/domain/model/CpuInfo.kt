package com.sultonuzdev.coredroid.domain.model


data class CpuInfo(
    val name: String,
    val architecture: String,
    val coreCount: Int,
    val maxFrequency: Long,
    val currentFrequency: Long,
    val totalRam: Long,
    val availableRam: Long,
    val usedRam: Long,
    val cpuUsage: Float
) {
    val ramUsagePercentage: Int
        get() = if (totalRam > 0) ((usedRam * 100) / totalRam).toInt() else 0

    companion object {
        fun empty() = CpuInfo(
            name = "Unknown",
            architecture = "Unknown",
            coreCount = 0,
            maxFrequency = 0L,
            currentFrequency = 0L,
            totalRam = 0L,
            availableRam = 0L,
            usedRam = 0L,
            cpuUsage = 0f
        )
    }
}