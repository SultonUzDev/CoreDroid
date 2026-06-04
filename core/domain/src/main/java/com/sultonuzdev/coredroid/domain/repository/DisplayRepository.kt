package com.sultonuzdev.coredroid.domain.repository

import com.sultonuzdev.coredroid.domain.model.DisplayInfo
import kotlinx.coroutines.flow.Flow

interface DisplayRepository {
    fun getDisplayInfo(): Flow<DisplayInfo>
}