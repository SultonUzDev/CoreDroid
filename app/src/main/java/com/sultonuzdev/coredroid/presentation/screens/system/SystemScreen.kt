package com.sultonuzdev.coredroid.presentation.screens.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.presentation.common.ErrorState
import com.sultonuzdev.coredroid.presentation.common.LoadingState
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.screens.system.components.AndroidInfoCard
import com.sultonuzdev.coredroid.presentation.screens.system.components.DeviceInfoCard
import com.sultonuzdev.coredroid.presentation.screens.system.components.KernelInfoCard
import com.sultonuzdev.coredroid.presentation.screens.system.components.SecurityInfoCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun SystemScreen(
    viewModel: SystemViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column {
        TopAppBar(modifier = Modifier, currentTitle = "System Information")

        when {
            state.isLoading -> {
                LoadingState(message = "Loading system information...")
            }

            state.error != null -> {
                ErrorState(
                    message = state.error!!,
                    onRetry = { viewModel.handleIntent(SystemContract.Intent.RefreshData) }
                )
            }

            else -> {
                state.systemInfo?.let { system ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(CoreDroidDimensions.ScreenPadding),
                        verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceLarge)
                    ) {
                        // Android Information
                        AndroidInfoCard(systemInfo = system)

                        // Device Information
                        DeviceInfoCard(systemInfo = system)

                        // Kernel/System Information
                        KernelInfoCard(systemInfo = system)

                        // Security Information
                        SecurityInfoCard(systemInfo = system)
                    }
                }
            }
        }

    }

}