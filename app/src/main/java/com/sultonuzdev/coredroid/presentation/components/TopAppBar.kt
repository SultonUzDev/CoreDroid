package com.sultonuzdev.coredroid.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidDimensions
import com.sultonuzdev.coredroid.presentation.theme.CoreDroidTheme

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    currentTitle: String,
    navigationIcon: @Composable () -> Unit = {}
) {
    // App Header with dynamic title
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentTitle,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = CoreDroidDimensions.PaddingXSmall),
                )
            }
        },
        navigationIcon = {
            Box(
                modifier = Modifier.padding(horizontal = CoreDroidDimensions.PaddingXSmall),
                contentAlignment = Alignment.Center
            ) {
                navigationIcon()
            }
        }
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    CoreDroidTheme {
        TopAppBar(currentTitle = "Home") {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

