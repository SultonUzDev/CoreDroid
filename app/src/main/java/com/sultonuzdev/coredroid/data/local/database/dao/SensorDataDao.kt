package com.sultonuzdev.coredroid.data.local.database.dao

import androidx.room.Dao


import androidx.room.*
import com.sultonuzdev.coredroid.data.local.database.entities.SensorDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorDataDao {

    @Query("SELECT * FROM sensor_data WHERE sensorName = :sensorName ORDER BY timestamp DESC LIMIT 1")
    fun getLatestSensorData(sensorName: String): Flow<SensorDataEntity?>

    @Query("SELECT * FROM sensor_data ORDER BY timestamp DESC")
    fun getAllSensorData(): Flow<List<SensorDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSensorData(sensorData: SensorDataEntity)

    @Query("DELETE FROM sensor_data WHERE timestamp < :cutoffTime")
    suspend fun deleteOldSensorData(cutoffTime: Long)
}