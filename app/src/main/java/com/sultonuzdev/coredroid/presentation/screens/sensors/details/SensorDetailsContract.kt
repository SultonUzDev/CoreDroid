package com.sultonuzdev.coredroid.presentation.screens.sensors.details

import com.sultonuzdev.coredroid.core.base.MviContract
import com.sultonuzdev.coredroid.domain.model.SensorInfo

object SensorDetailsContract : MviContract {

    data class State(
        val sensorInfo: SensorInfo? = null,
        val isMonitoring: Boolean = false,
        val error: String? = null
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object StartMonitoring : Intent()
        object StopMonitoring : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
        data class ShowInfo(val message: String) : Effect()
    }
}