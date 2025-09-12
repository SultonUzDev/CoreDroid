package com.sultonuzdev.coredroid.data.datasource


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import com.sultonuzdev.coredroid.core.extensions.hasPermission
import com.sultonuzdev.coredroid.core.utils.Constants
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.net.NetworkInterface

class NetworkDataSource(private val context: Context) {

    @SuppressLint("MissingPermission")
    fun getNetworkInfo(): Flow<NetworkInfo> = flow {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        // WiFi Info
        val wifiInfo = if (context.hasPermission(Constants.PERMISSION_ACCESS_WIFI_STATE)) {
            wifiManager.connectionInfo
        } else null

        val isWifiConnected =
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        val isMobileConnected =
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true

        // Get IP Address
        val ipAddress = getIpAddress()

        // Get MAC Address
        val macAddress = if (wifiInfo != null && isWifiConnected) {
            getMacAddress()
        } else null

        // Mobile network info
        val networkType = if (context.hasPermission(Constants.PERMISSION_READ_PHONE_STATE)) {
            getNetworkType(telephonyManager)
        } else "Unknown"

        val operatorName = if (context.hasPermission(Constants.PERMISSION_READ_PHONE_STATE)) {
            telephonyManager.networkOperatorName ?: "Unknown"
        } else "Unknown"

        emit(
            NetworkInfo(
                isWifiConnected = isWifiConnected,
                isMobileConnected = isMobileConnected,
                wifiSsid = wifiInfo?.ssid?.replace("\"", "") ?: "Not connected",
                wifiFrequency = if (wifiInfo != null) "${wifiInfo.frequency} MHz" else "N/A",
                signalStrength = if (wifiInfo != null) "${wifiInfo.rssi} dBm" else "N/A",
                ipAddress = ipAddress ?: "N/A",
                macAddress = macAddress ?: "N/A",
                networkType = networkType,
                operatorName = operatorName,
                isBluetoothEnabled = false, // Will implement separately
                isNfcEnabled = false, // Will implement separately
                isGpsEnabled = false // Will implement separately
            )
        )

    }.catch { e ->
        Timber.e(e, "Failed to get network info")
        emit(NetworkInfo.empty())
    }

    private fun getIpAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                val addresses = networkInterface.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress && address.address.size == 4) {
                        return address.hostAddress
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to get IP address")
        }
        return null
    }

    private fun getMacAddress(): String {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                if (networkInterface.name.equals("wlan0", ignoreCase = true)) {
                    val mac = networkInterface.hardwareAddress
                    if (mac != null) {
                        return mac.joinToString(":") { "%02x".format(it) }
                    }
                }
            }
            "02:00:00:00:00:00" // Default for privacy
        } catch (e: Exception) {
            "N/A"
        }
    }

    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    private fun getNetworkType(telephonyManager: TelephonyManager): String {
        return try {
            when (telephonyManager.dataNetworkType) {
                TelephonyManager.NETWORK_TYPE_1xRTT,
                TelephonyManager.NETWORK_TYPE_CDMA,
                TelephonyManager.NETWORK_TYPE_EDGE,
                TelephonyManager.NETWORK_TYPE_GPRS,
                TelephonyManager.NETWORK_TYPE_IDEN -> "2G"

                TelephonyManager.NETWORK_TYPE_EHRPD,
                TelephonyManager.NETWORK_TYPE_EVDO_0,
                TelephonyManager.NETWORK_TYPE_EVDO_A,
                TelephonyManager.NETWORK_TYPE_EVDO_B,
                TelephonyManager.NETWORK_TYPE_HSDPA,
                TelephonyManager.NETWORK_TYPE_HSPA,
                TelephonyManager.NETWORK_TYPE_HSPAP,
                TelephonyManager.NETWORK_TYPE_HSUPA,
                TelephonyManager.NETWORK_TYPE_UMTS -> "3G"

                TelephonyManager.NETWORK_TYPE_LTE -> "4G"

                20 -> "5G" // TelephonyManager.NETWORK_TYPE_NR

                else -> "Unknown"
            }
        } catch (e: Exception) {
            "Unknown"
        }
    }
}