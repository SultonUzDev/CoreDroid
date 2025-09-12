package com.sultonuzdev.coredroid.presentation.screens.sensors

import androidx.lifecycle.viewModelScope
import com.sultonuzdev.coredroid.core.base.BaseViewModel
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.domain.usecase.sensor.GetAvailableSensorsUseCase
import com.sultonuzdev.coredroid.domain.usecase.sensor.MonitorSensorDataUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

class SensorsViewModel(
    private val getAvailableSensorsUseCase: GetAvailableSensorsUseCase,
    private val monitorSensorDataUseCase: MonitorSensorDataUseCase
) : BaseViewModel<SensorsContract.State, SensorsContract.Intent, SensorsContract.Effect>(
    SensorsContract.State()
) {

    private var monitoringJobs: MutableMap<Int, Job> = mutableMapOf()
    private var allSensorsMonitoringJob: Job? = null

    init {
        handleIntent(SensorsContract.Intent.LoadSensors)
    }

    override fun handleIntent(intent: SensorsContract.Intent) {
        when (intent) {
            is SensorsContract.Intent.LoadSensors -> loadSensors()
            is SensorsContract.Intent.StartMonitoringAll -> startMonitoringAll()
            is SensorsContract.Intent.StartMonitoringSelected -> startMonitoringSelected()
            is SensorsContract.Intent.StopMonitoring -> stopMonitoring()
            is SensorsContract.Intent.RefreshSensors -> refreshSensors()
            is SensorsContract.Intent.ToggleSensorSelection -> toggleSensorSelection(intent.sensorType)
            is SensorsContract.Intent.StartMonitoringSingle -> startMonitoringSingle(intent.sensorType)
            is SensorsContract.Intent.ClearSelection -> clearSelection()
        }
    }

    private fun loadSensors() {
        viewModelScope.launch {
            setState(state.value.copy(isLoading = true, error = null))

            getAvailableSensorsUseCase()
                .catch { error ->
                    setState(state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load sensors"
                    ))
                }
                .collect { sensors ->
                    setState(state.value.copy(
                        isLoading = false,
                        sensors = sensors,
                        error = null
                    ))
                }
        }
    }

    private fun refreshSensors() {
        stopMonitoring()
        loadSensors()
    }

    private fun startMonitoringAll() {
        stopMonitoring()
        setState(state.value.copy(
            isMonitoring = true,
            monitoringMode = SensorsContract.MonitoringMode.ALL
        ))

        allSensorsMonitoringJob = viewModelScope.launch {
            monitorSensorDataUseCase.monitorAllSensors()
                .catch { error ->
                    Timber.w(error, "All sensors monitoring error")
                    sendEffect(SensorsContract.Effect.ShowError(
                        error.message ?: "Sensor monitoring failed"
                    ))
                }
                .collect { sensors ->
                    // Update live data map
                    val liveDataMap = sensors.associateBy { it.type }
                    setState(state.value.copy(
                        sensors = sensors,
                        liveSensorData = liveDataMap
                    ))
                }
        }

        sendEffect(SensorsContract.Effect.ShowInfo("Monitoring all available sensors"))
    }

    private fun startMonitoringSelected() {
        if (state.value.selectedSensorTypes.isEmpty()) {
            sendEffect(SensorsContract.Effect.ShowError("Please select sensors to monitor first"))
            return
        }

        stopMonitoring()
        setState(state.value.copy(
            isMonitoring = true,
            monitoringMode = SensorsContract.MonitoringMode.SELECTED
        ))

        // Start monitoring each selected sensor individually
        state.value.selectedSensorTypes.forEach { sensorType ->
            startIndividualSensorMonitoring(sensorType)
        }

        sendEffect(SensorsContract.Effect.ShowInfo(
            "Monitoring ${state.value.selectedSensorTypes.size} selected sensors"
        ))
    }

    private fun startMonitoringSingle(sensorType: Int) {
        // Stop current monitoring but keep other individual sensors running
        if (state.value.monitoringMode == SensorsContract.MonitoringMode.ALL) {
            allSensorsMonitoringJob?.cancel()
            allSensorsMonitoringJob = null
        }

        // If this sensor is already being monitored individually, stop it
        if (monitoringJobs.containsKey(sensorType)) {
            stopIndividualSensorMonitoring(sensorType)
        } else {
            // Start monitoring this sensor
            startIndividualSensorMonitoring(sensorType)

            // Update state
            val newSelectedTypes = state.value.selectedSensorTypes + sensorType
            setState(state.value.copy(
                isMonitoring = monitoringJobs.isNotEmpty(),
                monitoringMode = if (newSelectedTypes.size == state.value.sensors.size) {
                    SensorsContract.MonitoringMode.ALL
                } else {
                    SensorsContract.MonitoringMode.SELECTED
                },
                selectedSensorTypes = newSelectedTypes
            ))

            val sensorName = state.value.sensors.find { it.type == sensorType }?.name ?: "Sensor"
            sendEffect(SensorsContract.Effect.ShowInfo("Started monitoring $sensorName"))
        }
    }

    private fun startIndividualSensorMonitoring(sensorType: Int) {
        val job = viewModelScope.launch {
            monitorSensorDataUseCase(sensorType)
                .catch { error ->
                    Timber.w(error, "Individual sensor monitoring error for type $sensorType")
                    // Remove failed sensor from monitoring
                    stopIndividualSensorMonitoring(sensorType)
                }
                .collect { sensorInfo ->
                    // Update the live data map for this specific sensor
                    val currentLiveData = state.value.liveSensorData.toMutableMap()
                    currentLiveData[sensorType] = sensorInfo

                    // Also update the main sensors list
                    val updatedSensors = state.value.sensors.map { sensor ->
                        if (sensor.type == sensorType) sensorInfo else sensor
                    }

                    setState(state.value.copy(
                        sensors = updatedSensors,
                        liveSensorData = currentLiveData
                    ))
                }
        }

        monitoringJobs[sensorType] = job
    }

    private fun stopIndividualSensorMonitoring(sensorType: Int) {
        monitoringJobs[sensorType]?.cancel()
        monitoringJobs.remove(sensorType)

        // Remove from live data
        val currentLiveData = state.value.liveSensorData.toMutableMap()
        currentLiveData.remove(sensorType)

        // Remove from selected types
        val newSelectedTypes = state.value.selectedSensorTypes - sensorType

        setState(state.value.copy(
            selectedSensorTypes = newSelectedTypes,
            liveSensorData = currentLiveData,
            isMonitoring = monitoringJobs.isNotEmpty() || allSensorsMonitoringJob != null,
            monitoringMode = when {
                monitoringJobs.isEmpty() && allSensorsMonitoringJob == null -> SensorsContract.MonitoringMode.NONE
                newSelectedTypes.size == state.value.sensors.size -> SensorsContract.MonitoringMode.ALL
                else -> SensorsContract.MonitoringMode.SELECTED
            }
        ))
    }

    private fun toggleSensorSelection(sensorType: Int) {
        val currentSelection = state.value.selectedSensorTypes
        val newSelection = if (currentSelection.contains(sensorType)) {
            currentSelection - sensorType
        } else {
            currentSelection + sensorType
        }

        setState(state.value.copy(selectedSensorTypes = newSelection))
    }

    private fun clearSelection() {
        setState(state.value.copy(selectedSensorTypes = emptySet()))
    }

    private fun stopMonitoring() {
        // Cancel all monitoring jobs
        allSensorsMonitoringJob?.cancel()
        allSensorsMonitoringJob = null

        monitoringJobs.values.forEach { it.cancel() }
        monitoringJobs.clear()

        setState(state.value.copy(
            isMonitoring = false,
            monitoringMode = SensorsContract.MonitoringMode.NONE,
            liveSensorData = emptyMap()
        ))
    }

    override fun onCleared() {
        super.onCleared()
        stopMonitoring()
    }
}