package com.sultonuzdev.coredroid.core.di


import com.sultonuzdev.coredroid.domain.usecase.battery.*
import com.sultonuzdev.coredroid.domain.usecase.storage.*
import com.sultonuzdev.coredroid.domain.usecase.network.*
import com.sultonuzdev.coredroid.domain.usecase.display.*
import com.sultonuzdev.coredroid.domain.usecase.cpu.*
import com.sultonuzdev.coredroid.domain.usecase.camera.*
import com.sultonuzdev.coredroid.domain.usecase.system.*
import com.sultonuzdev.coredroid.domain.usecase.sensor.*
import com.sultonuzdev.coredroid.domain.usecase.overview.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetBatteryInfoUseCase)
    singleOf(::MonitorBatteryUseCase)

    // Storage Use Cases
    singleOf(::GetStorageInfoUseCase)

    // Network Use Cases
    singleOf(::GetNetworkInfoUseCase)
    singleOf(::MonitorNetworkStateUseCase)

    // Display Use Cases
    singleOf(::GetDisplayInfoUseCase)

    // CPU Use Cases
    singleOf(::GetCpuInfoUseCase)
    singleOf(::MonitorCpuUsageUseCase)

    // Camera Use Cases
    singleOf(::GetCameraInfoUseCase)

    // System Use Cases
    singleOf(::GetSystemInfoUseCase)

    // Sensor Use Cases
    singleOf(::GetAvailableSensorsUseCase)
    singleOf(::MonitorSensorDataUseCase)

    // Overview Use Cases
    singleOf(::GetDeviceOverviewUseCase)


}