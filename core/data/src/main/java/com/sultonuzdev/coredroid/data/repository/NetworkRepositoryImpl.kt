package com.sultonuzdev.coredroid.data.repository


import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.NetworkDataSource
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import com.sultonuzdev.coredroid.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow

class NetworkRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : BaseRepository(), NetworkRepository {

    override fun getNetworkInfo(): Flow<NetworkInfo> {
        return safeFlowCall {
            networkDataSource.getNetworkInfo()
        }
    }

    override fun monitorNetworkChanges(): Flow<NetworkInfo> {
        return safeMonitoring(
            intervalMs = 3000,
            flowCall = { networkDataSource.getNetworkInfo() },
            onError = { NetworkInfo.empty() } // Fallback on error
        )
    }
}