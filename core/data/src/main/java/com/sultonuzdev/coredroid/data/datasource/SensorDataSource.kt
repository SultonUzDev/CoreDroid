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
        Timber.d("monitorSensor - Starting monitoring for sensor type: $sensorType")
        val sensor = sensorManager.getDefaultSensor(sensorType)

        if (sensor == null) {
            Timber.w("monitorSensor - Sensor type $sensorType not available")
            close(IllegalArgumentException("Sensor type $sensorType is not available on this device"))
            return@callbackFlow
        }

        Timber.d("monitorSensor - Found sensor: ${sensor.name}, Type: ${sensor.type}")

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                try {
                    Timber.d("onSensorChanged - Sensor: ${sensor.name}, Values count: ${event.values.size}, Values: ${event.values.toList()}")

                    val valuesList = event.values.toList()
                    Timber.d("onSensorChanged - Converted to list, size: ${valuesList.size}, isEmpty: ${valuesList.isEmpty()}")

                    val sensorInfo = SensorInfo(
                        name = sensor.name,
                        type = sensor.type,
                        vendor = sensor.vendor,
                        version = sensor.version,
                        power = sensor.power,
                        resolution = sensor.resolution,
                        maximumRange = sensor.maximumRange,
                        values = valuesList,
                        accuracy = event.accuracy,
                        timestamp = event.timestamp
                    )

                    Timber.d("onSensorChanged - Created SensorInfo, values size: ${sensorInfo.values.size}")
                    val success = trySend(sensorInfo).isSuccess
                    Timber.d("onSensorChanged - Sent to flow, success: $success")
                } catch (e: Exception) {
                    Timber.e(e, "ERROR in onSensorChanged - Sensor: ${sensor.name}, Type: ${sensor.type}")
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Timber.d("onAccuracyChanged - Sensor: ${sensor?.name}, Accuracy: $accuracy")
            }
        }

        val registered = sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        if (!registered) {
            val errorMsg = "Failed to register sensor listener for ${sensor.name} (type $sensorType). " +
                    "This sensor may require special permissions or may not be supported."
            Timber.e(errorMsg)
            close(IllegalStateException(errorMsg))
            return@callbackFlow
        }

        awaitClose {
            sensorManager.unregisterListener(listener)
            Timber.d("Unregistered sensor listener for type $sensorType")
        }
    }
}