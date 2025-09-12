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
fun KernelInfoCard(
    systemInfo: SystemInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
        
    ) {
        Text(
            text = "⚙️ System Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Kernel Version", systemInfo.kernelVersion)
        InfoRow("Bootloader", systemInfo.bootloader)

        if (systemInfo.baseband != "Unknown" && systemInfo.baseband.isNotEmpty()) {
            InfoRow("Baseband", systemInfo.baseband)
        }

        InfoRow(
            "Root Status",
            if (systemInfo.isRooted) "⚠️ Rooted" else "✅ Not Rooted"
        )
    }
}