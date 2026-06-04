package com.sultonuzdev.coredroid.domain.model

data class NetworkInfo(
    val isWifiConnected: Boolean,
    val isMobileConnected: Boolean,
    val wifiFrequency: String,
    val wifiSignalStrength: String,
    val mobileSignalStrength: String,
    val linkSpeed: String,
    val ipAddress: String,
    val gatewayIp: String,
    val dnsServers: String,
    val subnetMask: String,
    val macAddress: String,
    val networkId: String,
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
            wifiFrequency = "N/A",
            wifiSignalStrength = "N/A",
            mobileSignalStrength = "N/A",
            linkSpeed = "N/A",
            ipAddress = "N/A",
            gatewayIp = "N/A",
            dnsServers = "N/A",
            subnetMask = "N/A",
            macAddress = "N/A",
            networkId = "N/A",
            networkType = "Unknown",
            operatorName = "Unknown",
            isBluetoothEnabled = false,
            isNfcEnabled = false,
            isGpsEnabled = false
        )
    }
}