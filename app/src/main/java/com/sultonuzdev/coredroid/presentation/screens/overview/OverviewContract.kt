package com.sultonuzdev.coredroid.presentation.screens.overview


import com.sultonuzdev.coredroid.core.base.MviContract
import com.sultonuzdev.coredroid.domain.model.DeviceOverview
import com.sultonuzdev.coredroid.domain.model.BatteryInfo
import com.sultonuzdev.coredroid.domain.model.StorageInfo


import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.core.base.LoadingInfo

object OverviewContract : MviContract {

    data class State(
        val deviceOverviewState: UiState<DeviceOverview> = UiState.Loading,
        val batteryInfoState: UiState<BatteryInfo> = UiState.Loading,
        val storageInfoState: UiState<StorageInfo> = UiState.Loading,
        val loadingInfo: LoadingInfo = LoadingInfo.loading("Loading device information..."),
        val isRefreshing: Boolean = false,
        val exportProgress: LoadingInfo = LoadingInfo.idle()
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
        object RefreshData : Intent()
        object Export : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
        object ShowExportSuccess : Effect()
        data class ShareReport(val intent: android.content.Intent) : Effect()
        object ShowRefreshComplete : Effect()
    }
}