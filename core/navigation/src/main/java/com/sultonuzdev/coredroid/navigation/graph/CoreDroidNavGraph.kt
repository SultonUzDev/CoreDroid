package com.sultonuzdev.coredroid.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sultonuzdev.coredroid.feature.battery.presentation.BatteryScreen
import com.sultonuzdev.coredroid.feature.network.presentation.NetworkScreen
import com.sultonuzdev.coredroid.feature.overview.presentation.OverviewScreen
import com.sultonuzdev.coredroid.feature.settings.presentation.SettingsScreen
import com.sultonuzdev.coredroid.feature.system.presentation.SystemScreen
import com.sultonuzdev.coredroid.feature.tools.presentation.ToolsScreen
import com.sultonuzdev.coredroid.navigation.routes.AppRoutes

// Main navigation setup
@Composable
fun CoreDroidNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppRoutes.OverviewGraph.Overview
    ) {
        // overview navigation graph
        composable<AppRoutes.OverviewGraph.Overview> {
            OverviewScreen()
        }


        //system navigation graph
        composable<AppRoutes.SystemGraph.System> {
            SystemScreen()
        }


        //battery navigation graph
        composable<AppRoutes.BatteryGraph.Battery> {
            BatteryScreen()
        }


        //network navigation graph
        composable<AppRoutes.NetworkGraph.Network> {
            NetworkScreen()
        }


        //tools navigation graph
        composable<AppRoutes.ToolsGraph.Tools> {
            ToolsScreen()
        }


        //settings navigation graph
        composable<AppRoutes.SettingsGraph.Settings> {
            SettingsScreen()
        }


    }
}