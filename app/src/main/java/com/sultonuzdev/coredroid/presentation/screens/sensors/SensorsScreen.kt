package com.sultonuzdev.coredroid.presentation.screens.sensors

import android.hardware.Sensor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.presentation.common.ErrorState
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.screens.sensors.components.SensorsHeader
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SensorsScreen(
    onNavigateToSensorDetails: (Int, String) -> Unit,
    viewModel: SensorsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    SensorsScreenContent(
        state = state,
        onNavigateToSensorDetails = onNavigateToSensorDetails,
        onRefresh = { viewModel.handleIntent(SensorsContract.Intent.RefreshSensors) }
    )
}

@Composable
fun SensorsScreenContent(
    state: SensorsContract.State,
    onNavigateToSensorDetails: (Int, String) -> Unit,
    onRefresh: () -> Unit
) {


    Column {
        TopAppBar(modifier = Modifier, currentTitle = "Sensors")
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Loading sensors...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            state.error != null -> {
                ErrorState(
                    message = state.error,
                    onRetry = onRefresh
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header with sensor count and refresh
                    SensorsHeader(
                        sensorCount = state.sensors.size,
                        isMonitoring = false, // No global monitoring in this version
                        onRefresh = onRefresh
                    )

                    // Info card explaining the new flow
                    InfoCard()

                    // Simple sensors list for navigation
                    SimpleSensorsList(
                        sensors = state.sensors,
                        onSensorClick = { sensorType, sensorName ->
                            onNavigateToSensorDetails(sensorType, sensorName)
                        },
                        modifier = Modifier.height(500.dp)
                    )
                }
            }
        }

    }
}

@Composable
private fun InfoCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CoreDroidColors.Info.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💡",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Tap any sensor to view detailed information and start real-time monitoring",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun SimpleSensorsList(
    sensors: List<SensorInfo>,
    onSensorClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Header
        Text(
            text = "Available Sensors (${sensors.size})",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Sensors list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(
                items = sensors,
                key = { sensor -> "${sensor.name}_${sensor.type}" }
            ) { sensor ->
                SimpleSensorCard(
                    sensorInfo = sensor,
                    onClick = { onSensorClick(sensor.type, sensor.name) }
                )
            }
        }
    }
}

