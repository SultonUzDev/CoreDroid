package com.sultonuzdev.coredroid.domain.usecase.system


import com.sultonuzdev.coredroid.domain.model.SystemInfo
import com.sultonuzdev.coredroid.domain.repository.SystemRepository
import kotlinx.coroutines.flow.Flow

class GetSystemInfoUseCase(
    private val systemRepository: SystemRepository
) {
    operator fun invoke(): Flow<SystemInfo> {
        return systemRepository.getSystemInfo()
    }
}