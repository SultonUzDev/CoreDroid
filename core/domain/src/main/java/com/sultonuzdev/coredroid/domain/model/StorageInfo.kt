package com.sultonuzdev.coredroid.domain.model


data class StorageInfo(

    val externalTotal: Long,
    val externalAvailable: Long,
    val externalUsed: Long,
    val appCacheSize: Long,
    val appDataSize: Long
) {
    val usageExternalStoragePercentage: Int
        get() = if (externalTotal > 0) {
            (externalUsed * 100 / externalTotal).toInt()
        } else 0



    companion object {
        fun empty() = StorageInfo(
            externalTotal = 0L,
            externalAvailable = 0L,
            externalUsed = 0L,
            appCacheSize = 0L,
            appDataSize = 0L
        )
    }
}