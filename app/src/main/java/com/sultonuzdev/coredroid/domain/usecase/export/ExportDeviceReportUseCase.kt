package com.sultonuzdev.coredroid.domain.usecase.export

import com.sultonuzdev.coredroid.domain.model.DeviceReport
import com.sultonuzdev.coredroid.domain.repository.ExportRepository
import java.io.File

class ExportDeviceReportUseCase(
    private val exportRepository: ExportRepository
) {
    suspend fun exportToText(report: DeviceReport): Result<File> {
        return exportRepository.exportToText(report)
    }

    suspend fun exportToPdf(report: DeviceReport): Result<File> {
        return exportRepository.exportToPdf(report)
    }
}
