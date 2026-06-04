package com.sultonuzdev.coredroid.core.common.utils

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import com.sultonuzdev.coredroid.core.common.extensions.hasPermission
import com.sultonuzdev.coredroid.core.utils.Constants

class PermissionManager(private val context: Context) {

    fun hasPhoneStatePermission(): Boolean = context.hasPermission(Constants.PERMISSION_READ_PHONE_STATE)

    fun hasWifiPermission(): Boolean = context.hasPermission(Constants.PERMISSION_ACCESS_WIFI_STATE)

    fun hasNetworkPermission(): Boolean = context.hasPermission(Constants.PERMISSION_ACCESS_NETWORK_STATE)

    fun hasCameraPermission(): Boolean = context.hasPermission(Constants.PERMISSION_CAMERA)

    fun hasStoragePermission(): Boolean = context.hasPermission(Constants.PERMISSION_WRITE_EXTERNAL_STORAGE)

    fun hasAllRequiredPermissions(): Boolean {
        return hasNetworkPermission() && hasWifiPermission()
    }

    fun getRequiredPermissions(): Array<String> {
        return arrayOf(
            Constants.PERMISSION_ACCESS_NETWORK_STATE,
            Constants.PERMISSION_ACCESS_WIFI_STATE,
            Constants.PERMISSION_READ_PHONE_STATE,
            Constants.PERMISSION_WRITE_EXTERNAL_STORAGE
        )
    }

    /**
     * Check if permission is permanently denied
     * Returns true if permission is denied and shouldn't show rationale
     */
    fun isPermissionPermanentlyDenied(activity: Activity, permission: String): Boolean {
        val hasPermission = context.hasPermission(permission)
        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

        return !hasPermission && !shouldShowRationale
    }

    /**
     * Check if camera permission is permanently denied
     */
    fun isCameraPermissionPermanentlyDenied(activity: Activity): Boolean {
        return isPermissionPermanentlyDenied(activity, Constants.PERMISSION_CAMERA)
    }

    /**
     * Check if permission should show rationale
     */
    fun shouldShowPermissionRationale(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }
}