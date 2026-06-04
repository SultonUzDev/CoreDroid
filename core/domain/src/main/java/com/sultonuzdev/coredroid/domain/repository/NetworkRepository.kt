package com.sultonuzdev.coredroid.domain.repository
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun getNetworkInfo(): Flow<NetworkInfo>
    fun monitorNetworkChanges(): Flow<NetworkInfo>
}