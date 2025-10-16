package com.sultonuzdev.coredroid.core.di

import com.sultonuzdev.coredroid.data.datasource.BatteryDataSource
import com.sultonuzdev.coredroid.data.datasource.CameraDataSource
import com.sultonuzdev.coredroid.data.datasource.CpuDataSource
import com.sultonuzdev.coredroid.data.datasource.DisplayDataSource
import com.sultonuzdev.coredroid.data.datasource.NetworkDataSource
import com.sultonuzdev.coredroid.data.datasource.SensorDataSource
import com.sultonuzdev.coredroid.data.datasource.StorageDataSource
import com.sultonuzdev.coredroid.data.datasource.SystemDataSource
import com.sultonuzdev.coredroid.data.repository.BatteryRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.CameraRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.CpuRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.DisplayRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.NetworkRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.SensorRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.StorageRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.SystemRepositoryImpl
import com.sultonuzdev.coredroid.domain.repository.BatteryRepository
import com.sultonuzdev.coredroid.domain.repository.CameraRepository
import com.sultonuzdev.coredroid.domain.repository.CpuRepository
import com.sultonuzdev.coredroid.domain.repository.DisplayRepository
import com.sultonuzdev.coredroid.domain.repository.NetworkRepository
import com.sultonuzdev.coredroid.domain.repository.SensorRepository
import com.sultonuzdev.coredroid.domain.repository.StorageRepository
import com.sultonuzdev.coredroid.domain.repository.SystemRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {


    // Data Sources
    singleOf(::BatteryDataSource)
    singleOf(::StorageDataSource)
    singleOf(::NetworkDataSource)
    singleOf(::DisplayDataSource)
    singleOf(::CpuDataSource)
    singleOf(::CameraDataSource)
    singleOf(::SystemDataSource)
    singleOf(::SensorDataSource)


    // Repositories
    singleOf(::BatteryRepositoryImpl) { bind<BatteryRepository>() }
    singleOf(::StorageRepositoryImpl) { bind<StorageRepository>() }
    singleOf(::NetworkRepositoryImpl) { bind<NetworkRepository>() }
    singleOf(::DisplayRepositoryImpl) { bind<DisplayRepository>() }
    singleOf(::CpuRepositoryImpl) { bind<CpuRepository>() }
    singleOf(::CameraRepositoryImpl) { bind<CameraRepository>() }
    singleOf(::SystemRepositoryImpl) { bind<SystemRepository>() }
    singleOf(::SensorRepositoryImpl) { bind<SensorRepository>() }

}