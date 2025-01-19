package com.example.getwell.screens.settingspage.datastorage

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.TrafficStats
import android.os.Build
import android.os.storage.StorageManager
import java.io.File

class AppStorageAnalyzer(private val context: Context) {
    /**
     * Gets the total storage usage of the app including cache, data, and APK size
     */
    fun getAppStorageUsage(): StorageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getStorageModern()
        } else {
            getStorageLegacy()
        }
    }

    /**
     * Gets storage info for Android O and above using StorageStatsManager
     */
    private fun getStorageModern(): StorageInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val storageStatsManager = context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val uuid = StorageManager.UUID_DEFAULT

            val appPackage = context.packageName
            val userHandle = android.os.Process.myUserHandle()

            val storageStats = storageStatsManager.queryStatsForPackage(
                uuid, appPackage, userHandle
            )

            return StorageInfo(
                appSize = storageStats.appBytes,
                cacheSize = storageStats.cacheBytes,
                dataSize = storageStats.dataBytes,
                totalSize = storageStats.appBytes + storageStats.cacheBytes + storageStats.dataBytes
            )
        }
        return getStorageLegacy()
    }

    /**
     * Gets storage info for older Android versions using File APIs
     */
    private fun getStorageLegacy(): StorageInfo {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val appFile = File(packageInfo.applicationInfo.sourceDir)

        val cacheSize = getDirSize(context.cacheDir) +
                (if (context.externalCacheDir != null) getDirSize(context.externalCacheDir!!) else 0)

        val dataSize = getDirSize(context.dataDir) - cacheSize
        val appSize = appFile.length()

        return StorageInfo(
            appSize = appSize,
            cacheSize = cacheSize,
            dataSize = dataSize,
            totalSize = appSize + cacheSize + dataSize
        )
    }

    /**
     * Gets the size of a directory recursively
     */
    private fun getDirSize(dir: File): Long {
        var size: Long = 0
        dir.listFiles()?.forEach { file ->
            size += if (file.isDirectory) {
                getDirSize(file)
            } else {
                file.length()
            }
        }
        return size
    }

    /**
     * Gets network data usage statistics
     */
    fun getDataUsage(): DataUsageInfo {
        val uid = try {
            context.packageManager.getApplicationInfo(context.packageName, 0).uid
        } catch (e: PackageManager.NameNotFoundException) {
            return DataUsageInfo(0, 0, 0)
        }

        return DataUsageInfo(
            receivedBytes = TrafficStats.getUidRxBytes(uid),
            transmittedBytes = TrafficStats.getUidTxBytes(uid),
            totalBytes = TrafficStats.getUidRxBytes(uid) + TrafficStats.getUidTxBytes(uid)
        )
    }

    /**
     * Clears the app cache
     */
    fun clearAppCache() {
        try {
            context.cacheDir.deleteRecursively()
            context.externalCacheDir?.deleteRecursively()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * Data class to hold storage information
 */
data class StorageInfo(
    val appSize: Long,      // Size of the APK
    val cacheSize: Long,    // Size of cache
    val dataSize: Long,     // Size of app data
    val totalSize: Long     // Total storage used
)

/**
 * Data class to hold data usage information
 */
data class DataUsageInfo(
    val receivedBytes: Long,    // Downloaded data
    val transmittedBytes: Long, // Uploaded data
    val totalBytes: Long        // Total data used
)

