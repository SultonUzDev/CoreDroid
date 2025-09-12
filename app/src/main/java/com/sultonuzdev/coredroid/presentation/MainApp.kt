package com.sultonuzdev.coredroid.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sultonuzdev.coredroid.presentation.navigation.AppNavigationGraph
import com.sultonuzdev.coredroid.presentation.navigation.BottomNavigationBar

/**
 * MainApp wraps the application with a Scaffold and decides
 * if the bottom navigation bar should be shown.
 * 
 * The "Stop Monitoring" button in monitoring screens should always be pinned to 
 * the bottom by letting those screens use the `bottomBar` slot of the Scaffold.
 * This Scaffold only controls the main app-level bottom navigation.
 */

@Composable
fun MainApp() {
    val navController = rememberNavController()

    // This value will be set by screens such as Home or Monitor,
    // to signal if the bottom navigation bar should show.
    val shouldShowBottomBar = remember {
        mutableStateOf(false)
    }

    Scaffold(
        // App-level bottom navigation bar
        bottomBar = {
            if (shouldShowBottomBar.value) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Each top-level screen is responsible for its own "bottomBar", such as
            // a sticky "Stop Monitoring" button inside a details/monitor screen.
            AppNavigationGraph(
                navController = navController,
                showBottomBar = shouldShowBottomBar
            )
        }
    }
}