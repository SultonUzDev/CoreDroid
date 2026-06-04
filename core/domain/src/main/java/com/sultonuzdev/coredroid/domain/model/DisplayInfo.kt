package com.sultonuzdev.coredroid.domain.model


data class DisplayInfo(
    val widthPixels: Int,
    val heightPixels: Int,
    val densityDpi: Int,
    val density: Float,
    val screenSizeInches: Float,
    val refreshRate: Float,
    val orientation: String,
    val hdrSupported: Boolean
) {
    val resolution: String
        get() = "$widthPixels x $heightPixels"

    companion object {
        fun empty() = DisplayInfo(
            widthPixels = 0,
            heightPixels = 0,
            densityDpi = 0,
            density = 0f,
            screenSizeInches = 0f,
            refreshRate = 0f,
            orientation = "Unknown",
            hdrSupported = false
        )
    }
}