package com.sultonuzdev.coredroid.presentation.screens.hardware


import androidx.lifecycle.viewModelScope
import com.sultonuzdev.coredroid.core.base.BaseViewModel
import com.sultonuzdev.coredroid.core.base.LoadingInfo
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.domain.usecase.camera.GetCameraInfoUseCase
import com.sultonuzdev.coredroid.domain.usecase.cpu.GetCpuInfoUseCase
import com.sultonuzdev.coredroid.domain.usecase.display.GetDisplayInfoUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

class HardwareViewModel(
    private val getCpuInfoUseCase: GetCpuInfoUseCase,
    private val getDisplayInfoUseCase: GetDisplayInfoUseCase,
    private val getCameraInfoUseCase: GetCameraInfoUseCase
) : BaseViewModel<HardwareContract.State, HardwareContract.Intent, HardwareContract.Effect>(
    HardwareContract.State()
) {

    init {
        handleIntent(HardwareContract.Intent.LoadData)
    }

    override fun handleIntent(intent: HardwareContract.Intent) {
        when (intent) {
            is HardwareContract.Intent.LoadData -> loadData()
            is HardwareContract.Intent.RefreshData -> refreshData()
            is HardwareContract.Intent.ShowPermissionDenied -> {
                setState(
                    state.value.copy(
                        cameraPermissionState = HardwareContract.CameraPermissionState.PermanentlyDenied
                    )
                )
            }
            is HardwareContract.Intent.RequestCameraPermission -> {
                // This will be handled by the composable
            }

            is HardwareContract.Intent.OpenAppSettings -> {
                sendEffect(HardwareContract.Effect.OpenAppSettings)
            }
        }
    }

    fun onCameraPermissionGranted() {
        setState(
            state.value.copy(
                cameraPermissionState = HardwareContract.CameraPermissionState.Granted
            )
        )
        loadCameraInfo()
    }

    fun onCameraPermissionDenied(isPermanentlyDenied: Boolean) {
        setState(
            state.value.copy(
                cameraPermissionState = if (isPermanentlyDenied) {
                    HardwareContract.CameraPermissionState.PermanentlyDenied
                } else {
                    HardwareContract.CameraPermissionState.Denied
                }
            )
        )
    }

    private fun loadData() {
        viewModelScope.launch {
            setState(
                state.value.copy(
                    loadingInfo = LoadingInfo.loading("Loading device information...")

                )
            )

            // Load CPU and Display data immediately, camera data only if permission is granted
            combine(
                getCpuInfoUseCase(),
                getDisplayInfoUseCase()
            ) { cpu, display ->
                Pair(cpu, display)
            }
                .catch { error ->
                    Timber.e(error, "Failed to load overview data")
                    setState(
                        state.value.copy(
                            cpuInfoState = UiState.Error(error),
                            displayInfoState = UiState.Error(error),
                            loadingInfo = LoadingInfo.idle()
                        )
                    )
                }
                .collect { (cpu, display) ->
                    setState(
                        state.value.copy(
                            cpuInfoState = UiState.Success(cpu),
                            displayInfoState = UiState.Success(display),
                            loadingInfo = LoadingInfo.idle()
                        )
                    )
                }
        }
    }

    private fun loadCameraInfo() {
        viewModelScope.launch {
            setState(state.value.copy(cameraInfoState = UiState.Loading))

            getCameraInfoUseCase()
                .catch { error ->
                    Timber.e(error, "Failed to load camera data")
                    setState(
                        state.value.copy(
                            cameraInfoState = UiState.Error(error)
                        )
                    )
                }
                .collect { camera ->
                    setState(
                        state.value.copy(
                            cameraInfoState = UiState.Success(camera)
                        )
                    )
                }
        }
    }

    private fun refreshData() {
        setState(state.value.copy(
            isRefreshing = true,
            loadingInfo = LoadingInfo.loading("Refreshing device information...")
        ))
        loadData()
        // Also reload camera info if permission is granted
        if (state.value.cameraPermissionState is HardwareContract.CameraPermissionState.Granted) {
            loadCameraInfo()
        }
    }

}