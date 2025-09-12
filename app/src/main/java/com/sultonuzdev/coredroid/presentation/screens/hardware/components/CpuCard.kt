package com.sultonuzdev.coredroid.presentation.screens.hardware.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.core.extensions.formatFrequency
import com.sultonuzdev.coredroid.domain.model.CpuInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun CpuCard(
    cpuInfo: CpuInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "🧠 CPU Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Processor", cpuInfo.name)
        InfoRow("Architecture", cpuInfo.architecture)
        InfoRow("Cores", "${cpuInfo.coreCount} cores")
        InfoRow("Max Frequency", cpuInfo.maxFrequency.toInt().formatFrequency())

        if (cpuInfo.currentFrequency > 0) {
            InfoRow("Current Frequency", cpuInfo.currentFrequency.toInt().formatFrequency())
        }

        if (cpuInfo.cpuUsage > 0) {
            InfoRow("Usage", "%.1f%%".format(cpuInfo.cpuUsage))
        }
    }
}