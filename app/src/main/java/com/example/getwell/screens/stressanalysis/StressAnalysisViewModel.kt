package com.example.getwell.screens.stressanalysis

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.di.Injection
import com.example.getwell.screens.stressanalysis.analysis.ImageAnalyzer
import com.example.getwell.screens.stressanalysis.analysis.SpeechAnalyzer
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException

class StressAnalysisViewModel(
    application: Application
) : AndroidViewModel(application) {
    var isAnalyzing by mutableStateOf(false)
    var analysisResult by mutableStateOf<String?>(null)

    private val imageAnalyzer = ImageAnalyzer()
    private val speechAnalyzer = SpeechAnalyzer()
    private val firestore: FirebaseFirestore = Injection.instance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    val userEmail = currentUser?.email
    private val faceDetector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
    )

    fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return getApplication<Application>().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    fun analyzeImage(uri: Uri) {
        viewModelScope.launch {
            isAnalyzing = true
            try {
                val image = InputImage.fromFilePath(getApplication(), uri)
                faceDetector.process(image)
                    .addOnSuccessListener { faces ->
                        if (faces.isEmpty()) {
                            analysisResult = "No face detected, Please take photo of your image in a bright environment"
                            return@addOnSuccessListener
                        }

                        val face = faces[0]
                        val (stressLevel, indicators) = imageAnalyzer.analyzeFacialFeatures(face)

                        val analysisText = buildString {
                            append("Facial Analysis Results:\n\n")
                            append("Overall Stress Level: ${(stressLevel * 100).toInt()}%\n\n")
                            append("Detected Indicators:\n")
                            indicators.forEach { append("- $it\n") }
                            append("\nRecommendations:\n")
                            when {
                                stressLevel > 0.7f -> {
                                    append("High Stress Detected:\n")
                                    append("- Immediate break recommended\n")
                                    append("- Practice deep breathing exercises\n")
                                    append("- Consider speaking with a counselor\n")
                                    append("- Take a short walk outside\n")
                                    append("- Reduce screen time if possible")
                                }
                                stressLevel > 0.4f -> {
                                    append("Moderate Stress Detected:\n")
                                    append("- Take regular breaks\n")
                                    append("- Practice mindfulness exercises\n")
                                    append("- Maintain work-life balance\n")
                                    append("- Consider stress management techniques")
                                }
                                else -> {
                                    append("Low Stress Detected:\n")
                                    append("- Maintain current well-being practices\n")
                                    append("- Continue regular breaks and self-care\n")
                                    append("- Monitor stress levels periodically")
                                }
                            }
                        }
                        viewModelScope.launch {
                            saveAIData(userEmail!!,(stressLevel * 100).toInt(), "Face" )
                        }

                        analysisResult = analysisText
                    }
                    .addOnFailureListener { e ->
                        analysisResult = "Analysis failed: ${e.message}"
                    }
                    .addOnCompleteListener {
                        isAnalyzing = false
                    }
            } catch (e: IOException) {
                analysisResult = "Error processing image: ${e.message}"
                isAnalyzing = false
            }
        }
    }

    fun analyzeSpeech(spokenText: String) {
        viewModelScope.launch {
            isAnalyzing = true
            
            val (stressScore, indicators, voiceIndicators) = speechAnalyzer.analyzeSpeech(spokenText)

            val analysisText = buildString {
                append("Speech Analysis Results:\n\n")
                append("Overall Stress Level: ${(stressScore * 100).toInt()}%\n\n")
                append("Analysis Details:\n")
                indicators.forEach { append("- $it\n") }
                append("\nVoice Metrics:\n")
                append("- Speaking Rate: ${voiceIndicators.speakingRate.toInt()} words per minute\n")
                append("- Word Repetitions: ${voiceIndicators.wordRepetitions}\n")
                append("- Stress Keywords: ${voiceIndicators.stressKeywordCount}\n")
                append("- Positive Keywords: ${voiceIndicators.positiveKeywordCount}\n")
                
                append("\nRecommendations:\n")
                when {
                    stressScore > 0.7f -> {
                        append("High Stress Indicators:\n")
                        append("- Consider immediate stress relief activities\n")
                        append("- Schedule a counseling session\n")
                        append("- Take a break from current tasks\n")
                        append("- Practice calming breathing exercises")
                    }
                    stressScore > 0.4f -> {
                        append("Moderate Stress Indicators:\n")
                        append("- Implement regular stress management\n")
                        append("- Practice mindfulness techniques\n")
                        append("- Maintain healthy work-life balance")
                    }
                    else -> {
                        append("Low Stress Indicators:\n")
                        append("- Continue current well-being practices\n")
                        append("- Maintain regular self-care routine\n")
                        append("- Schedule periodic stress check-ins")
                    }
                }
            }
            viewModelScope.launch {
                saveAIData(userEmail!!,(stressScore * 100).toInt(), "Speech" )
            }
            analysisResult = analysisText
            isAnalyzing = false
        }
    }


    suspend fun saveAIData(userId: String, stressLevel : Int, id : String) {
        val stressObj = StressAIObj(
            level = stressLevel,
            timestamp = Timestamp.now() // Current time
        )

        val stressData = hashMapOf(
            "level" to stressObj.level,
            "timestamp" to stressObj.timestamp
        )

        try {
            // Save the mood data to Firestore under the "moods" collection
            firestore
                .collection("users")
                .document(userId)
                .collection("StressQuiz")
                .document(id)
                .collection("response")
                .add(stressData)
                .await()// Wait for the Firestore operation to complete
            println("AI data saved successfully")
        } catch (exception: Exception) {
            println("Error saving data: ${exception.message}")
        }
    }
}

data class StressAIObj(
    val level: Int = 0,  // Default value
    val timestamp: Timestamp = Timestamp.now()  // Default value
)