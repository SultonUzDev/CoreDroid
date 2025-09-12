package com.sultonuzdev.coredroid.presentation.screens.overview.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.core.extensions.formatTemperature
import com.sultonuzdev.coredroid.domain.model.BatteryInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.components.ProgressBar
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun BatteryCard(
    batteryInfo: BatteryInfo,
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
                text = "🔋 Battery Status",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (batteryInfo.isCharging) {
                Text(
                    text = "⚡ Charging",
                    style = MaterialTheme.typography.bodySmall,
                    color = CoreDroidColors.Success
                )
            }
        }

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Level",
                style = CoreDroidTextStyles.InfoLabel,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${batteryInfo.level}%",
                style = CoreDroidTextStyles.InfoValue,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        ProgressBar(
            progress = batteryInfo.level / 100f,
            modifier = Modifier.padding(vertical = CoreDroidDimensions.SpaceSmall),
            progressColors = CoreDroidColors.Gradients.SuccessGradient
        )

        InfoRow("Health", batteryInfo.health)
        InfoRow("Temperature", batteryInfo.temperature.formatTemperature())
        InfoRow("Charging Source", batteryInfo.chargingSource)

        if (batteryInfo.voltage > 0) {
            InfoRow("Voltage", "%.2f V".format(batteryInfo.voltage))
        }
    }
}