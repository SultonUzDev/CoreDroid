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
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun AndroidInfoCard(
    systemInfo: SystemInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "🤖 Android Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Version", "Android ${systemInfo.androidVersion}")
        InfoRow("API Level", systemInfo.apiLevel.toString())
        InfoRow("Build Number", systemInfo.buildNumber)

        if (systemInfo.securityPatch.isNotEmpty() && systemInfo.securityPatch != "Unknown") {
            InfoRow("Security Patch", systemInfo.securityPatch)
        }
    }
}