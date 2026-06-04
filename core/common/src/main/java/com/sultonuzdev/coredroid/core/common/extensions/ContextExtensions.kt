package com.sultonuzdev.coredroid.core.common.extensions


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.shareText(text: String, title: String = "Share") {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    startActivity(Intent.createChooser(intent, title))
}

fun Context.isAtLeastVersion(apiLevel: Int): Boolean {
    return Build.VERSION.SDK_INT >= apiLevel
}