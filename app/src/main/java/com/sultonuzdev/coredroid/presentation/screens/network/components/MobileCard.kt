package com.sultonuzdev.coredroid.presentation.screens.network.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sultonuzdev.coredroid.domain.model.NetworkInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.components.NetworkStatusBadge
import com.sultonuzdev.coredroid.presentation.screens.network.NetworkContract
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun MobileCard(
    networkInfo: NetworkInfo,
    phonePermissionState: NetworkContract.PhonePermissionState,
    modifier: Modifier = Modifier,
    onRequestPermission: () -> Unit = {},
    onOpenAppSettings: () -> Unit = {}
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "📶 Mobile Network",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

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
                status = if (networkInfo.isMobileConnected) "Connected" else "Disconnected",
                isConnected = networkInfo.isMobileConnected
            )
        }

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceXSmall))

        when (phonePermissionState) {
            is NetworkContract.PhonePermissionState.Granted -> {
                // Show full mobile network information
                InfoRow("Network Type", networkInfo.networkType)
                InfoRow("Operator", networkInfo.operatorName)
                InfoRow("Signal Strength", networkInfo.signalStrength)
            }

            is NetworkContract.PhonePermissionState.PermanentlyDenied -> {
                // Show limited info and settings redirect
                InfoRow("Network Type", "Permission needed")
                InfoRow("Operator", "Permission needed")
                InfoRow("Signal Strength", networkInfo.signalStrength)

                Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceSmall)
                ) {
                    Text(
                        text = "Phone permission is permanently denied. Please enable it in app settings to view detailed mobile network information.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onOpenAppSettings,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Open App Settings", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            else -> {
                // Show limited info and permission request
                InfoRow("Network Type", "Permission needed")
                InfoRow("Operator", "Permission needed")
                InfoRow("Signal Strength", networkInfo.signalStrength)

                Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceSmall)
                ) {
                    Text(
                        text = "Phone permission is needed to display detailed mobile network information (network type and carrier).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onRequestPermission,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Grant Phone Permission",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
