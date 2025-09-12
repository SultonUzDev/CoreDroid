package com.sultonuzdev.coredroid.presentation.screens.hardware


import com.sultonuzdev.coredroid.core.base.LoadingInfo
import com.sultonuzdev.coredroid.core.base.MviContract
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.domain.model.*

object HardwareContract : MviContract {

    data class State(
        val cpuInfoState: UiState<CpuInfo> = UiState.Loading,
        val displayInfoState: UiState<DisplayInfo> = UiState.Loading,
        val cameraInfoState: UiState<CameraInfo> = UiState.Loading,
        val loadingInfo: LoadingInfo = LoadingInfo.loading("Loading information..."),
        val isRefreshing: Boolean = false,
        val cameraPermissionState: CameraPermissionState = CameraPermissionState.NotRequested
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
        object RefreshData : Intent()
        object ShowPermissionDenied : Intent()
        object RequestCameraPermission : Intent()
        object OpenAppSettings : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
        object OpenAppSettings : Effect()
    }

    sealed class CameraPermissionState {
        object NotRequested : CameraPermissionState()
        object Granted : CameraPermissionState()
        object Denied : CameraPermissionState()
        object PermanentlyDenied : CameraPermissionState()
    }
}