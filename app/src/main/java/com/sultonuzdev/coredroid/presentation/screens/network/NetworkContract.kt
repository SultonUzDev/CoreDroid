package com.sultonuzdev.coredroid.presentation.screens.network


import com.sultonuzdev.coredroid.core.base.LoadingInfo
import com.sultonuzdev.coredroid.core.base.MviContract
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.domain.model.NetworkInfo

object NetworkContract : MviContract {

    data class State(
        val networkInfoState: UiState<NetworkInfo> = UiState.Loading,
        val loadingInfo: LoadingInfo = LoadingInfo.loading("Loading network information..."),
        val isMonitoring: Boolean = false,
        val monitoringEvents: List<MonitoringEvent> = emptyList(),
        val phonePermissionState: PhonePermissionState = PhonePermissionState.NotRequested
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
        object RefreshData : Intent()
        object StartMonitoring : Intent()
        object StopMonitoring : Intent()
        object ClearMonitoringEvents : Intent()
        object RequestPhonePermission : Intent()
        object OpenAppSettings : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
        object ShowMonitoringStarted : Effect()
        object ShowMonitoringStopped : Effect()
        object OpenAppSettings : Effect()
    }

    data class MonitoringEvent(
        val timestamp: Long,
        val type: String,
        val description: String
    )

    sealed class PhonePermissionState {
        object NotRequested : PhonePermissionState()
        object Granted : PhonePermissionState()
        object Denied : PhonePermissionState()
        object PermanentlyDenied : PhonePermissionState()
    }
}