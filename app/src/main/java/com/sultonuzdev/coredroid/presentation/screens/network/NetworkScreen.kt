package com.sultonuzdev.coredroid.presentation.screens.network

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sultonuzdev.coredroid.core.utils.PermissionManager
import com.sultonuzdev.coredroid.presentation.common.ErrorState
import com.sultonuzdev.coredroid.presentation.common.LoadingState
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.screens.network.components.ConnectivityCard
import com.sultonuzdev.coredroid.presentation.screens.network.components.MobileCard
import com.sultonuzdev.coredroid.presentation.screens.network.components.WifiCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NetworkScreen(
    onNavigateToMonitoring: () -> Unit = {},
    viewModel: NetworkViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val permissionManager = PermissionManager(context)

    val phonePermissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_PHONE_STATE
    )

    val requestPhonePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onPhonePermissionGranted()
        } else {
            val isPermanentlyDenied =
                (context as? androidx.activity.ComponentActivity)?.let { activity ->
                    permissionManager.isPermissionPermanentlyDenied(
                        activity,
                        android.Manifest.permission.READ_PHONE_STATE
                    )
                } ?: false
            viewModel.onPhonePermissionDenied(isPermanentlyDenied)
        }
    }

    // Handle effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NetworkContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }

                is NetworkContract.Effect.ShowMonitoringStarted -> {
                    Toast.makeText(context, "Real-time monitoring started", Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkContract.Effect.ShowMonitoringStopped -> {
                    Toast.makeText(context, "Monitoring stopped", Toast.LENGTH_SHORT).show()
                }

                is NetworkContract.Effect.OpenAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    // Initial permission check
    LaunchedEffect(key1 = phonePermissionState.status.isGranted) {
        if (phonePermissionState.status.isGranted) {
            viewModel.onPhonePermissionGranted()
        }
    }

    Column {
        TopAppBar(modifier = Modifier, currentTitle = "Network Info")
        // Handle different UI states properly
        when {
            state.loadingInfo.isLoading -> {
                LoadingState(message = state.loadingInfo.message ?: "Loading...")
            }

            state.networkInfoState.isError -> {
                ErrorState(
                    message = state.networkInfoState.getErrorOrNull()?.message ?: "Unknown error",
                    onRetry = { viewModel.handleIntent(NetworkContract.Intent.RefreshData) }
                )
            }

            state.networkInfoState.isSuccess -> {
                val networkInfo = state.networkInfoState.getDataOrNull()!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(CoreDroidDimensions.ScreenPadding),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceLarge)
                ) {
                    // Network Information Cards
                    MobileCard(
                        networkInfo = networkInfo,
                        phonePermissionState = state.phonePermissionState,
                        onRequestPermission = {
                            requestPhonePermissionLauncher.launch(android.Manifest.permission.READ_PHONE_STATE)
                        },
                        onOpenAppSettings = {
                            viewModel.handleIntent(NetworkContract.Intent.OpenAppSettings)
                        }
                    )
                    WifiCard(networkInfo = networkInfo)
                    ConnectivityCard(networkInfo = networkInfo)

                    // Monitoring Controls
                    MonitoringControls(
                        isMonitoring = state.isMonitoring,
                        onStartMonitoring = {
                            onNavigateToMonitoring()
                        },
                        onStopMonitoring = {
                            viewModel.handleIntent(NetworkContract.Intent.StopMonitoring)
                        }
                    )
                }
            }
        }


    }

}

@Composable
private fun MonitoringControls(
    isMonitoring: Boolean,
    onStartMonitoring: () -> Unit,
    onStopMonitoring: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = CoreDroidDimensions.ElevationMedium)
    ) {
        Column(
            modifier = Modifier.padding(CoreDroidDimensions.CardPadding),
            verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceMedium)
        ) {
            Text(
                text = "📊 Real-time Network Monitoring",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Monitor WiFi connections, mobile data changes, and connectivity status in real-time with detailed event tracking.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = onStartMonitoring,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open Monitoring Screen")
            }
        }
    }
}