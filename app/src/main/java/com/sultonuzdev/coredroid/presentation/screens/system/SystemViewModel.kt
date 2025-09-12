package com.sultonuzdev.coredroid.presentation.screens.system

import androidx.lifecycle.viewModelScope
import com.sultonuzdev.coredroid.core.base.BaseViewModel
import com.sultonuzdev.coredroid.domain.usecase.system.GetSystemInfoUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SystemViewModel(
    private val getSystemInfoUseCase: GetSystemInfoUseCase
) : BaseViewModel<SystemContract.State, SystemContract.Intent, SystemContract.Effect>(
    SystemContract.State()
) {

    init {
        handleIntent(SystemContract.Intent.LoadData)
    }

    override fun handleIntent(intent: SystemContract.Intent) {
        when (intent) {
            is SystemContract.Intent.LoadData -> loadData()
            is SystemContract.Intent.RefreshData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            setState(state.value.copy(isLoading = true, error = null))

            getSystemInfoUseCase().catch { error ->
                setState(state.value.copy(isLoading = false, error = error.message))
            }.collect { systemInfo ->
                setState(state.value.copy(
                    isLoading = false,
                    systemInfo = systemInfo,
                    error = null
                ))
            }
        }
    }
}