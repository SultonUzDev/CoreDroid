package com.sultonuzdev.coredroid.feature.overview.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sultonuzdev.coredroid.core.ui.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.core.ui.theme.CoreDroidTheme

/**
 * Overview dashboard — quick system stats (CPU, RAM, Battery, Storage) presented
 * as Tech-Noir "bento" cards with circular progress indicators.
 *
 * Matches the `overview_dashboard` design sample.
 */

@Composable
fun OverviewScreen(
    onMemoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
) {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewScreenContent(
    modifier: Modifier = Modifier,
    onMemoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},

    ) {
    val stats = overviewStats()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "CoreDroid",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onMemoryClick) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = "Hardware",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = CoreDroidDimensions.ScreenPadding,
                    vertical = CoreDroidDimensions.SpaceMedium,
                ),
            verticalArrangement = Arrangement.spacedBy(CoreDroidDimensions.SpaceLarge),
        ) {
            stats.forEach { stat ->
                StatCard(stat = stat)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenContentPreview() {
    CoreDroidTheme {
        OverviewScreenContent()
    }
}

private data class OverviewStat(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val percent: Int,
    val active: Boolean = false,
)

private fun overviewStats(): List<OverviewStat> = listOf(
    OverviewStat(
        title = "CPU Usage",
        subtitle = "Hexa-core 2.4GHz",
        icon = Icons.Default.Memory,
        percent = 45,
        active = true,
    ),
    OverviewStat(
        title = "RAM",
        subtitle = "3.2GB / 6GB",
        icon = Icons.Default.DeveloperBoard,
        percent = 62,
    ),
    OverviewStat(
        title = "Battery",
        subtitle = "Charging • 4000mAh",
        icon = Icons.Default.BatteryChargingFull,
        percent = 88,
    ),
    OverviewStat(
        title = "Storage",
        subtitle = "96GB / 128GB",
        icon = Icons.Default.Storage,
        percent = 75,
    ),
)

@Composable
private fun StatCard(
    stat: OverviewStat,
    modifier: Modifier = Modifier,
) {
    val techGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
            Color.Transparent,
        ),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CoreDroidDimensions.CardRadiusLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .background(techGradient)
            .border(
                width = CoreDroidDimensions.DividerThickness,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(CoreDroidDimensions.CardRadiusLarge),
            )
            .padding(CoreDroidDimensions.CardPadding),
    ) {
        Column {
            // Header: title + subtitle on the left, category icon on the right.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text(
                        text = stat.title,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.size(CoreDroidDimensions.SpaceXXSmall))
                    Text(
                        text = stat.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                Icon(
                    imageVector = stat.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(CoreDroidDimensions.IconSizeLarge),
                )
            }

            Spacer(Modifier.size(CoreDroidDimensions.SpaceLarge))

            // Circular gauge + optional "Active" pulse label.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularStat(percent = stat.percent)

                if (stat.active) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(CoreDroidDimensions.SpaceSmall)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                        )
                        Spacer(Modifier.size(CoreDroidDimensions.SpaceSmall))
                        Text(
                            text = "Active",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularStat(
    percent: Int,
    modifier: Modifier = Modifier,
) {
    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    val progressColor = MaterialTheme.colorScheme.primary
    val sweep = 360f * (percent.coerceIn(0, 100) / 100f)

    Box(
        modifier = modifier.size(80.dp),
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val inset = strokeWidth / 2
            val arcSize = androidx.compose.ui.geometry.Size(
                size.width - strokeWidth,
                size.height - strokeWidth,
            )
            val topLeft = androidx.compose.ui.geometry.Offset(inset, inset)

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth),
            )
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        }
        Text(
            text = "$percent%",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