@Composable
private fun SimpleSensorCard(
    sensorInfo: SensorInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sensor info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sensorInfo.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

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

                    if (sensorInfo.vendor.isNotBlank()) {
                        Text(
                            text = "•",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = "${String.format("%.1f", sensorInfo.power)} mA",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = if (sensorInfo.power > 1.0f) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                // Sensor type info
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Type: ${getSensorTypeDisplayName(sensorInfo.type)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Arrow indicator
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Helper function to get readable sensor type names
private fun getSensorTypeDisplayName(sensorType: Int): String {
    return when (sensorType) {
        Sensor.TYPE_ACCELEROMETER -> "Accelerometer"
        Sensor.TYPE_GYROSCOPE -> "Gyroscope"
        Sensor.TYPE_MAGNETIC_FIELD -> "Magnetometer"
        Sensor.TYPE_GRAVITY -> "Gravity"
        Sensor.TYPE_LINEAR_ACCELERATION -> "Linear Acceleration"
        Sensor.TYPE_ROTATION_VECTOR -> "Rotation Vector"
        Sensor.TYPE_ORIENTATION -> "Orientation"
        Sensor.TYPE_PRESSURE -> "Pressure"
        Sensor.TYPE_TEMPERATURE -> "Temperature"
        Sensor.TYPE_AMBIENT_TEMPERATURE -> "Ambient Temperature"
        Sensor.TYPE_LIGHT -> "Light"
        Sensor.TYPE_PROXIMITY -> "Proximity"
        Sensor.TYPE_RELATIVE_HUMIDITY -> "Humidity"
        Sensor.TYPE_STEP_COUNTER -> "Step Counter"
        Sensor.TYPE_STEP_DETECTOR -> "Step Detector"
        Sensor.TYPE_GAME_ROTATION_VECTOR -> "Game Rotation Vector"
        Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> "Geomagnetic Rotation"
        else -> "Unknown ($sensorType)"
    }
}

// Sample sensor data for preview
object SampleSensorData {
    val mockSensors = listOf(
        SensorInfo(
            name = "LSM6DSL Accelerometer",
            type = Sensor.TYPE_ACCELEROMETER,
            vendor = "STMicroelectronics",
            version = 1,
            power = 0.25f,
            resolution = 0.0023956299f,
            maximumRange = 78.4532f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "LSM6DSL Gyroscope",
            type = Sensor.TYPE_GYROSCOPE,
            vendor = "STMicroelectronics",
            version = 1,
            power = 0.55f,
            resolution = 0.0010681152f,
            maximumRange = 34.906586f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "AK09918 Magnetometer",
            type = Sensor.TYPE_MAGNETIC_FIELD,
            vendor = "AKM",
            version = 1,
            power = 1.1f,
            resolution = 0.15f,
            maximumRange = 4912.0f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "TMD3725 Light Sensor",
            type = Sensor.TYPE_LIGHT,
            vendor = "AMS-TAOS USA Inc.",
            version = 1,
            power = 0.09f,
            resolution = 1.0f,
            maximumRange = 65535.0f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "TMD3725 Proximity Sensor",
            type = Sensor.TYPE_PROXIMITY,
            vendor = "AMS-TAOS USA Inc.",
            version = 1,
            power = 0.09f,
            resolution = 1.0f,
            maximumRange = 5.0f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "BMP380 Pressure Sensor",
            type = Sensor.TYPE_PRESSURE,
            vendor = "Bosch",
            version = 1,
            power = 0.003f,
            resolution = 0.0039f,
            maximumRange = 1260.0f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "BMP380 Temperature Sensor",
            type = Sensor.TYPE_AMBIENT_TEMPERATURE,
            vendor = "Bosch",
            version = 1,
            power = 0.003f,
            resolution = 0.01f,
            maximumRange = 85.0f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        ),
        SensorInfo(
            name = "Step Counter",
            type = Sensor.TYPE_STEP_COUNTER,
            vendor = "Google Inc.",
            version = 1,
            power = 0.0f,
            resolution = 1.0f,
            maximumRange = 2147483647.0f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        )
    )
}

// Preview composables
@Preview(showBackground = true, heightDp = 800)
@Composable
fun SensorsScreenPreview() {
    CoreDroidTheme {
        SensorsScreenContent(
            state = SensorsContract.State(
                isLoading = false,
                sensors = SampleSensorData.mockSensors,
                error = null,
                isMonitoring = false,
                monitoringMode = SensorsContract.MonitoringMode.NONE,
                selectedSensorTypes = emptySet(),
                liveSensorData = emptyMap()
            ),
            onNavigateToSensorDetails = { _, _ -> /* Preview */ },
            onRefresh = { /* Preview */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SensorsScreenLoadingPreview() {
    CoreDroidTheme {
        SensorsScreenContent(
            state = SensorsContract.State(
                isLoading = true,
                sensors = emptyList(),
                error = null,
                isMonitoring = false,
                monitoringMode = SensorsContract.MonitoringMode.NONE,
                selectedSensorTypes = emptySet(),
                liveSensorData = emptyMap()
            ),
            onNavigateToSensorDetails = { _, _ -> /* Preview */ },
            onRefresh = { /* Preview */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SensorsScreenErrorPreview() {
    CoreDroidTheme {
        SensorsScreenContent(
            state = SensorsContract.State(
                isLoading = false,
                sensors = emptyList(),
                error = "Failed to access sensor hardware. Please check device permissions and try again.",
                isMonitoring = false,
                monitoringMode = SensorsContract.MonitoringMode.NONE,
                selectedSensorTypes = emptySet(),
                liveSensorData = emptyMap()
            ),
            onNavigateToSensorDetails = { _, _ -> /* Preview */ },
            onRefresh = { /* Preview */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleSensorCardPreview() {
    CoreDroidTheme {
        SimpleSensorCard(
            sensorInfo = SampleSensorData.mockSensors[0],
            onClick = { /* Preview */ }
        )
    }
}