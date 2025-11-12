package com.sultonuzdev.coredroid.core.ui.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sultonuzdev.coredroid.core.ui.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.core.ui.theme.CoreDroidPreview
import com.sultonuzdev.coredroid.core.ui.theme.CoreDroidTextStyles
import com.sultonuzdev.coredroid.core.ui.theme.CoreDroidTheme

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            CoreDroidDimensions.SpaceSmall,
            Alignment.CenterVertically
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.Timelapse,
            contentDescription = title,
            modifier = Modifier.size(CoreDroidDimensions.IconSizeLarge),
            tint = MaterialTheme.colorScheme.error
        )
        Text(text = title, style = CoreDroidTextStyles.AppTitle)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    CoreDroidPreview {
        EmptyState(title = "No Data")
    }
}