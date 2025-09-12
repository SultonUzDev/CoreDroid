package com.sultonuzdev.coredroid.presentation.screens.overview

import android.widget.Toast
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.core.base.onError
import com.sultonuzdev.coredroid.core.base.onSuccess
import com.sultonuzdev.coredroid.domain.model.BatteryInfo
import com.sultonuzdev.coredroid.domain.model.StorageInfo
import com.sultonuzdev.coredroid.presentation.common.EmptyState
import com.sultonuzdev.coredroid.presentation.common.ErrorState
import com.sultonuzdev.coredroid.presentation.common.LoadingState
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.screens.overview.components.BatteryCard
import com.sultonuzdev.coredroid.presentation.screens.overview.components.QuickStatsGrid
import com.sultonuzdev.coredroid.presentation.screens.overview.components.StorageCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is OverviewContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }

                is OverviewContract.Effect.ShowExportSuccess -> {
                    Toast.makeText(context, "Export successful!", Toast.LENGTH_SHORT).show()
                }

                is OverviewContract.Effect.ShareReport -> {
                    context.startActivity(effect.intent)
                }

                is OverviewContract.Effect.ShowRefreshComplete -> {
                    Toast.makeText(context, "Device information refreshed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Column {
        TopAppBar(modifier = Modifier, currentTitle = "Overview")
        if (state.isRefreshing) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
        when {
            state.loadingInfo.isLoading -> {
                LoadingState(
                    message = state.loadingInfo.message ?: "Loading...",
                    progress = state.loadingInfo.progress
                )
            }

            // Check if any essential data failed to load
            state.deviceOverviewState.isError -> {
                ErrorState(
                    message = "Failed to load device information",
                    onRetry = { viewModel.handleIntent(OverviewContract.Intent.RefreshData) }
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(CoreDroidDimensions.ScreenPadding),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceLarge)
                ) {
                    // Quick Stats Grid - only show if data is available
                    state.deviceOverviewState.onSuccess { overview ->
                        QuickStatsGrid(deviceOverview = overview)
                    }
                    state.deviceOverviewState.onError {
                        ErrorCard(
                            title = "Device Information",
                            error = "Failed to load device data"
                        )
                    }

                    // Battery Card - handle individual state
                    when (state.batteryInfoState) {
                        is UiState.Success -> {
                            BatteryCard(batteryInfo = (state.batteryInfoState as UiState.Success<BatteryInfo>).data)
                        }

                        is UiState.Error -> {
                            ErrorCard(
                                title = "Battery Information",
                                error = "Failed to load battery data"
                            )
                        }

                        is UiState.Loading -> {
                            LoadingCard(title = "Battery Information")
                        }

                        UiState.Empty -> {
                            EmptyState(message = "No battery information available")
                        }
                    }

                    // Storage Card - handle individual state
                    when (state.storageInfoState) {
                        is UiState.Success -> {
                            StorageCard(storageInfo = (state.storageInfoState as UiState.Success<StorageInfo>).data)
                        }

                        is UiState.Error -> {
                            ErrorCard(
                                title = "Storage Information",
                                error = "Failed to load storage data"
                            )
                        }

                        is UiState.Loading -> {
                            LoadingCard(title = "Storage Information")
                        }

                        UiState.Empty -> {
                            EmptyState(message = "No storage information available")
                        }
                    }

                    // Refresh Button
                    Button(
                        onClick = { viewModel.handleIntent(OverviewContract.Intent.RefreshData) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isRefreshing
                    ) {
                        Text("🔄 Refresh Information")
                    }
                }
            }
        }


    }

}


@Composable
private fun ErrorCard(
    title: String,
    error: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(CoreDroidDimensions.CardPadding)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
private fun LoadingCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(CoreDroidDimensions.CardPadding)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
