package com.sultonuzdev.coredroid.presentation.screens.hardware.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.domain.model.DisplayInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun DisplayCard(
    displayInfo: DisplayInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
        
    ) {
        Text(
            text = "📱 Display Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Resolution", displayInfo.resolution)
        InfoRow("Density", "${displayInfo.densityDpi} DPI")
        InfoRow("Refresh Rate", "${displayInfo.refreshRate.toInt()}Hz")
        InfoRow("Size", "%.1f inches".format(displayInfo.screenSizeInches))
        InfoRow("Orientation", displayInfo.orientation)

        if (displayInfo.hdrSupported) {
            InfoRow("HDR Support", "Yes")
        }
    }
}

