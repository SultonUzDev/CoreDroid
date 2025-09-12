package com.sultonuzdev.coredroid.domain.usecase.export

import android.content.Intent
import com.sultonuzdev.coredroid.domain.model.DeviceReport
import com.sultonuzdev.coredroid.domain.repository.ExportRepository

class ShareDeviceReportUseCase(
    private val exportRepository: ExportRepository
) {
    suspend operator fun invoke(report: DeviceReport): Result<Intent> {
        return exportRepository.shareReport(report)
    }
}