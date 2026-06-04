//package com.sultonuzdev.coredroid.core.common.utils
//
//
//import android.content.Context
//import android.content.Intent
//import androidx.core.content.FileProvider
//import com.sultonuzdev.coredroid.core.utils.Constants
//import com.sultonuzdev.coredroid.domain.model.DeviceReport
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import timber.log.Timber
//import java.io.File
//import java.io.FileWriter
//import java.text.SimpleDateFormat
//import java.util.*
//
//class FileExporter(private val context: Context) {
//
//    suspend fun exportToText(report: DeviceReport): Result<File> = withContext(Dispatchers.IO) {
//        try {
//            val fileName = "${Constants.EXPORT_FILE_NAME}_${getTimestamp()}.txt"
//            val file = File(context.cacheDir, fileName)
//
//            FileWriter(file).use { writer ->
//                writer.write(formatReportAsText(report))
//            }
//
//            Result.success(file)
//        } catch (e: Exception) {
//            Timber.e(e, "Failed to export text file")
//            Result.failure(e)
//        }
//    }
//
//    suspend fun shareReport(report: DeviceReport): Result<Intent> = withContext(Dispatchers.IO) {
//        try {
//            val textResult = exportToText(report)
//            if (textResult.isFailure) {
//                return@withContext Result.failure(textResult.exceptionOrNull()!!)
//            }
//
//            val file = textResult.getOrThrow()
//            val uri = FileProvider.getUriForFile(
//                context,
//                "${context.packageName}.fileprovider",
//                file
//            )
//
//            val intent = Intent(Intent.ACTION_SEND).apply {
//                type = Constants.EXPORT_MIME_TYPE_TXT
//                putExtra(Intent.EXTRA_STREAM, uri)
//                putExtra(Intent.EXTRA_SUBJECT, "Device Report - ${Constants.APP_NAME}")
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            }
//
//            Result.success(Intent.createChooser(intent, "Share Device Report"))
//        } catch (e: Exception) {
//            Timber.e(e, "Failed to share report")
//            Result.failure(e)
//        }
//    }
//
//    private fun formatReportAsText(report: DeviceReport): String {
//        return buildString {
//            appendLine("==========================================")
//            appendLine("${Constants.APP_NAME} - Device Report")
//            appendLine("Generated: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}")
//            appendLine("==========================================")
//            appendLine()
//
//            appendLine("DEVICE OVERVIEW")
//            appendLine("---------------")
//            appendLine("Manufacturer: ${report.systemInfo.manufacturer}")
//            appendLine("Model: ${report.systemInfo.model}")
//            appendLine("Android Version: ${report.systemInfo.androidVersion}")
//            appendLine("Battery Level: ${report.batteryInfo.level}%")
//            appendLine("Available Storage: ${report.storageInfo.externalAvailable}")
//            appendLine("Available RAM: ${report.cpuInfo.availableRam}")
//            appendLine()
//
//            // Add more sections as needed...
//        }
//    }
//
//    private fun getTimestamp(): String {
//        return SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//    }
//}