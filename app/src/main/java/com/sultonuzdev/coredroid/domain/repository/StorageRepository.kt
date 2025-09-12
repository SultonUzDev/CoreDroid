package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.StorageInfo
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun getStorageInfo(): Flow<StorageInfo>
    fun refreshStorageInfo(): Flow<StorageInfo>
}