package com.example.getwell.screens.stressanalysis

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageAnalysisScreen(
    onDismiss: () -> Unit,
    viewModel: StressAnalysisViewModel
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri?.let { uri ->
                viewModel.analyzeImage(uri)
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Image Analysis") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        imageUri = viewModel.createImageUri()
                        imageUri?.let { launcher.launch(it) }
                    }
                ) {
                    Text("Take Photo")
                }
                
                if (viewModel.isAnalyzing) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}