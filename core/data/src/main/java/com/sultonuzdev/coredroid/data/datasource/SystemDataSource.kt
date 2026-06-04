package com.sultonuzdev.coredroid.data.datasource


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import com.sultonuzdev.coredroid.domain.model.SystemInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.BufferedReader
import java.io.FileReader

class SystemDataSource() {

    fun getSystemInfo(): Flow<SystemInfo> = flow {

        emit(
            SystemInfo(
                androidVersion = Build.VERSION.RELEASE,
                apiLevel = Build.VERSION.SDK_INT,
                manufacturer = Build.MANUFACTURER,
                model = Build.MODEL,
                brand = Build.BRAND,
                device = Build.DEVICE,
                buildNumber = Build.DISPLAY,
                kernelVersion = getKernelVersion(),
                bootloader = Build.BOOTLOADER,
                baseband = getBaseband(),
                securityPatch = Build.VERSION.SECURITY_PATCH,
                isRooted = isDeviceRooted(),
                serialNumber = getSerialNumber()
            )
        )

    }.catch {
        Timber.e(it, "Failed to get system info")
        emit(SystemInfo.empty())
    }

    private fun getKernelVersion(): String {
        return try {
            BufferedReader(FileReader("/proc/version")).use { reader ->
                reader.readLine().split(" ")[2]
            }
        } catch (e: Exception) {
            "Unknown"
        }
    }

    private fun getBaseband(): String {
        return try {
            Build.getRadioVersion() ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }

    private fun isDeviceRooted(): Boolean {
        return try {
            val paths = arrayOf(
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
            )

            for (path in paths) {
                if (java.io.File(path).exists()) return true
            }
            false
        } catch (e: Exception) {
            false
        }
    }

    @RequiresPermission("android.permission.READ_PRIVILEGED_PHONE_STATE")
    private fun getSerialNumber(): String {
        return try {
            Build.getSerial()
        } catch (e: Exception) {
            "Unknown"
        }
    }
}
