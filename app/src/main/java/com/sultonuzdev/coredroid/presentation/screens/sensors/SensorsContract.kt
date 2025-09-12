package com.sultonuzdev.coredroid.presentation.screens.sensors

import com.sultonuzdev.coredroid.core.base.MviContract
import com.sultonuzdev.coredroid.domain.model.SensorInfo

object SensorsContract : MviContract {

    data class State(
        val isLoading: Boolean = true,
        val sensors: List<SensorInfo> = emptyList(),
        val error: String? = null,
        val isMonitoring: Boolean = false,
        val monitoringMode: MonitoringMode = MonitoringMode.ALL,
        val selectedSensorTypes: Set<Int> = emptySet(),
        val liveSensorData: Map<Int, SensorInfo> = emptyMap()
    ) : MviContract.State

    sealed class MonitoringMode {
        object ALL : MonitoringMode()
        object SELECTED : MonitoringMode()
        object NONE : MonitoringMode()
    }

    sealed class Intent : MviContract.Intent {
        object LoadSensors : Intent()
        object StartMonitoringAll : Intent()
        object StartMonitoringSelected : Intent()
        object StopMonitoring : Intent()
        object RefreshSensors : Intent()
        data class ToggleSensorSelection(val sensorType: Int) : Intent()
        data class StartMonitoringSingle(val sensorType: Int) : Intent()
        object ClearSelection : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
        data class ShowInfo(val message: String) : Effect()
    }
}