package com.example.getwell.screens.settingspage.avatar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun AvatarChangeScreen(viewModel: AvatarViewModel) {
    val context = LocalContext.current
    val avatarUrl by viewModel.avatarUrl.collectAsState()

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateAvatar(it)
        }
    }

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        // Avatar Display with Edit Icon
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = androidx.compose.ui.Alignment.BottomEnd
        ) {
            // Display Avatar
            AsyncImage(
                model = avatarUrl,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(BorderStroke(2.dp, Color.White), CircleShape)
                    .clickable {
                        // Open gallery when avatar is clicked
                        imagePickerLauncher.launch("image/*")
                    }
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            // Edit Icon
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Change Avatar",
                modifier = Modifier
                    .background(Color(53,101,111), shape = CircleShape)
                    .padding(12.dp)
                    .clickable {
                        // Open gallery
                        imagePickerLauncher.launch("image/*")
                    },
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Optional: Descriptive Text
        Text(
            text = "Click on the avatar to change your profile picture",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}
