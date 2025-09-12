package com.sultonuzdev.coredroid.presentation.screens.sensors.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sultonuzdev.coredroid.core.utils.DateTimeUtils.formatTimestamp
import com.sultonuzdev.coredroid.core.utils.formatResolution
import com.sultonuzdev.coredroid.core.utils.getAccuracyText
import com.sultonuzdev.coredroid.core.utils.getSensorTypeName
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorDetailsScreen(
    sensorType: Int,
    sensorName: String,
    onNavigateBack: () -> Unit,
    viewModel: SensorDetailsViewModel = koinViewModel { parametersOf(sensorType) }
) {
    val state by viewModel.state.collectAsState()

    // Stop monitoring when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.handleIntent(SensorDetailsContract.Intent.StopMonitoring)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                currentTitle = sensorName,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
        },
        bottomBar = {
            MonitoringControlBar(
                isMonitoring = state.isMonitoring,
                onStartMonitoring = {
                    viewModel.handleIntent(SensorDetailsContract.Intent.StartMonitoring)
                },
                onStopMonitoring = {
                    viewModel.handleIntent(SensorDetailsContract.Intent.StopMonitoring)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isMonitoring) {
                Text(
                    text = "LIVE MONITORING",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = CoreDroidColors.Success
                )
            }

            state.sensorInfo?.let { sensorInfo ->
                // Live data section
                if (state.isMonitoring && sensorInfo.values.isNotEmpty()) {
                    LiveDataSection(sensorInfo = sensorInfo)
                }

                // Sensor specifications
                SensorSpecificationsSection(sensorInfo = sensorInfo)

                // Technical details
                TechnicalDetailsSection(sensorInfo = sensorInfo)

                // Monitoring status
                MonitoringStatusSection(
                    isMonitoring = state.isMonitoring,
                    sensorInfo = sensorInfo
                )
            }

            if (state.error != null) {
                ErrorSection(error = state.error!!)
            }
        }
    }
}

