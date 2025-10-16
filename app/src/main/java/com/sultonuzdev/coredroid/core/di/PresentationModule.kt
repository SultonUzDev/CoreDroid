package com.sultonuzdev.coredroid.core.di


import com.sultonuzdev.coredroid.presentation.screens.overview.OverviewViewModel
import com.sultonuzdev.coredroid.presentation.screens.hardware.HardwareViewModel
import com.sultonuzdev.coredroid.presentation.screens.system.SystemViewModel
import com.sultonuzdev.coredroid.presentation.screens.network.NetworkViewModel
import com.sultonuzdev.coredroid.presentation.screens.sensors.SensorsViewModel
import com.sultonuzdev.coredroid.presentation.screens.sensors.details.SensorDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::OverviewViewModel)
    viewModelOf(::HardwareViewModel)
    viewModelOf(::SystemViewModel)
    viewModelOf(::NetworkViewModel)
    viewModelOf(::SensorsViewModel)
    viewModelOf(::SensorDetailsViewModel)
}