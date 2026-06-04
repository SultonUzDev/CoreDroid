package com.sultonuzdev.coredroid.data.datasource

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.media.AudioManager
import android.os.Build
import com.sultonuzdev.coredroid.domain.model.HardwareFeaturesInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class HardwareFeaturesDataSource(private val context: Context) {

    fun getHardwareFeatures(): Flow<HardwareFeaturesInfo> = flow {
        try {
            val packageManager = context.packageManager
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

            // Connectivity features
            val hasNfc = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
            val hasBluetooth = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)
            val hasBluetoothLe = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
            val hasWifi = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI)
            val hasWifiDirect = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)
            val hasWifi5Ghz = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_AWARE)
            } else false

            // Location features
            val hasGps = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)

            // Biometric features
            val hasFingerprint = packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
            val hasFaceUnlock = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
            } else false

            // Sensor features
            val hasAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null
            val hasGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null
            val hasCompass = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null
            val hasProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null
            val hasLightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null
            val hasBarometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null
            val hasPedometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null
            val hasHeartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null

            // Other hardware features
            val hasIrBlaster = packageManager.hasSystemFeature(PackageManager.FEATURE_CONSUMER_IR)
            val hasUsbHost = packageManager.hasSystemFeature(PackageManager.FEATURE_USB_HOST)
            val hasUsbAccessory = packageManager.hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY)

            // Audio features
            val microphoneCount = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    audioManager.getMicrophones()?.size ?: 1
                } else {
                    1 // Assume at least 1 microphone
                }
            } catch (e: Exception) {
                Timber.w(e, "Failed to get microphone count")
                1
            }

            // Bluetooth version
            val bluetoothVersion = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> "5.2+"
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> "5.0+"
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> "5.0"
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> "4.2+"
                else -> "4.0+"
            }

            // OpenGL ES version
            val openGlEsVersion = try {
                val featureInfo = packageManager.systemAvailableFeatures
                    .firstOrNull { it.name == null }
                if (featureInfo != null) {
                    val version = featureInfo.reqGlEsVersion
                    val major = version shr 16
                    val minor = version and 0xffff
                    "$major.$minor"
                } else {
                    "Unknown"
                }
            } catch (e: Exception) {
                Timber.w(e, "Failed to get OpenGL ES version")
                "Unknown"
            }

            val featuresInfo = HardwareFeaturesInfo(
                hasNfc = hasNfc,
                hasBluetooth = hasBluetooth,
                hasBluetoothLe = hasBluetoothLe,
                hasWifi = hasWifi,
                hasWifiDirect = hasWifiDirect,
                hasWifi5Ghz = hasWifi5Ghz,
                hasGps = hasGps,
                hasFingerprint = hasFingerprint,
                hasFaceUnlock = hasFaceUnlock,
                hasAccelerometer = hasAccelerometer,
                hasGyroscope = hasGyroscope,
                hasCompass = hasCompass,
                hasProximitySensor = hasProximitySensor,
                hasLightSensor = hasLightSensor,
                hasBarometer = hasBarometer,
                hasPedometerSensor = hasPedometerSensor,
                hasHeartRateSensor = hasHeartRateSensor,
                hasIrBlaster = hasIrBlaster,
                hasUsbHost = hasUsbHost,
                hasUsbAccessory = hasUsbAccessory,
                microphoneCount = microphoneCount,
                bluetoothVersion = bluetoothVersion,
                openGlEsVersion = openGlEsVersion
            )

            emit(featuresInfo)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get hardware features")
            emit(HardwareFeaturesInfo.empty())
        }
    }
}
