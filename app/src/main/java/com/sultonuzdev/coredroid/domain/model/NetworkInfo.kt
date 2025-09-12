package com.sultonuzdev.coredroid.domain.model

data class NetworkInfo(
    val isWifiConnected: Boolean,
    val isMobileConnected: Boolean,
    val wifiSsid: String,
    val wifiFrequency: String,
    val signalStrength: String,
    val ipAddress: String,
    val macAddress: String,
    val networkType: String,
    val operatorName: String,
    val isBluetoothEnabled: Boolean,
    val isNfcEnabled: Boolean,
    val isGpsEnabled: Boolean
) {
    companion object {
        fun empty() = NetworkInfo(
            isWifiConnected = false,
            isMobileConnected = false,
            wifiSsid = "Not connected",
            wifiFrequency = "N/A",
            signalStrength = "N/A",
            ipAddress = "N/A",
            macAddress = "N/A",
            networkType = "Unknown",
            operatorName = "Unknown",
            isBluetoothEnabled = false,
            isNfcEnabled = false,
            isGpsEnabled = false
        )
    }
}