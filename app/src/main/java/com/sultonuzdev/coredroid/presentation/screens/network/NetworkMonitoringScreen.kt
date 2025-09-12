package com.sultonuzdev.coredroid.presentation.screens.network

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NetworkMonitoringScreen(
    onNavigateBack: () -> Unit,
    viewModel: NetworkViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Handle effects (unchanged)
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NetworkContract.Effect.ShowError -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_LONG
                ).show()

                is NetworkContract.Effect.ShowMonitoringStarted -> Toast.makeText(
                    context,
                    "Monitoring started",
                    Toast.LENGTH_SHORT
                ).show()

                is NetworkContract.Effect.ShowMonitoringStopped -> Toast.makeText(
                    context,
                    "Monitoring stopped",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                currentTitle = "Network Monitoring",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (state.isMonitoring) {
                        viewModel.handleIntent(NetworkContract.Intent.StopMonitoring)
                    } else {
                        viewModel.handleIntent(NetworkContract.Intent.StartMonitoring)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CoreDroidDimensions.ScreenPadding)
            ) {
                Text(if (state.isMonitoring) "Stop Monitoring" else "Start Monitoring")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = CoreDroidDimensions.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceLarge)
        ) {
            item {
                Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceLarge))
            }
            // 1. Status Banner
            item {
                NetworkMonitoringBanner(isMonitoring = state.isMonitoring)
            }
            // 2. Quick Info Cards
            state.networkInfoState.getDataOrNull()?.let { networkInfo ->
                item { NetworkQuickInfoCards(networkInfo, state.phonePermissionState) }
            }
            // 3. Events Heading
            item {
                Text(
                    text = "Recent Events",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            // 4. Event Feed
            items(state.monitoringEvents.reversed()) { event ->
                NetworkEventItem(event)
            }
        }
    }
}

@Composable
private fun NetworkMonitoringBanner(isMonitoring: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isMonitoring)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            else
                MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 18.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(
                        if (isMonitoring)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.error
                    )
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = if (isMonitoring) "LIVE Monitoring" else "Monitoring OFF",
                style = MaterialTheme.typography.titleSmall,
                color = if (isMonitoring)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = if (isMonitoring) "Tracking changes…" else "Not tracking changes",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
private fun NetworkQuickInfoCards(
    networkInfo: com.sultonuzdev.coredroid.domain.model.NetworkInfo,
    phonePermissionState: NetworkContract.PhonePermissionState
) {
    Column(verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceMedium)) {
        InfoCard {
            Column(Modifier.padding(CoreDroidDimensions.CardPadding)) {
                Text(
                    text = "\uD83D\uDCF6 Wi-Fi Network",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(CoreDroidDimensions.SpaceSmall))
                InfoRow("SSID", networkInfo.wifiSsid)
                InfoRow("IP", networkInfo.ipAddress)
                InfoRow("MAC", networkInfo.macAddress)
                InfoRow("Frequency", networkInfo.wifiFrequency)
                InfoRow("Signal Strength", networkInfo.signalStrength)
                InfoRow("Status", if (networkInfo.isWifiConnected) "Connected" else "Disconnected")
            }
        }
        InfoCard {
            Column(Modifier.padding(CoreDroidDimensions.CardPadding)) {
                Text(
                    text = "\uD83D\uDCF1 Mobile Network",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(CoreDroidDimensions.SpaceSmall))
                val isPhoneGranted =
                    phonePermissionState is NetworkContract.PhonePermissionState.Granted
                InfoRow(
                    "Status",
                    if (networkInfo.isMobileConnected) "Connected" else "Disconnected"
                )
                InfoRow(
                    "Network Type",
                    if (isPhoneGranted) networkInfo.networkType else "Permission needed"
                )
                InfoRow(
                    "Operator",
                    if (isPhoneGranted) networkInfo.operatorName else "Permission needed"
                )
                InfoRow("Signal Strength", networkInfo.signalStrength)
            }
        }
    }
}


@Composable
private fun NetworkEventItem(event: NetworkContract.MonitoringEvent) {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    InfoCard {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = timeFormat.format(Date(event.timestamp)),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.width(70.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = event.type,
                modifier = Modifier.width(72.dp),
                color = when (event.type) {
                    "WIFI" -> CoreDroidColors.Success
                    "MOBILE" -> CoreDroidColors.Warning
                    "STATUS" -> CoreDroidColors.Primary
                    "SIGNAL" -> CoreDroidColors.Info
                    "ERROR" -> CoreDroidColors.Error
                    else -> MaterialTheme.colorScheme.onSurface
                },
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }
    }
}