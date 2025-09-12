package com.sultonuzdev.coredroid.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sultonuzdev.coredroid.core.utils.Constants
import com.sultonuzdev.coredroid.data.local.database.dao.DeviceInfoDao
import com.sultonuzdev.coredroid.data.local.database.dao.SensorDataDao
import com.sultonuzdev.coredroid.data.local.database.entities.DeviceInfoEntity
import com.sultonuzdev.coredroid.data.local.database.entities.SensorDataEntity


@Database(
    entities = [DeviceInfoEntity::class, SensorDataEntity::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
abstract class CoreDroidDatabase : RoomDatabase() {
    abstract fun deviceInfoDao(): DeviceInfoDao
    abstract fun sensorDataDao(): SensorDataDao
}
