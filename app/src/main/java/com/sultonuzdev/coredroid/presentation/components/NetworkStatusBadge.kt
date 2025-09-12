package com.sultonuzdev.coredroid.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidColors
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTextStyles

@Composable
fun NetworkStatusBadge(
    status: String,
    isConnected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isConnected) {
        CoreDroidColors.ConnectedBackground
    } else {
        CoreDroidColors.DisconnectedBackground
    }

    val textColor = if (isConnected) {
        CoreDroidColors.Connected
    } else {
        CoreDroidColors.Disconnected
    }

    Text(
        text = status.uppercase(),
        style = CoreDroidTextStyles.ButtonText.copy(
            fontSize = CoreDroidTextStyles.ButtonText.fontSize * 0.8f,
            fontWeight = FontWeight.SemiBold
        ),
        color = textColor,
        modifier = modifier
            .clip(RoundedCornerShape(CoreDroidDimensions.BadgeRadius))
            .background(backgroundColor)
            .padding(
                horizontal = CoreDroidDimensions.BadgePaddingHorizontal,
                vertical = CoreDroidDimensions.BadgePaddingVertical
            )
    )
}