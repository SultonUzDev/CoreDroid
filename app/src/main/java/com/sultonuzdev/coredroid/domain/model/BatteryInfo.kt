package com.sultonuzdev.coredroid.domain.model


data class BatteryInfo(
 val level: Int,
 val isCharging: Boolean,
 val chargingSource: String,
 val health: String,
 val temperature: Float,
 val voltage: Float,
 val cycleCount: Int?
) {
 companion object {
  fun empty() = BatteryInfo(
   level = 0,
   isCharging = false,
   chargingSource = "Unknown",
   health = "Unknown",
   temperature = 0f,
   voltage = 0f,
   cycleCount = null
  )
 }
}
