package com.sultonuzdev.coredroid.presentation.screens.overview



import androidx.lifecycle.viewModelScope
import com.sultonuzdev.coredroid.core.base.BaseViewModel
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.core.base.LoadingInfo
import com.sultonuzdev.coredroid.domain.usecase.overview.GetDeviceOverviewUseCase
import com.sultonuzdev.coredroid.domain.usecase.battery.GetBatteryInfoUseCase
import com.sultonuzdev.coredroid.domain.usecase.storage.GetStorageInfoUseCase
import com.sultonuzdev.coredroid.domain.usecase.export.ExportDeviceReportUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

class OverviewViewModel(
    private val getDeviceOverviewUseCase: GetDeviceOverviewUseCase,
    private val getBatteryInfoUseCase: GetBatteryInfoUseCase,
    private val getStorageInfoUseCase: GetStorageInfoUseCase,
    private val exportDeviceReportUseCase: ExportDeviceReportUseCase
) : BaseViewModel<OverviewContract.State, OverviewContract.Intent, OverviewContract.Effect>(
    OverviewContract.State()
) {

    init {
        handleIntent(OverviewContract.Intent.LoadData)
    }

    override fun handleIntent(intent: OverviewContract.Intent) {
        when (intent) {
            is OverviewContract.Intent.LoadData -> loadData()
            is OverviewContract.Intent.RefreshData -> refreshData()
            is OverviewContract.Intent.Export -> exportData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            setState(state.value.copy(
                loadingInfo = LoadingInfo.loading("Loading device information...")
            ))

            // Load all data in parallel
            combine(
                getDeviceOverviewUseCase(),
                getBatteryInfoUseCase(),
                getStorageInfoUseCase()
            ) { overview, battery, storage ->
                Triple(overview, battery, storage)
            }
                .catch { error ->
                    Timber.e(error, "Failed to load overview data")
                    setState(state.value.copy(
                        deviceOverviewState = UiState.Error(error),
                        batteryInfoState = UiState.Error(error),
                        storageInfoState = UiState.Error(error),
                        loadingInfo = LoadingInfo.idle(),
                        isRefreshing = false
                    ))
                }
                .collect { (overview, battery, storage) ->
                    val wasRefreshing = state.value.isRefreshing

                    setState(state.value.copy(
                        deviceOverviewState = UiState.Success(overview),
                        batteryInfoState = UiState.Success(battery),
                        storageInfoState = UiState.Success(storage),
                        loadingInfo = LoadingInfo.idle(),
                        isRefreshing = false // Reset refreshing state on success
                    ))

                    // Send refresh complete effect if this was a refresh operation
                    if (wasRefreshing) {
                        sendEffect(OverviewContract.Effect.ShowRefreshComplete)
                    }
                }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            setState(
                state.value.copy(
                    isRefreshing = true,
                    loadingInfo = LoadingInfo.loading("Refreshing device information...")
                )
            )

            // Add a small delay to make refresh visible
            delay(1000) // 1 second delay

            loadData()
        }
    }

    private fun exportData() {
        viewModelScope.launch {
            setState(state.value.copy(
                exportProgress = LoadingInfo.loading("Preparing device report...")
            ))

            try {
                // Create device report from current state data
                val overview = state.value.deviceOverviewState.getDataOrNull()
                val battery = state.value.batteryInfoState.getDataOrNull()
                val storage = state.value.storageInfoState.getDataOrNull()

                if (overview == null || battery == null || storage == null) {
                    throw IllegalStateException("Data not available for export")
                }

                setState(state.value.copy(
                    exportProgress = LoadingInfo.progress(0.5f, "Generating report...")
                ))

                // Export would happen here
                // val report = createDeviceReport(overview, battery, storage)
                // exportDeviceReportUseCase.exportToText(report)

                setState(state.value.copy(
                    exportProgress = LoadingInfo.idle()
                ))

                sendEffect(OverviewContract.Effect.ShowExportSuccess)
            } catch (e: Exception) {
                setState(state.value.copy(
                    exportProgress = LoadingInfo.idle()
                ))
                sendEffect(OverviewContract.Effect.ShowError(
                    "Export failed: ${e.message}"
                ))
            }
        }
    }
}