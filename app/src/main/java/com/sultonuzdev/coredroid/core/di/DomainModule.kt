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
import com.sultonuzdev.coredroid.domain.usecase.export.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetBatteryInfoUseCase(get()) }
    factory { MonitorBatteryUseCase(get()) }

    // Storage Use Cases
    factory { GetStorageInfoUseCase(get()) }

    // Network Use Cases
    factory { GetNetworkInfoUseCase(get()) }
    factory { MonitorNetworkStateUseCase(get()) }

    // Display Use Cases
    factory { GetDisplayInfoUseCase(get()) }

    // CPU Use Cases
    factory { GetCpuInfoUseCase(get()) }
    factory { MonitorCpuUsageUseCase(get()) }

    // Camera Use Cases
    factory { GetCameraInfoUseCase(get()) }

    // System Use Cases
    factory { GetSystemInfoUseCase(get()) }

    // Sensor Use Cases
    factory { GetAvailableSensorsUseCase(get()) }
    factory { MonitorSensorDataUseCase(get()) }

    // Overview Use Cases
    factory { GetDeviceOverviewUseCase(get(), get(), get(), get()) }

    // Export Use Cases
    factory { ExportDeviceReportUseCase(get()) }
    factory { ShareDeviceReportUseCase(get()) }
}