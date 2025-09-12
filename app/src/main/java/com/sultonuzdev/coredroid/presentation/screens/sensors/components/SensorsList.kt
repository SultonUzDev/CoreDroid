package com.sultonuzdev.coredroid.presentation.screens.sensors.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.presentation.screens.sensors.SensorsContract
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun CompactSensorsList(
    sensors: List<SensorInfo>,
    liveSensorData: Map<Int, SensorInfo>,
    selectedSensorTypes: Set<Int>,
    onSensorClick: (Int) -> Unit,
    onSensorSelectionToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Compact header
        CompactSensorListHeader(
            totalSensors = sensors.size,
            activeSensors = liveSensorData.size,
            selectedSensors = selectedSensorTypes.size
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Sensors list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(
                items = sensors,
                key = { sensor -> "${sensor.name}_${sensor.type}" }
            ) { sensor ->
                val isSelected = selectedSensorTypes.contains(sensor.type)
                val isLive = liveSensorData.containsKey(sensor.type)
                val liveSensor = liveSensorData[sensor.type] ?: sensor

                CompactSensorCard(
                    sensorInfo = liveSensor,
                    isSelected = isSelected,
                    isLive = isLive,
                    onToggleSelection = { onSensorSelectionToggle(sensor.type) },
                    onStartMonitoring = { onSensorClick(sensor.type) }
                )
            }
        }
    }
}

@Composable
private fun CompactSensorListHeader(
    totalSensors: Int,
    activeSensors: Int,
    selectedSensors: Int
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CompactStatItem(
                label = "Total",
                value = totalSensors.toString(),
                color = MaterialTheme.colorScheme.onSurface
            )
            CompactStatItem(
                label = "Live",
                value = activeSensors.toString(),
                color = if (activeSensors > 0) CoreDroidColors.Success else MaterialTheme.colorScheme.onSurfaceVariant
            )
            CompactStatItem(
                label = "Selected",
                value = selectedSensors.toString(),
                color = if (selectedSensors > 0) CoreDroidColors.Info else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CompactStatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CompactSensorCard(
    sensorInfo: SensorInfo,
    isSelected: Boolean,
    isLive: Boolean,
    onToggleSelection: () -> Unit,
    onStartMonitoring: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            isLive -> CoreDroidColors.Success
            isSelected -> CoreDroidColors.Info
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        },
        animationSpec = tween(300),
        label = "border_color"
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            isLive -> CoreDroidColors.Success.copy(alpha = 0.05f)
            isSelected -> CoreDroidColors.Info.copy(alpha = 0.05f)
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(300),
        label = "container_color"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (isLive) {
                    // If already live, stop monitoring this sensor
                    onStartMonitoring()
                } else {
                    // If not live, start monitoring this sensor
                    onStartMonitoring()
                }
            },
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(
            width = if (isSelected || isLive) 1.5.dp else 0.5.dp,
            color = borderColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        if (isLive) {
            // Show real-time data
            LiveSensorContent(
                sensorInfo = sensorInfo,
            )
        } else {
            // Show static sensor info
            StaticSensorContent(
                sensorInfo = sensorInfo,
                isSelected = isSelected,
                onToggleSelection = onToggleSelection
            )
        }
    }
}

@Composable
private fun LiveSensorContent(
    sensorInfo: SensorInfo,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Header with live indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sensorInfo.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = sensorInfo.vendor,
                    style = MaterialTheme.typography.bodySmall,
                    color = CoreDroidColors.Info,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Live badge
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = CoreDroidColors.Success.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        modifier = Modifier.size(8.dp),
                        tint = CoreDroidColors.Success
                    )
                    Text(
                        text = "LIVE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        ),
                        color = CoreDroidColors.Success
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Live values display
        Card(
            colors = CardDefaults.cardColors(
                containerColor = CoreDroidColors.Success.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Current Reading:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = sensorInfo.formattedValues,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = CoreDroidColors.Success
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun StaticSensorContent(
    sensorInfo: SensorInfo,
    isSelected: Boolean,
    onToggleSelection: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Selection indicator (clickable)
        Icon(
            imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clickable { onToggleSelection() },
            tint = if (isSelected) CoreDroidColors.Info else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Sensor info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = sensorInfo.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = sensorInfo.vendor,
                    style = MaterialTheme.typography.bodySmall,
                    color = CoreDroidColors.Info,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "•",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "${String.format("%.1f", sensorInfo.power)} mA",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (sensorInfo.power > 1.0f) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }

        // Tap to monitor hint
        Text(
            text = "TAP",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}