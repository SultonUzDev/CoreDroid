package com.sultonuzdev.coredroid.presentation.screens.system

import com.sultonuzdev.coredroid.core.base.MviContract
import com.sultonuzdev.coredroid.domain.model.SystemInfo

object SystemContract : MviContract {

    data class State(
        val isLoading: Boolean = true,
        val systemInfo: SystemInfo? = null,
        val error: String? = null
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
        object RefreshData : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
    }
}