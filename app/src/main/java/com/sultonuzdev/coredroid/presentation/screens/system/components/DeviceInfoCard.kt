package com.sultonuzdev.coredroid.presentation.screens.system.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.domain.model.SystemInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun DeviceInfoCard(
    systemInfo: SystemInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "🏭 Device Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Manufacturer", systemInfo.manufacturer)
        InfoRow("Model", systemInfo.model)
        InfoRow("Brand", systemInfo.brand)
        InfoRow("Device", systemInfo.device)

        if (systemInfo.serialNumber != "Unknown") {
            InfoRow("Serial Number", systemInfo.serialNumber.take(8) + "...")
        }
    }
}