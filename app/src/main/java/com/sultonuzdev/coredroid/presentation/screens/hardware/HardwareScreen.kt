package com.sultonuzdev.coredroid.presentation.screens.hardware

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sultonuzdev.coredroid.core.base.UiState
import com.sultonuzdev.coredroid.core.utils.PermissionManager
import com.sultonuzdev.coredroid.domain.model.CameraInfo
import com.sultonuzdev.coredroid.domain.model.CpuInfo
import com.sultonuzdev.coredroid.domain.model.DisplayInfo
import com.sultonuzdev.coredroid.presentation.common.ErrorState
import com.sultonuzdev.coredroid.presentation.common.LoadingState
import com.sultonuzdev.coredroid.presentation.components.TopAppBar
import com.sultonuzdev.coredroid.presentation.screens.hardware.components.CameraCard
import com.sultonuzdev.coredroid.presentation.screens.hardware.components.CpuCard
import com.sultonuzdev.coredroid.presentation.screens.hardware.components.DisplayCard
import com.sultonuzdev.coredroid.presentation.screens.hardware.components.RamCard
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HardwareScreen(
    viewModel: HardwareViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val permissionManager = PermissionManager(context)

    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onCameraPermissionGranted()
        } else {
            val isPermanentlyDenied =
                (context as? androidx.activity.ComponentActivity)?.let { activity ->
                    permissionManager.isCameraPermissionPermanentlyDenied(activity)
                } ?: false
            viewModel.onCameraPermissionDenied(isPermanentlyDenied)
        }
    }

    // Handle effects
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HardwareContract.Effect.OpenAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }

                else -> {}
            }
        }
    }

    // Initial permission check
    LaunchedEffect(key1 = cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted) {
            viewModel.onCameraPermissionGranted()
        }
    }
    Column {
        TopAppBar(modifier = Modifier, currentTitle = "Hardware")
        when {
            state.loadingInfo.isLoading -> {
                LoadingState(message = "Loading hardware information...")
            }

            else -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(CoreDroidDimensions.ScreenPadding),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceLarge)
                ) {

                    when (state.cpuInfoState) {
                        UiState.Empty -> {
                            CpuCard(cpuInfo = CpuInfo.empty())
                        }

                        is UiState.Error -> {
                            ErrorState(
                                message = "Failed to load CPU information",
                                onRetry = { viewModel.handleIntent(HardwareContract.Intent.RefreshData) })
                        }

                        UiState.Loading -> {
                            LoadingState(message = "Loading CPU information...")

                        }

                        is UiState.Success<*> -> {
                            val cpu = (state.cpuInfoState as UiState.Success<CpuInfo>).data
                            CpuCard(cpuInfo = cpu)
                            RamCard(cpuInfo = cpu)
                        }
                    }

                    when (state.displayInfoState) {
                        UiState.Empty -> {
                            DisplayCard(DisplayInfo.empty())
                        }

                        is UiState.Error -> {
                            ErrorState(
                                message = "Failed to load Display information",
                                onRetry = { viewModel.handleIntent(HardwareContract.Intent.RefreshData) })
                        }

                        UiState.Loading -> {
                            LoadingState(message = "Loading Display information...")

                        }

                        is UiState.Success<*> -> {
                            val display =
                                (state.displayInfoState as UiState.Success<DisplayInfo>).data
                            DisplayCard(displayInfo = display)
                        }
                    }

                    CameraCard(
                        cameraInfo = when (state.cameraInfoState) {
                            is UiState.Success -> (state.cameraInfoState as UiState.Success<CameraInfo>).data
                            else -> CameraInfo.empty()
                        },
                        cameraPermissionState = state.cameraPermissionState,
                        isLoading = state.cameraInfoState is UiState.Loading,
                        onRequestPermission = {
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        },
                        onOpenAppSettings = {
                            viewModel.handleIntent(HardwareContract.Intent.OpenAppSettings)
                        }
                    )
                }
            }
        }
    }
}