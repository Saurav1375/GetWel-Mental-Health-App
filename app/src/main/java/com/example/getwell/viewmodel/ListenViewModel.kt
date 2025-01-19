package com.example.getwell.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.relaxScreen.listen.ListenItem
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File

class ListenViewModel : ViewModel(){

    fun downloadItemFiles(context: Context, item: ListenItem, downloadStatus : MutableState<Boolean>, isDownloaded: MutableState<Boolean>) {
        viewModelScope.launch {
            val storage = Firebase.storage
            val filesDir = context.filesDir

            // Construct paths for video and audio files
            val videoPath = "video/${item.videoUri}" // Assuming item.videoUri contains the file name
            val audioPath = "audio/${item.musicUri}" // Assuming item.musicUri contains the file name
            downloadStatus.value = true

            // Download Video
            try {
                val videoRef = storage.reference.child(videoPath)
                val videoFile = File(filesDir, item.videoUri) // Save video with the same name
                videoRef.getFile(videoFile).await()

                // Download Audio
                val audioRef = storage.reference.child(audioPath)
                val audioFile = File(filesDir, item.musicUri) // Save audio with the same name
                audioRef.getFile(audioFile).await()


                isDownloaded.value = true
                Toast.makeText(context, "${item.title} downloaded", Toast.LENGTH_SHORT).show()


            } catch (e: Exception) {
                println(e.message)
                isDownloaded.value = false
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

            downloadStatus.value = false

        }

    }
}