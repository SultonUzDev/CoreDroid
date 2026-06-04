package com.sultonuzdev.coredroid.domain.model

data class HardwareFeaturesInfo(
    val hasNfc: Boolean,
    val hasBluetooth: Boolean,
    val hasBluetoothLe: Boolean,
    val hasWifi: Boolean,
    val hasWifiDirect: Boolean,
    val hasWifi5Ghz: Boolean,
    val hasGps: Boolean,
    val hasFingerprint: Boolean,
    val hasFaceUnlock: Boolean,
    val hasAccelerometer: Boolean,
    val hasGyroscope: Boolean,
    val hasCompass: Boolean,
    val hasProximitySensor: Boolean,
    val hasLightSensor: Boolean,
    val hasBarometer: Boolean,
    val hasPedometerSensor: Boolean,
    val hasHeartRateSensor: Boolean,
    val hasIrBlaster: Boolean,
    val hasUsbHost: Boolean,
    val hasUsbAccessory: Boolean,
    val microphoneCount: Int,
    val bluetoothVersion: String,
    val openGlEsVersion: String
) {
    companion object {
        fun empty() = HardwareFeaturesInfo(
            hasNfc = false,
            hasBluetooth = false,
            hasBluetoothLe = false,
            hasWifi = false,
            hasWifiDirect = false,
            hasWifi5Ghz = false,
            hasGps = false,
            hasFingerprint = false,
            hasFaceUnlock = false,
            hasAccelerometer = false,
            hasGyroscope = false,
            hasCompass = false,
            hasProximitySensor = false,
            hasLightSensor = false,
            hasBarometer = false,
            hasPedometerSensor = false,
            hasHeartRateSensor = false,
            hasIrBlaster = false,
            hasUsbHost = false,
            hasUsbAccessory = false,
            microphoneCount = 0,
            bluetoothVersion = "Unknown",
            openGlEsVersion = "Unknown"
        )
    }
}
