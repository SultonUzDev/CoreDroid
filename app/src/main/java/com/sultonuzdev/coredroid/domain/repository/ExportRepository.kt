package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.DeviceReport
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ExportRepository {
    suspend fun exportToText(report: DeviceReport): Result<File>
    suspend fun exportToPdf(report: DeviceReport): Result<File>
    suspend fun shareReport(report: DeviceReport): Result<android.content.Intent>
}