// Preview composables
@Preview(showBackground = true, heightDp = 800)
@Composable
fun SensorDetailsScreenIdlePreview() {
    CoreDroidTheme {
        val mockSensorInfo = SensorInfo(
            name = "LSM6DSL Accelerometer",
            type = android.hardware.Sensor.TYPE_ACCELEROMETER,
            vendor = "STMicroelectronics",
            version = 1,
            power = 0.25f,
            resolution = 0.0023956299f,
            maximumRange = 78.4532f,
            values = emptyList(),
            accuracy = 0,
            timestamp = 0L
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SensorSpecificationsSection(sensorInfo = mockSensorInfo)
            TechnicalDetailsSection(sensorInfo = mockSensorInfo)
            MonitoringStatusSection(isMonitoring = false, sensorInfo = mockSensorInfo)
            MonitoringControlBar(
                isMonitoring = false,
                onStartMonitoring = { },
                onStopMonitoring = { }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun SensorDetailsScreenLivePreview() {
    CoreDroidTheme {
        val mockSensorInfo = SensorInfo(
            name = "LSM6DSL Accelerometer",
            type = android.hardware.Sensor.TYPE_ACCELEROMETER,
            vendor = "STMicroelectronics",
            version = 1,
            power = 0.25f,
            resolution = 0.0023956299f,
            maximumRange = 78.4532f,
            values = listOf(-0.52f, 9.81f, 0.23f),
            accuracy = 3,
            timestamp = System.currentTimeMillis()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LiveDataSection(sensorInfo = mockSensorInfo)
            SensorSpecificationsSection(sensorInfo = mockSensorInfo)
            TechnicalDetailsSection(sensorInfo = mockSensorInfo)
            MonitoringStatusSection(isMonitoring = true, sensorInfo = mockSensorInfo)
            MonitoringControlBar(
                isMonitoring = true,
                onStartMonitoring = { },
                onStopMonitoring = { }
            )
        }
    }
}

@Composable
private fun LiveDataSection(sensorInfo: SensorInfo) {
    val pulseScale by animateFloatAsState(
        targetValue = 1.05f,
        animationSpec = tween(1000),
        label = "pulse"
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = CoreDroidColors.Success.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header with live indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Real-Time Data",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .scale(pulseScale)
                            .background(
                                color = CoreDroidColors.Success,
                                shape = CircleShape
                            )
                    )
                    Text(
                        text = "LIVE",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = CoreDroidColors.Success
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current values
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Current Reading:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = sensorInfo.formattedValues,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = CoreDroidColors.Success
                    )

                    if (sensorInfo.accuracy >= 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Accuracy:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = getAccuracyText(sensorInfo.accuracy),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = when (sensorInfo.accuracy) {
                                    3 -> CoreDroidColors.Success
                                    2 -> Color(0xFFFF9800)
                                    1 -> Color(0xFFFF5722)
                                    else -> MaterialTheme.colorScheme.error
                                }
                            )
                        }
                    }

                    if (sensorInfo.timestamp > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Last Update: ${formatTimestamp(sensorInfo.timestamp)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SensorSpecificationsSection(sensorInfo: SensorInfo) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Sensor Specifications",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SpecificationItem("Name", sensorInfo.name)
                SpecificationItem("Type", getSensorTypeName(sensorInfo.type))
                SpecificationItem("Vendor", sensorInfo.vendor)
                SpecificationItem("Version", "v${sensorInfo.version}")
                SpecificationItem(
                    "Power Consumption",
                    "${String.format("%.3f", sensorInfo.power)} mA",
                    isHighlight = sensorInfo.power > 1.0f
                )
                SpecificationItem("Resolution", formatResolution(sensorInfo.resolution))
                SpecificationItem(
                    "Maximum Range",
                    "${String.format("%.2f", sensorInfo.maximumRange)} units"
                )
            }
        }
    }
}

@Composable
private fun TechnicalDetailsSection(sensorInfo: SensorInfo) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Technical Details",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SpecificationItem("Sensor Type ID", sensorInfo.type.toString())
                SpecificationItem(
                    "Data Dimensions",
                    when (sensorInfo.type) {
                        1, 2, 4, 9, 10, 11, 15, 20 -> "3-axis (X, Y, Z)"
                        5, 6, 8, 12, 13 -> "Single value"
                        else -> "Variable"
                    }
                )
                SpecificationItem(
                    "Typical Use Cases",
                    when (sensorInfo.type) {
                        1 -> "Motion detection, orientation, gaming"
                        2 -> "Device orientation, compass apps"
                        4 -> "Rotation detection, VR/AR"
                        5 -> "Ambient light adjustment"
                        6 -> "Pressure monitoring, altitude"
                        8 -> "Proximity detection, screen control"
                        else -> "Sensor monitoring applications"
                    }
                )
            }
        }
    }
}

@Composable
private fun MonitoringStatusSection(
    isMonitoring: Boolean,
    sensorInfo: SensorInfo
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isMonitoring) {
                CoreDroidColors.Info.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Monitoring Status",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isMonitoring) "ACTIVE" else "INACTIVE",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (isMonitoring) CoreDroidColors.Success else MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = if (isMonitoring) {
                            "Receiving real-time data"
                        } else {
                            "Tap start button to begin monitoring"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = if (isMonitoring) Icons.Default.Circle else Icons.Default.Circle,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = if (isMonitoring) CoreDroidColors.Success else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ErrorSection(error: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = error,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SpecificationItem(
    label: String,
    value: String,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = if (isHighlight) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun MonitoringControlBar(
    isMonitoring: Boolean,
    onStartMonitoring: () -> Unit,
    onStopMonitoring: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            if (isMonitoring) {
                Button(
                    onClick = onStopMonitoring,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Stop Monitoring",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            } else {
                Button(
                    onClick = onStartMonitoring,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoreDroidColors.Success
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Start Monitoring",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isMonitoring) {
                    "Monitoring will stop automatically when you leave this screen"
                } else {
                    "Tap to start real-time sensor monitoring"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}