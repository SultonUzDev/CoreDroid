package com.sultonuzdev.coredroid.data.local.database.dao

import androidx.room.Dao


import androidx.room.*
import com.sultonuzdev.coredroid.data.local.database.entities.DeviceInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceInfoDao {

    @Query("SELECT * FROM device_info ORDER BY timestamp DESC LIMIT 1")
    fun getLatestDeviceInfo(): Flow<DeviceInfoEntity?>

    @Query("SELECT * FROM device_info ORDER BY timestamp DESC")
    fun getAllDeviceInfo(): Flow<List<DeviceInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceInfo(deviceInfo: DeviceInfoEntity)

    @Delete
    suspend fun deleteDeviceInfo(deviceInfo: DeviceInfoEntity)

    @Query("DELETE FROM device_info WHERE timestamp < :cutoffTime")
    suspend fun deleteOldRecords(cutoffTime: Long)
}