package com.sultonuzdev.coredroid.data.repository


import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.StorageDataSource
import com.sultonuzdev.coredroid.domain.model.StorageInfo
import com.sultonuzdev.coredroid.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow

class StorageRepositoryImpl(
    private val storageDataSource: StorageDataSource
) : BaseRepository(), StorageRepository {

    override fun getStorageInfo(): Flow<StorageInfo> {
        return storageDataSource.getStorageInfo()
    }

    override fun refreshStorageInfo(): Flow<StorageInfo> {
        return storageDataSource.getStorageInfo()
    }
}