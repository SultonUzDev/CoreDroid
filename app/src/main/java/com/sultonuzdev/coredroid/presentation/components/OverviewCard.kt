package com.sultonuzdev.coredroid.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun OverviewCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    gradient: List<Color> = CoreDroidColors.Gradients.PrimaryGradient
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(CoreDroidDimensions.OverviewCardRadius)),
        elevation = CardDefaults.cardElevation(defaultElevation = CoreDroidDimensions.ElevationMedium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradient
                    )
                )
                .padding(CoreDroidDimensions.OverviewCardPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value,
                    style = CoreDroidTextStyles.OverviewCardValue,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = label,
                    style = CoreDroidTextStyles.OverviewCardLabel,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}