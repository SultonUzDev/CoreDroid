package com.sultonuzdev.coredroid.presentation.screens.overview.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.core.extensions.formatBytes
import com.sultonuzdev.coredroid.domain.model.StorageInfo
import com.sultonuzdev.coredroid.presentation.components.InfoCard
import com.sultonuzdev.coredroid.presentation.components.InfoRow
import com.sultonuzdev.coredroid.presentation.components.ProgressBar
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun StorageCard(
    storageInfo: StorageInfo,
    modifier: Modifier = Modifier
) {
    InfoCard(
        modifier = modifier,
       
    ) {
        Text(
            text = "💾 Storage Overview",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceSmall))

        InfoRow("Total Space", storageInfo.externalTotal.formatBytes())

        ProgressBar(
            progress = storageInfo.usageExternalStoragePercentage / 100f,
            modifier = Modifier.padding(vertical = CoreDroidDimensions.SpaceSmall),
            progressColors = CoreDroidColors.Gradients.InfoGradient
        )

        InfoRow("Used", "${storageInfo.externalUsed.formatBytes()} (${storageInfo.usageExternalStoragePercentage}%)")
        InfoRow("Available", storageInfo.externalAvailable.formatBytes())

        if (storageInfo.appCacheSize > 0) {
            Spacer(modifier = Modifier.height(CoreDroidDimensions.SpaceXSmall))
            InfoRow("App Cache", storageInfo.appCacheSize.formatBytes())
        }
    }
}
