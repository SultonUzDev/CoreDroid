package com.sultonuzdev.coredroid.domain.usecase.storage


import com.sultonuzdev.coredroid.domain.model.StorageInfo
import com.sultonuzdev.coredroid.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow

class GetStorageInfoUseCase(
    private val storageRepository: StorageRepository
) {
    operator fun invoke(): Flow<StorageInfo> {
        return storageRepository.getStorageInfo()
    }
}