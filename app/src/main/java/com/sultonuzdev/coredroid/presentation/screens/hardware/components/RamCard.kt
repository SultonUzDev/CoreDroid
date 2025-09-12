package com.sultonuzdev.coredroid.presentation.screens.hardware.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.core.extensions.formatBytes
import com.sultonuzdev.coredroid.domain.model.CpuInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.ProgressBar
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun RamCard(
    cpuInfo: CpuInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "💾 Memory Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Total RAM", cpuInfo.totalRam.formatBytes())

        ProgressBar(
            progress = cpuInfo.ramUsagePercentage / 100f,
            modifier = Modifier.padding(vertical = CoreDroidDimensions.SpaceSmall),
            progressColors = CoreDroidColors.Gradients.SuccessGradient
        )

        InfoRow("Available RAM", cpuInfo.availableRam.formatBytes())
        InfoRow("Used RAM", "${cpuInfo.usedRam.formatBytes()} (${cpuInfo.ramUsagePercentage}%)")
    }
}