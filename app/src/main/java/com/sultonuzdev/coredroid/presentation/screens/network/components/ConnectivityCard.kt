package com.sultonuzdev.coredroid.presentation.screens.network.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun ConnectivityCard(
    networkInfo: NetworkInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
        
    ) {
        Text(
            text = "🌐 Connectivity",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CoreDroidDimensions.GridSpacing)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                InfoRow("Bluetooth", if (networkInfo.isBluetoothEnabled) "Enabled" else "Disabled")
                InfoRow("GPS", if (networkInfo.isGpsEnabled) "Active" else "Inactive")
            }
            Column(modifier = Modifier.weight(1f)) {
                InfoRow("NFC", if (networkInfo.isNfcEnabled) "Enabled" else "Disabled")
                InfoRow("Hotspot", "Off")
            }
        }
    }
}