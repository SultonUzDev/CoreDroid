package com.sultonuzdev.coredroid.core.navigation

import kotlinx.serialization.Serializable

@Serializable
 object OverviewRoute

@Serializable
 object HardwareRoute

@Serializable
 object SystemRoute

@Serializable
 object NetworkRoute

@Serializable
object NetworkMonitoringRoute

@Serializable
 object SensorsRoute

@Serializable
data class SensorDetailsRoute(
 val sensorType: Int,
 val sensorName: String
)

