package com.sultonuzdev.coredroid.data.repository


import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.DisplayDataSource
import com.sultonuzdev.coredroid.domain.model.DisplayInfo
import com.sultonuzdev.coredroid.domain.repository.DisplayRepository
import kotlinx.coroutines.flow.Flow

class DisplayRepositoryImpl(
    private val displayDataSource: DisplayDataSource
) : BaseRepository(), DisplayRepository {

    override fun getDisplayInfo(): Flow<DisplayInfo> {
        return safeFlowCall {
            displayDataSource.getDisplayInfo()
        }
    }
}