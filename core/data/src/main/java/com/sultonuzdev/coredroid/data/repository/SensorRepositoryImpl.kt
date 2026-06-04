package com.sultonuzdev.coredroid.data.repository

import com.sultonuzdev.coredroid.core.common.base.BaseRepository
import com.sultonuzdev.coredroid.data.datasource.SensorDataSource
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import com.sultonuzdev.coredroid.domain.repository.SensorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

class SensorRepositoryImpl(
    private val sensorDataSource: SensorDataSource
) : BaseRepository(), SensorRepository {

    override fun getAvailableSensors(): Flow<List<SensorInfo>> {
        return safeFlowCall { sensorDataSource.getAvailableSensors() }
    }

    override fun monitorSensor(sensorType: Int): Flow<SensorInfo> {
        // Don't use safeFlowCall here - monitoring is a continuous stream, not a single value
        // Let the caller handle errors via catch block
        return sensorDataSource.monitorSensor(sensorType)
    }

    override fun monitorAllSensors(): Flow<List<SensorInfo>> {
        return safeFlowCall {
            flow {
                // First get all available sensors
                sensorDataSource.getAvailableSensors().collect { staticSensors ->
                    if (staticSensors.isEmpty()) {
                        emit(emptyList())
                        return@collect
                    }

                    // Create flows for all sensor types
                    val sensorFlows = staticSensors.map { sensor ->
                        sensorDataSource.monitorSensor(sensor.type)
                            .catch { error ->
                                Timber.w(error, "Failed to monitor sensor type ${sensor.type}")
                                // Emit the static sensor info if monitoring fails
                                emit(sensor)
                            }
                    }

                    // Combine all sensor flows into a single flow
                    if (sensorFlows.isNotEmpty()) {
                        combine(sensorFlows) { sensorArray ->
                            sensorArray.toList()
                        }.collect { liveSensors ->
                            emit(liveSensors)
                        }
                    } else {
                        emit(staticSensors)
                    }
                }
            }
        }
    }

    /**
     * Monitor multiple specific sensor types
     */
    fun monitorSelectedSensors(sensorTypes: Set<Int>): Flow<List<SensorInfo>> {
        return safeFlowCall {
            if (sensorTypes.isEmpty()) {
                flowOf(emptyList())
            } else {
                val sensorFlows = sensorTypes.map { sensorType ->
                    sensorDataSource.monitorSensor(sensorType)
                        .catch { error ->
                            Timber.w(error, "Failed to monitor sensor type $sensorType")
                            // You might want to emit a fallback sensor or skip this one
                        }
                }

                combine(sensorFlows) { sensorArray ->
                    sensorArray.toList()
                }
            }
        }
    }
}