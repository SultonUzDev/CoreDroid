package com.sultonuzdev.coredroid.feature.tools.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sultonuzdev.coredroid.core.ui.state.EmptyState

@Composable
fun ToolsScreen(
    modifier: Modifier = Modifier
) {

    EmptyState(
        modifier = modifier,
        title = "Tools"
    )
}