package com.sultonuzdev.coredroid.presentation.screens.sensors.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sultonuzdev.coredroid.presentation.screens.sensors.SensorsContract
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTheme

@Composable
fun CompactSensorMonitor(
    selectedSensorCount: Int,
    activeSensorCount: Int,
    isMonitoring: Boolean,
    onStartMonitoringSelected: () -> Unit,
    onStopMonitoring: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardColor by animateColorAsState(
        targetValue = when {
            isMonitoring -> CoreDroidColors.Success.copy(alpha = 0.1f)
            selectedSensorCount > 0 -> CoreDroidColors.Info.copy(alpha = 0.1f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        },
        animationSpec = tween(300),
        label = "card_color"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Compact header with status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sensor Control",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Compact status
                CompactStatusBadge(
                    isMonitoring = isMonitoring,
                    activeCount = activeSensorCount
                )
            }

            // Selection info (compact)
            if (selectedSensorCount > 0 || isMonitoring) {
                Spacer(modifier = Modifier.height(8.dp))
                CompactSelectionInfo(
                    selectedCount = selectedSensorCount,
                    activeCount = activeSensorCount,
                    isMonitoring = isMonitoring,
                    onClearSelection = onClearSelection
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Compact control buttons
            if (isMonitoring) {
                CompactStopButton(onStopMonitoring = onStopMonitoring)
            } else if (selectedSensorCount > 0) {
                CompactStartButton(
                    selectedCount = selectedSensorCount,
                    onStartMonitoringSelected = onStartMonitoringSelected
                )
            } else {
                CompactHelpText()
            }
        }
    }
}

@Composable
private fun CompactStatusBadge(
    isMonitoring: Boolean,
    activeCount: Int
) {
    val (text, color) = when {
        isMonitoring -> "$activeCount Active" to CoreDroidColors.Success
        else -> "Tap to Select" to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = color
        )
    }
}

@Composable
private fun CompactSelectionInfo(
    selectedCount: Int,
    activeCount: Int,
    isMonitoring: Boolean,
    onClearSelection: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isMonitoring) {
                "$activeCount sensors monitoring"
            } else {
                "$selectedCount selected"
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        if (selectedCount > 0 && !isMonitoring) {
            OutlinedButton(
                onClick = onClearSelection,
                modifier = Modifier.height(32.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Clear",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
private fun CompactStartButton(
    selectedCount: Int,
    onStartMonitoringSelected: () -> Unit
) {
    Button(
        onClick = onStartMonitoringSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CoreDroidColors.Info
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Start Monitoring ($selectedCount)",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun CompactStopButton(
    onStopMonitoring: () -> Unit
) {
    Button(
        onClick = onStopMonitoring,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Stop,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Stop Monitoring",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun CompactHelpText() {
    Text(
        text = "👆 Tap sensors below to select and start monitoring",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

// Previews
@Preview(showBackground = true)
@Composable
fun CompactSensorMonitorIdlePreview() {
    CoreDroidTheme {
        CompactSensorMonitor(
            selectedSensorCount = 0,
            activeSensorCount = 0,
            isMonitoring = false,
            onStartMonitoringSelected = { },
            onStopMonitoring = { },
            onClearSelection = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompactSensorMonitorSelectedPreview() {
    CoreDroidTheme {
        CompactSensorMonitor(
            selectedSensorCount = 3,
            activeSensorCount = 0,
            isMonitoring = false,
            onStartMonitoringSelected = { },
            onStopMonitoring = { },
            onClearSelection = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompactSensorMonitorActivePreview() {
    CoreDroidTheme {
        CompactSensorMonitor(
            selectedSensorCount = 3,
            activeSensorCount = 3,
            isMonitoring = true,
            onStartMonitoringSelected = { },
            onStopMonitoring = { },
            onClearSelection = { }
        )
    }
}