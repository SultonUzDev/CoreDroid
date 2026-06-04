package com.sultonuzdev.coredroid.domain.usecase.display

import com.sultonuzdev.coredroid.domain.model.DisplayInfo
import com.sultonuzdev.coredroid.domain.repository.DisplayRepository
import kotlinx.coroutines.flow.Flow

class GetDisplayInfoUseCase(
    private val displayRepository: DisplayRepository
) {
    operator fun invoke(): Flow<DisplayInfo> {
        return displayRepository.getDisplayInfo()
    }
}
