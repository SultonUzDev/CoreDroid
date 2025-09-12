package com.sultonuzdev.coredroid.presentation.screens.system.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.domain.model.SystemInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions


@Composable
 fun SecurityInfoCard(
    systemInfo: SystemInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
    ) {
        Text(
            text = "🔐 Security Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Security Patch", systemInfo.securityPatch)
        InfoRow(
            "Root Access",
            if (systemInfo.isRooted) "⚠️ Detected" else "✅ Not Detected"
        )

        if (systemInfo.serialNumber != "Unknown") {
            InfoRow("Serial Number", systemInfo.serialNumber)
        }
    }
}