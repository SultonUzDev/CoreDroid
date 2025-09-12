package com.sultonuzdev.coredroid.presentation.screens.sensors.details

import androidx.lifecycle.viewModelScope
import com.sultonuzdev.coredroid.core.base.BaseViewModel
import com.sultonuzdev.coredroid.domain.usecase.sensor.MonitorSensorDataUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class SensorDetailsViewModel(
    private val sensorType: Int,
    private val monitorSensorDataUseCase: MonitorSensorDataUseCase
) : BaseViewModel<SensorDetailsContract.State, SensorDetailsContract.Intent, SensorDetailsContract.Effect>(
    SensorDetailsContract.State()
) {

    private var monitoringJob: Job? = null

    override fun handleIntent(intent: SensorDetailsContract.Intent) {
        when (intent) {
            is SensorDetailsContract.Intent.StartMonitoring -> startMonitoring()
            is SensorDetailsContract.Intent.StopMonitoring -> stopMonitoring()
        }
    }

    private fun startMonitoring() {
        if (state.value.isMonitoring) return

        setState(state.value.copy(isMonitoring = true, error = null))

        monitoringJob = viewModelScope.launch {
            monitorSensorDataUseCase(sensorType)
                .catch { error ->
                    Timber.e(error, "Sensor monitoring error for type $sensorType")
                    setState(state.value.copy(
                        isMonitoring = false,
                        error = error.message ?: "Failed to monitor sensor"
                    ))
                    sendEffect(SensorDetailsContract.Effect.ShowError(
                        error.message ?: "Sensor monitoring failed"
                    ))
                }
                .collect { sensorInfo ->
                    setState(state.value.copy(
                        sensorInfo = sensorInfo,
                        error = null
                    ))
                }
        }

        sendEffect(SensorDetailsContract.Effect.ShowInfo("Started monitoring sensor"))
    }

    private fun stopMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = null
        setState(state.value.copy(isMonitoring = false))
        sendEffect(SensorDetailsContract.Effect.ShowInfo("Stopped monitoring sensor"))
    }

    override fun onCleared() {
        super.onCleared()
        stopMonitoring()
    }
}