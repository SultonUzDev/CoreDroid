package com.sultonuzdev.coredroid.presentation.screens.sensors.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sultonuzdev.coredroid.core.utils.getSensorTypeName
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun SensorStatsCard(
    sensors: List<SensorInfo>,
    modifier: Modifier = Modifier
) {
    if (sensors.isEmpty()) return

    val activeSensors = sensors.count { it.values.isNotEmpty() }
    val sensorTypes = sensors.groupBy { getSensorTypeName(it.type) }.keys.size
    val avgPowerConsumption = sensors.map { it.power }.average()

    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "📊 Sensor Statistics",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CoreDroidDimensions.GridSpacing)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                StatItem("Total Sensors", sensors.size.toString())
                StatItem("Active Sensors", activeSensors.toString())
            }
            Column(modifier = Modifier.weight(1f)) {
                StatItem("Sensor Types", sensorTypes.toString())
                StatItem("Avg Power", "%.1f mA".format(avgPowerConsumption))
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.padding(vertical = CoreDroidDimensions.SpaceXSmall)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = CoreDroidColors.Info
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
