package com.sultonuzdev.coredroid.data.repository

import com.sultonuzdev.coredroid.core.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.SystemDataSource
import com.sultonuzdev.coredroid.domain.model.SystemInfo
import com.sultonuzdev.coredroid.domain.repository.SystemRepository
import kotlinx.coroutines.flow.Flow

class SystemRepositoryImpl(
    private val systemDataSource: SystemDataSource
) : BaseRepository(), SystemRepository {

    override fun getSystemInfo(): Flow<SystemInfo> {
        return safeFlowCall { systemDataSource.getSystemInfo() }
    }
}