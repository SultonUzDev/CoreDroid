package com.sultonuzdev.coredroid.presentation.screens.network.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.components.NetworkStatusBadge
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun WifiCard(
    networkInfo: NetworkInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📡 Wi-Fi Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        // Status Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Status",
                style = CoreDroidTextStyles.InfoLabel,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            NetworkStatusBadge(
                status = if (networkInfo.isWifiConnected) "Connected" else "Disconnected",
                isConnected = networkInfo.isWifiConnected
            )
        }

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceXSmall))

        // Network Details
        InfoRow("SSID", networkInfo.wifiSsid)
        InfoRow("IP Address", networkInfo.ipAddress)
        InfoRow("MAC Address", networkInfo.macAddress)
        InfoRow("Frequency", networkInfo.wifiFrequency)
        InfoRow("Signal Strength", networkInfo.signalStrength)
    }
}