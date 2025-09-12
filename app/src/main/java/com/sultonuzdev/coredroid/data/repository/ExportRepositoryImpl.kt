package com.sultonuzdev.coredroid.data.repository


import android.content.Intent
import com.sultonuzdev.coredroid.core.base.BaseRepository
import com.sultonuzdev.coredroid.core.utils.FileExporter
import com.sultonuzdev.coredroid.domain.model.DeviceReport
import com.sultonuzdev.coredroid.domain.repository.ExportRepository
import java.io.File

class ExportRepositoryImpl(
    private val fileExporter: FileExporter
) : BaseRepository(), ExportRepository {

    override suspend fun exportToText(report: DeviceReport): Result<File> {
        return fileExporter.exportToText(report)
    }

    override suspend fun exportToPdf(report: DeviceReport): Result<File> {
        // TODO: Implement PDF export
        return Result.failure(NotImplementedError("PDF export not implemented yet"))
    }

    override suspend fun shareReport(report: DeviceReport): Result<Intent> {
        return fileExporter.shareReport(report)
    }
}