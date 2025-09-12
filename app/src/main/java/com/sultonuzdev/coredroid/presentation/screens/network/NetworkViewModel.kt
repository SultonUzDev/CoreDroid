package com.sultonuzdev.coredroid.presentation.screens.network

import androidx.lifecycle.viewModelScope
import com.sultonuzdev.coredroid.core.base.BaseViewModel
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.core.base.LoadingInfo
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import com.sultonuzdev.coredroid.domain.usecase.network.GetNetworkInfoUseCase
import com.sultonuzdev.coredroid.domain.usecase.network.MonitorNetworkStateUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class NetworkViewModel(
    private val getNetworkInfoUseCase: GetNetworkInfoUseCase,
    private val monitorNetworkStateUseCase: MonitorNetworkStateUseCase
) : BaseViewModel<NetworkContract.State, NetworkContract.Intent, NetworkContract.Effect>(
    NetworkContract.State()
) {

    private var monitoringJob: Job? = null
    private var previousNetworkInfo: NetworkInfo? = null

    init {
        handleIntent(NetworkContract.Intent.LoadData)
    }

    override fun handleIntent(intent: NetworkContract.Intent) {
        when (intent) {
            is NetworkContract.Intent.LoadData -> loadData()
            is NetworkContract.Intent.RefreshData -> refreshData()
            is NetworkContract.Intent.StartMonitoring -> startMonitoring()
            is NetworkContract.Intent.StopMonitoring -> stopMonitoring()
            is NetworkContract.Intent.ClearMonitoringEvents -> clearMonitoringEvents()
            is NetworkContract.Intent.RequestPhonePermission -> {
                // This will be handled by the composable
            }

            is NetworkContract.Intent.OpenAppSettings -> {
                sendEffect(NetworkContract.Effect.OpenAppSettings)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            setState(state.value.copy(
                networkInfoState = UiState.Loading,
                loadingInfo = LoadingInfo.loading("Loading network information...")
            ))

            getNetworkInfoUseCase()
                .catch { error ->
                    Timber.e(error, "Failed to load network info")
                    setState(state.value.copy(
                        networkInfoState = UiState.Error(error),
                        loadingInfo = LoadingInfo.idle()
                    ))
                }
                .collect { networkInfo ->
                    setState(state.value.copy(
                        networkInfoState = UiState.Success(networkInfo),
                        loadingInfo = LoadingInfo.idle()
                    ))
                }
        }
    }

    private fun refreshData() {
        setState(state.value.copy(
            loadingInfo = LoadingInfo.loading("Refreshing network information...")
        ))
        loadData()
    }

    private fun startMonitoring() {
        stopMonitoring()

        // Store current network info as baseline
        val currentNetworkInfo = state.value.networkInfoState.getDataOrNull()
        previousNetworkInfo = currentNetworkInfo

        setState(
            state.value.copy(
                isMonitoring = true,
                monitoringEvents = emptyList() // Clear previous events
            )
        )
        sendEffect(NetworkContract.Effect.ShowMonitoringStarted)

        // Add initial monitoring start event
        addMonitoringEvent(
            type = "START",
            description = "Network monitoring started"
        )

        monitoringJob = viewModelScope.launch {
            monitorNetworkStateUseCase()
                .catch { error ->
                    Timber.w(error, "Network monitoring error")
                    addMonitoringEvent(
                        type = "ERROR",
                        description = "Monitoring error: ${error.message}"
                    )
                    sendEffect(NetworkContract.Effect.ShowError(
                        "Monitoring failed: ${error.message}"
                    ))
                }
                .collect { networkInfo ->
                    // Update network info
                    setState(state.value.copy(
                        networkInfoState = UiState.Success(networkInfo)
                    ))

                    // Detect and log changes
                    detectNetworkChanges(previousNetworkInfo, networkInfo)
                    previousNetworkInfo = networkInfo
                }
        }
    }

    private fun stopMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = null

        if (state.value.isMonitoring) {
            addMonitoringEvent(
                type = "STOP",
                description = "Network monitoring stopped"
            )
        }

        setState(state.value.copy(isMonitoring = false))
        sendEffect(NetworkContract.Effect.ShowMonitoringStopped)

        previousNetworkInfo = null
    }

    private fun clearMonitoringEvents() {
        setState(state.value.copy(monitoringEvents = emptyList()))
    }

    private fun detectNetworkChanges(previous: NetworkInfo?, current: NetworkInfo) {
        if (previous == null) return

        // WiFi changes
        if (previous.isWifiConnected != current.isWifiConnected) {
            addMonitoringEvent(
                type = "WIFI",
                description = if (current.isWifiConnected) "WiFi connected" else "WiFi disconnected"
            )
        }

        if (previous.wifiSsid != current.wifiSsid && current.isWifiConnected) {
            addMonitoringEvent(
                type = "WIFI",
                description = "WiFi network changed to: ${current.wifiSsid}"
            )
        }

        // Mobile data changes
        if (previous.isMobileConnected != current.isMobileConnected) {
            addMonitoringEvent(
                type = "MOBILE",
                description = if (current.isMobileConnected) "Mobile data connected" else "Mobile data disconnected"
            )
        }

        if (previous.networkType != current.networkType) {
            addMonitoringEvent(
                type = "MOBILE",
                description = "Network type changed to: ${current.networkType}"
            )
        }

        // Connectivity changes (WiFi or Mobile)
        val previousConnected = previous.isWifiConnected || previous.isMobileConnected
        val currentConnected = current.isWifiConnected || current.isMobileConnected

        if (previousConnected != currentConnected) {
            addMonitoringEvent(
                type = "STATUS",
                description = if (currentConnected) "Device connected to internet" else "Device disconnected from internet"
            )
        }

        // Signal strength changes (only if it's numeric)
        if (previous.signalStrength != "N/A" && current.signalStrength != "N/A" &&
            previous.signalStrength.toIntOrNull() != null && current.signalStrength.toIntOrNull() != null
        ) {
            val prevStrength = previous.signalStrength.toInt()
            val currStrength = current.signalStrength.toInt()

            if (kotlin.math.abs(prevStrength - currStrength) >= 10) {
                addMonitoringEvent(
                    type = "SIGNAL",
                    description = "Signal strength changed from ${previous.signalStrength}% to ${current.signalStrength}%"
                )
            }
        }

        // Operator changes
        if (previous.operatorName != current.operatorName && current.operatorName != "Unknown") {
            addMonitoringEvent(
                type = "MOBILE",
                description = "Mobile operator changed to: ${current.operatorName}"
            )
        }

        // IP Address changes
        if (previous.ipAddress != current.ipAddress && current.ipAddress != "N/A") {
            addMonitoringEvent(
                type = "NETWORK",
                description = "IP address changed to: ${current.ipAddress}"
            )
        }
    }

    private fun addMonitoringEvent(type: String, description: String) {
        val newEvent = NetworkContract.MonitoringEvent(
            timestamp = System.currentTimeMillis(),
            type = type,
            description = description
        )

        val currentEvents = state.value.monitoringEvents
        val updatedEvents = (currentEvents + newEvent).takeLast(50) // Keep only last 50 events

        setState(state.value.copy(monitoringEvents = updatedEvents))
    }

    fun onPhonePermissionGranted() {
        setState(
            state.value.copy(
                phonePermissionState = NetworkContract.PhonePermissionState.Granted
            )
        )
        // Reload data to get network type and operator info
        loadData()
    }

    fun onPhonePermissionDenied(isPermanentlyDenied: Boolean) {
        setState(
            state.value.copy(
                phonePermissionState = if (isPermanentlyDenied) {
                    NetworkContract.PhonePermissionState.PermanentlyDenied
                } else {
                    NetworkContract.PhonePermissionState.Denied
                }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopMonitoring()
    }
}