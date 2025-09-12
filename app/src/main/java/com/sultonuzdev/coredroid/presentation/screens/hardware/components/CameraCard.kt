package com.sultonuzdev.coredroid.presentation.screens.hardware.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sultonuzdev.coredroid.domain.model.CameraInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.screens.hardware.HardwareContract
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun CameraCard(
    modifier: Modifier = Modifier,
    cameraInfo: CameraInfo,
    cameraPermissionState: HardwareContract.CameraPermissionState,
    isLoading: Boolean = false,
    onRequestPermission: () -> Unit = {},
    onOpenAppSettings: () -> Unit = {}
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "📷 Camera Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        when (cameraPermissionState) {
            is HardwareContract.CameraPermissionState.Granted -> {
                if (isLoading) {
                    // Show loading state
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceSmall)
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Loading camera information...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                } else {
                    // Show camera information
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(CoreDroidDimensions.GridSpacing)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            InfoRow("Rear Camera", "${cameraInfo.rearCameraMegapixels}MP")
                            InfoRow("API Level", cameraInfo.apiLevel)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            InfoRow("Front Camera", "${cameraInfo.frontCameraMegapixels}MP")
                            InfoRow("Flash", if (cameraInfo.hasFlash) "LED" else "No")
                        }
                    }

                    if (cameraInfo.cameraCount > 0) {
                        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceXSmall))
                        InfoRow("Total Cameras", cameraInfo.cameraCount.toString())
                    }
                }
            }

            is HardwareContract.CameraPermissionState.PermanentlyDenied -> {
                // Show settings redirect message
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceSmall)
                ) {
                    Text(
                        text = "Camera permission is permanently denied. Please enable it in app settings to view camera specifications.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onOpenAppSettings,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Open App Settings")
                    }
                }
            }

            else -> {
                // Show permission request (NotRequested or Denied)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceSmall)
                ) {
                    Text(
                        text = "Camera permission is needed to display camera specifications.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onRequestPermission,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Grant Camera Permission")
                    }
                }
            }
        }
    }
}