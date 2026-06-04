package com.sultonuzdev.coredroid.data.datasource


import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.sultonuzdev.coredroid.domain.model.DisplayInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import kotlin.math.sqrt

class DisplayDataSource(private val context: Context) {

    fun getDisplayInfo(): Flow<DisplayInfo> = flow {

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getRealMetrics(displayMetrics)

        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        val densityDpi = displayMetrics.densityDpi
        val density = displayMetrics.density

        // Calculate screen size in inches
        val widthInches = widthPixels / densityDpi.toFloat()
        val heightInches = heightPixels / densityDpi.toFloat()
        val screenSizeInches = sqrt(widthInches * widthInches + heightInches * heightInches)

        // Get refresh rate
        val refreshRate = display.refreshRate

        emit(
            DisplayInfo(
                widthPixels = widthPixels,
                heightPixels = heightPixels,
                densityDpi = densityDpi,
                density = density,
                screenSizeInches = screenSizeInches,
                refreshRate = refreshRate,
                orientation = getOrientation(),
                hdrSupported = false // Would need more complex detection
            )
        )
    }.catch { e ->
        Timber.e(e, "Failed to get display info")
        emit(DisplayInfo.empty())
    }

    private fun getOrientation(): String {
        return try {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            when (windowManager.defaultDisplay.rotation) {
                0 -> "Portrait"
                1 -> "Landscape"
                2 -> "Reverse Portrait"
                3 -> "Reverse Landscape"
                else -> "Unknown"
            }
        } catch (e: Exception) {
            "Unknown"
        }
    }
}
