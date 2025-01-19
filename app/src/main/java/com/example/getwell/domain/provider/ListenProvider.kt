package com.example.getwell.domain.provider

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

object ListenProvider {

    fun getDownloadPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("DownloadPrefs", Context.MODE_PRIVATE)
    }
    fun markFileDownloaded(context: Context, fileName: String) {
        val prefs = getDownloadPreferences(context)
        prefs.edit().putBoolean(fileName, true).apply()
    }

    fun isFileDownloaded(context: Context, fileName: String): Boolean {
        val prefs = getDownloadPreferences(context)
        return prefs.getBoolean(fileName, false)
    }

    suspend fun downloadFile(context: Context, fileName: String): Uri? {
        val storage = Firebase.storage
        val fileRef = storage.reference.child(fileName)

        // Define a local file in cache directory to store downloaded file
        val localFile = File(context.cacheDir, fileName)
        return try {
            // Download file to the local cache
            fileRef.getFile(localFile).await()
            Uri.fromFile(localFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}