package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.SystemInfo
import kotlinx.coroutines.flow.Flow

interface SystemRepository {
    fun getSystemInfo(): Flow<SystemInfo>
}