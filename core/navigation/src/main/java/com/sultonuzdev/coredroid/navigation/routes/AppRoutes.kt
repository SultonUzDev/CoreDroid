package com.sultonuzdev.coredroid.navigation.routes

import kotlinx.serialization.Serializable

/**
 * App-level main
 */
@Serializable
sealed interface AppRoutes {
    @Serializable
    sealed interface SettingsGraph : AppRoutes {
        @Serializable
        data object Settings : SettingsGraph
    }

    // Main app graph
    @Serializable
    sealed interface OverviewGraph : AppRoutes {
        @Serializable
        data object Overview : OverviewGraph
    }

    @Serializable
    sealed interface SystemGraph : AppRoutes {
        @Serializable
        data object System : SystemGraph
    }


    @Serializable
    sealed interface BatteryGraph : AppRoutes {
        @Serializable
        data object Battery : BatteryGraph
    }

    @Serializable
    sealed interface NetworkGraph : AppRoutes {
        @Serializable
        data object Network : NetworkGraph
    }

    @Serializable
    sealed interface ToolsGraph : AppRoutes {
        @Serializable
        data object Tools : ToolsGraph
    }
}