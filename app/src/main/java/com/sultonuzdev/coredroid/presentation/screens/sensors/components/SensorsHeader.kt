package com.sultonuzdev.coredroid.presentation.screens.sensors.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun SensorsHeader(
    sensorCount: Int,
    isMonitoring: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
        
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(CoreDroidDimensions.PaddingSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceSmall)
                ) {
                    Text(
                        text = "📡 Available Sensors",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (isMonitoring) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = CoreDroidColors.Success,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        )
                    }
                }

                Text(
                    text = if (sensorCount > 0) {
                        "Found $sensorCount sensors on this device"
                    } else {
                        "No sensors detected"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (isMonitoring) {
                    Text(
                        text = "Real-time monitoring active",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = CoreDroidColors.Success
                    )
                }
            }

            IconButton(
                onClick = onRefresh,
                enabled = !isMonitoring
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh sensors",
                    tint = if (isMonitoring) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
        }
    }
}