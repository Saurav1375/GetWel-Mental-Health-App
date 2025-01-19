package com.example.getwell.screens.settingspage.avatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.auth.UserProfileChangeRequest

class AvatarViewModel : ViewModel() {
    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl = _avatarUrl.asStateFlow()

    init {
        // Fetch current user's avatar on initialization
        fetchCurrentUserAvatar()
    }

    private fun fetchCurrentUserAvatar() {
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            // Priority: 1. Custom uploaded avatar 2. Google Profile Picture
            _avatarUrl.value = currentUser?.photoUrl?.toString()
        }
    }

    fun updateAvatar(imageUri: Uri) {
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.uid?.let { userId ->
                // Upload to Firebase Storage
                val storageRef = FirebaseStorage.getInstance()
                    .reference
                    .child("profile_images/$userId/avatar.jpg")

                try {
                    // Upload image
                    val uploadTask = storageRef.putFile(imageUri).await()

                    // Get download URL
                    val downloadUrl = storageRef.downloadUrl.await()

                    // Update avatar URL in ViewModel and potentially in Firebase user profile
                    _avatarUrl.value = downloadUrl.toString()
                    updateProfilePicture(downloadUrl)
                } catch (e: Exception) {
                    // Handle upload failure
                    println("Avatar upload failed: ${e.message}")
                }
            }
        }
    }


    private fun updateProfilePicture(downloadUri: Uri) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUri)
                .build()

            user.updateProfile(profileUpdates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ProfileUpdate", "User profile updated successfully.")
                } else {
                    Log.e("ProfileUpdateError", "Error updating user profile: ${task.exception?.message}")
                }
            }
        }
    }
}

