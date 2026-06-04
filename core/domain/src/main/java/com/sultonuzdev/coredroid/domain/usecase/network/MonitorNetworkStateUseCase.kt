package com.sultonuzdev.coredroid.domain.usecase.network


import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import com.sultonuzdev.coredroid.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow

class MonitorNetworkStateUseCase(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Flow<NetworkInfo> {
        return networkRepository.monitorNetworkChanges()
    }
}