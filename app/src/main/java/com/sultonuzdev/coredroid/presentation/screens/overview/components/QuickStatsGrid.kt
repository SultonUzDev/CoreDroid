package com.sultonuzdev.coredroid.presentation.screens.overview.components


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.domain.model.DeviceOverview
import com.sultonuzdev.coredroid.presentation.components.OverviewCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun QuickStatsGrid(
    deviceOverview: DeviceOverview,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.OverviewCardSpacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CoreDroidDimensions.OverviewCardSpacing)
        ) {
            OverviewCard(
                value = "${deviceOverview.batteryLevel}%",
                label = "Battery Level",
                modifier = Modifier.weight(1f),
                gradient = if (deviceOverview.isCharging) {
                    CoreDroidColors.Gradients.SuccessGradient
                } else {
                    CoreDroidColors.Gradients.PrimaryGradient
                }
            )

            OverviewCard(
                value = deviceOverview.availableStorage,
                label = "Free Storage",
                modifier = Modifier.weight(1f),
                gradient = CoreDroidColors.Gradients.InfoGradient
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CoreDroidDimensions.OverviewCardSpacing)
        ) {
            OverviewCard(
                value = deviceOverview.availableRam,
                label = "Available RAM",
                modifier = Modifier.weight(1f),
                gradient = CoreDroidColors.Gradients.SecondaryGradient
            )

            OverviewCard(
                value = deviceOverview.androidVersion,
                label = "OS Version",
                modifier = Modifier.weight(1f),
                gradient = CoreDroidColors.Gradients.PrimaryGradient
            )
        }

        // Device model as full-width card
        OverviewCard(
            value = deviceOverview.deviceModel,
            label = "Device Model",
            modifier = Modifier.fillMaxWidth(),
            gradient = listOf(
                CoreDroidColors.Primary.copy(alpha = 0.8f),
                CoreDroidColors.Secondary.copy(alpha = 0.8f)
            )
        )
    }
}