package com.sultonuzdev.coredroid.core.di

import androidx.room.Room
import com.sultonuzdev.coredroid.core.utils.Constants
import com.sultonuzdev.coredroid.data.datasource.BatteryDataSource
import com.sultonuzdev.coredroid.data.datasource.CameraDataSource
import com.sultonuzdev.coredroid.data.datasource.CpuDataSource
import com.sultonuzdev.coredroid.data.datasource.DisplayDataSource
import com.sultonuzdev.coredroid.data.datasource.NetworkDataSource
import com.sultonuzdev.coredroid.data.datasource.SensorDataSource
import com.sultonuzdev.coredroid.data.datasource.StorageDataSource
import com.sultonuzdev.coredroid.data.datasource.SystemDataSource
import com.sultonuzdev.coredroid.data.local.database.CoreDroidDatabase
import com.sultonuzdev.coredroid.data.repository.BatteryRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.CameraRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.CpuRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.DisplayRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.ExportRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.NetworkRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.SensorRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.StorageRepositoryImpl
import com.sultonuzdev.coredroid.data.repository.SystemRepositoryImpl
import com.sultonuzdev.coredroid.domain.repository.BatteryRepository
import com.sultonuzdev.coredroid.domain.repository.CameraRepository
import com.sultonuzdev.coredroid.domain.repository.CpuRepository
import com.sultonuzdev.coredroid.domain.repository.DisplayRepository
import com.sultonuzdev.coredroid.domain.repository.ExportRepository
import com.sultonuzdev.coredroid.domain.repository.NetworkRepository
import com.sultonuzdev.coredroid.domain.repository.SensorRepository
import com.sultonuzdev.coredroid.domain.repository.StorageRepository
import com.sultonuzdev.coredroid.domain.repository.SystemRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CoreDroidDatabase::class.java, Constants.DATABASE_NAME
        ).build()
    }
    single {  get<CoreDroidDatabase>().deviceInfoDao() }
    single {  get<CoreDroidDatabase>().sensorDataDao() }


    // Data Sources
    single { BatteryDataSource(get()) }
    single { StorageDataSource(get()) }
    single { NetworkDataSource(get()) }
    single { DisplayDataSource(get()) }
    single { CpuDataSource(get()) }
    single { CameraDataSource(get()) }
    single { SystemDataSource() }
    single { SensorDataSource(get()) }

    // Repositories
    single<BatteryRepository> { BatteryRepositoryImpl(get()) }
    single<StorageRepository> { StorageRepositoryImpl(get()) }
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
    single<DisplayRepository> { DisplayRepositoryImpl(get()) }
    single<CpuRepository> { CpuRepositoryImpl(get()) }
    single<CameraRepository> { CameraRepositoryImpl(get()) }
    single<SystemRepository> { SystemRepositoryImpl(get()) }
    single<SensorRepository> { SensorRepositoryImpl(get()) }
    single<ExportRepository> { ExportRepositoryImpl(get()) }
}