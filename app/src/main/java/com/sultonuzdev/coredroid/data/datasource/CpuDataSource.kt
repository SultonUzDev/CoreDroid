package com.sultonuzdev.coredroid.data.datasource


import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.sultonuzdev.coredroid.domain.model.CpuInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.BufferedReader
import java.io.FileReader
import java.io.RandomAccessFile

class CpuDataSource(private val context: Context) {

    fun getCpuInfo(): Flow<CpuInfo> = flow {

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val totalRam = memoryInfo.totalMem
        val availableRam = memoryInfo.availMem
        val usedRam = totalRam - availableRam

        val cpuName = getCpuName()
        val architecture = Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown"
        val coreCount = Runtime.getRuntime().availableProcessors()
        val maxFrequency = getMaxCpuFrequency()
        val currentFrequency = getCurrentCpuFrequency()

        emit(
            CpuInfo(
                name = cpuName,
                architecture = architecture,
                coreCount = coreCount,
                maxFrequency = maxFrequency,
                currentFrequency = currentFrequency,
                totalRam = totalRam,
                availableRam = availableRam,
                usedRam = usedRam,
                cpuUsage = getCpuUsage()
            )
        )
    }.catch { e ->
        Timber.e(e, "Failed to get CPU info")
        emit(CpuInfo.empty())
    }


private fun getCpuName(): String {
    return try {
        BufferedReader(FileReader("/proc/cpuinfo")).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.startsWith("Hardware") || line!!.startsWith("Processor")) {
                    return line!!.split(":")[1].trim()
                }
            }
            Build.HARDWARE
        }
    } catch (e: Exception) {
        Build.HARDWARE
    }
}

private fun getMaxCpuFrequency(): Long {
    return try {
        var maxFreq = 0L
        for (i in 0 until Runtime.getRuntime().availableProcessors()) {
            val freqFile = "/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_max_freq"
            try {
                BufferedReader(FileReader(freqFile)).use { reader ->
                    val freq = reader.readLine()?.toLongOrNull() ?: 0L
                    if (freq > maxFreq) maxFreq = freq
                }
            } catch (e: Exception) {
                // Ignore individual core failures
            }
        }
        maxFreq / 1000 // Convert from kHz to MHz
    } catch (e: Exception) {
        0L
    }
}

private fun getCurrentCpuFrequency(): Long {
    return try {
        var currentFreq = 0L
        for (i in 0 until Runtime.getRuntime().availableProcessors()) {
            val freqFile = "/sys/devices/system/cpu/cpu$i/cpufreq/scaling_cur_freq"
            try {
                BufferedReader(FileReader(freqFile)).use { reader ->
                    val freq = reader.readLine()?.toLongOrNull() ?: 0L
                    if (freq > currentFreq) currentFreq = freq
                }
            } catch (e: Exception) {
                // Ignore individual core failures
            }
        }
        currentFreq / 1000 // Convert from kHz to MHz
    } catch (e: Exception) {
        0L
    }
}

private fun getCpuUsage(): Float {
    return try {
        val reader = RandomAccessFile("/proc/stat", "r")
        val load = reader.readLine()
        reader.close()

        val toks = load.split(" ")
        val idle1 = toks[4].toLong()
        val cpu1 = toks[2].toLong() + toks[3].toLong() + toks[5].toLong() +
                toks[6].toLong() + toks[7].toLong() + toks[8].toLong()

        Thread.sleep(360)

        val reader2 = RandomAccessFile("/proc/stat", "r")
        val load2 = reader2.readLine()
        reader2.close()

        val toks2 = load2.split(" ")
        val idle2 = toks2[4].toLong()
        val cpu2 = toks2[2].toLong() + toks2[3].toLong() + toks2[5].toLong() +
                toks2[6].toLong() + toks2[7].toLong() + toks2[8].toLong()

        ((cpu2 - cpu1).toFloat() / ((cpu2 + idle2) - (cpu1 + idle1))) * 100
    } catch (e: Exception) {
        0f
    }
}
}