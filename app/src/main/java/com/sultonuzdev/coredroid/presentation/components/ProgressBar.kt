package com.sultonuzdev.coredroid.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions

@Composable
fun ProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CoreDroidColors.SurfaceTint,
    progressColors: List<Color> = CoreDroidColors.Gradients.SuccessGradient,
    height: androidx.compose.ui.unit.Dp = CoreDroidDimensions.ProgressBarHeight
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(CoreDroidDimensions.ProgressBarRadius))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(RoundedCornerShape(CoreDroidDimensions.ProgressBarRadius))
                .background(
                    brush = Brush.horizontalGradient(progressColors)
                )
        )
    }
}