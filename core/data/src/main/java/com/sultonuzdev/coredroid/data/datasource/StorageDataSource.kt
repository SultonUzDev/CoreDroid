package com.sultonuzdev.coredroid.data.datasource


import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.sultonuzdev.coredroid.domain.model.StorageInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File

class StorageDataSource(private val context: Context) {

    fun getStorageInfo(): Flow<StorageInfo> = flow {


            // External storage
            var externalTotalBytes = 0L
            var externalAvailableBytes = 0L
            var externalUsedBytes = 0L

            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val externalPath = Environment.getExternalStorageDirectory()
                val externalStat = StatFs(externalPath.path)
                externalTotalBytes = externalStat.blockCountLong * externalStat.blockSizeLong
                externalAvailableBytes =
                    externalStat.availableBlocksLong * externalStat.blockSizeLong
                externalUsedBytes = externalTotalBytes - externalAvailableBytes
            }

            // App-specific storage
            val appDir = context.applicationContext.dataDir
            val appUsedBytes = calculateDirectorySize(appDir)

            emit(
                StorageInfo(

                    externalTotal = externalTotalBytes,
                    externalAvailable = externalAvailableBytes,
                    externalUsed = externalUsedBytes,
                    appCacheSize = calculateDirectorySize(context.cacheDir),
                    appDataSize = appUsedBytes
                )
            )

    }.catch {
        Timber.e(it, "Failed to get storage info")
        emit(StorageInfo.empty())
    }

    private fun calculateDirectorySize(directory: File): Long {
        return try {
            if (!directory.exists() || !directory.isDirectory) return 0L

            var size = 0L
            directory.listFiles()?.forEach { file ->
                size += if (file.isDirectory) {
                    calculateDirectorySize(file)
                } else {
                    file.length()
                }
            }
            size
        } catch (e: Exception) {
            0L
        }
    }
}

