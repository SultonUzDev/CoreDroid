package com.sultonuzdev.coredroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sultonuzdev.coredroid.core.navigation.HardwareRoute
import com.sultonuzdev.coredroid.core.navigation.NetworkMonitoringRoute
import com.sultonuzdev.coredroid.core.navigation.NetworkRoute
import com.sultonuzdev.coredroid.core.navigation.OverviewRoute
import com.sultonuzdev.coredroid.core.navigation.SensorDetailsRoute
import com.sultonuzdev.coredroid.core.navigation.SensorsRoute
import com.sultonuzdev.coredroid.core.navigation.SystemRoute
import com.sultonuzdev.coredroid.presentation.screens.hardware.HardwareScreen
import com.sultonuzdev.coredroid.presentation.screens.network.NetworkMonitoringScreen
import com.sultonuzdev.coredroid.presentation.screens.network.NetworkScreen
import com.sultonuzdev.coredroid.presentation.screens.overview.OverviewScreen
import com.sultonuzdev.coredroid.presentation.screens.sensors.SensorsScreen
import com.sultonuzdev.coredroid.presentation.screens.sensors.details.SensorDetailsScreen
import com.sultonuzdev.coredroid.presentation.screens.system.SystemScreen

@Composable fun AppNavigationGraph(
    navController: NavHostController,
    showBottomBar: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = OverviewRoute
    ) {
        composable<OverviewRoute> {
            showBottomBar.value = true
            OverviewScreen()
        }

        composable<HardwareRoute> {
            showBottomBar.value = true
            HardwareScreen()
        }

        composable<SystemRoute> {
            showBottomBar.value = true
            SystemScreen()
        }

        composable<NetworkRoute> {
            showBottomBar.value = true
            NetworkScreen(
                onNavigateToMonitoring = {
                    navController.navigate(NetworkMonitoringRoute)
                }
            )
        }

        composable<NetworkMonitoringRoute> {
            showBottomBar.value = false
            NetworkMonitoringScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<SensorsRoute> {
            showBottomBar.value = true
            SensorsScreen(
                onNavigateToSensorDetails = { sensorType, sensorName ->
                    navController.navigate(
                        SensorDetailsRoute(
                            sensorType = sensorType,
                            sensorName = sensorName
                        )
                    )
                }
            )
        }

        composable<SensorDetailsRoute> { backStackEntry ->
            showBottomBar.value = false
            val route = backStackEntry.toRoute<SensorDetailsRoute>()
            SensorDetailsScreen(
                sensorType = route.sensorType,
                sensorName = route.sensorName,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }


}