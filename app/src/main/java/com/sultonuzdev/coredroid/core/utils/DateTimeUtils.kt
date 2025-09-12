package com.sultonuzdev.coredroid.core.utils


import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val SHORT_DATE_FORMAT = "MMM dd, yyyy"
    private const val TIME_FORMAT = "HH:mm:ss"

    fun getCurrentTimestamp(): String {
        return SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(Date())
    }

    fun getCurrentDate(): String {
        return SimpleDateFormat(SHORT_DATE_FORMAT, Locale.getDefault()).format(Date())
    }

    fun getCurrentTime(): String {
        return SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(Date())
    }

    fun formatDate(timestamp: Long, format: String = DEFAULT_DATE_FORMAT): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date(timestamp))
    }

    fun parseDate(dateString: String, format: String = DEFAULT_DATE_FORMAT): Date? {
        return try {
            SimpleDateFormat(format, Locale.getDefault()).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

     fun formatTimestamp(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            sdf.format(Date(timestamp / 1000000)) // Convert nanoseconds to milliseconds
        } catch (e: Exception) {
            "Just now"
        }
    }
}