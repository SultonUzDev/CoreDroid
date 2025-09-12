package com.sultonuzdev.coredroid.data.datasource



import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.sultonuzdev.coredroid.domain.model.SensorInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber

class SensorDataSource(private val context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    fun getAvailableSensors(): Flow<List<SensorInfo>> = flow {
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sensorInfoList = sensors.map { sensor ->
            SensorInfo(
                name = sensor.name,
                type = sensor.type,
                vendor = sensor.vendor,
                version = sensor.version,
                power = sensor.power,
                resolution = sensor.resolution,
                maximumRange = sensor.maximumRange,
                values = emptyList(),
                accuracy = 0,
                timestamp = System.currentTimeMillis()
            )
        }
        emit(sensorInfoList)
    }.catch { e ->
        Timber.e(e, "Failed to get available sensors")
        emit(emptyList()) // Emit empty list instead of throwing
    }

    fun monitorSensor(sensorType: Int): Flow<SensorInfo> = callbackFlow {
        val sensor = sensorManager.getDefaultSensor(sensorType)

        if (sensor == null) {
            Timber.w("Sensor type $sensorType not available")
            close()
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                try {
                    val sensorInfo = SensorInfo(
                        name = sensor.name,
                        type = sensor.type,
                        vendor = sensor.vendor,
                        version = sensor.version,
                        power = sensor.power,
                        resolution = sensor.resolution,
                        maximumRange = sensor.maximumRange,
                        values = event.values.toList(),
                        accuracy = event.accuracy,
                        timestamp = event.timestamp
                    )
                    trySend(sensorInfo).isSuccess
                } catch (e: Exception) {
                    Timber.e(e, "Error processing sensor event")
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Timber.d("Sensor accuracy changed: $accuracy")
            }
        }

        val registered = sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        if (!registered) {
            Timber.e("Failed to register sensor listener for type $sensorType")
            close()
            return@callbackFlow
        }

        awaitClose {
            sensorManager.unregisterListener(listener)
            Timber.d("Unregistered sensor listener for type $sensorType")
        }
    }
}