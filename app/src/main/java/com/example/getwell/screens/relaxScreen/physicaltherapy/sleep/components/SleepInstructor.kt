package com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.components

import android.speech.tts.TextToSpeech
import android.content.Context
import androidx.compose.runtime.*
import com.example.getwell.screens.relaxScreen.physicaltherapy.sleep.SleepState
import java.util.*

class SleepInstructor(context: Context) {
    private var textToSpeech: TextToSpeech? = null
    
    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
            }
        }
    }
    
    fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    
    fun speakSleepState(state: SleepState) {
        val text = when (state) {
            SleepState.GET_UP -> "If you can't sleep, don't worry. Get up and sit in a comfortable chair."
            SleepState.ACTIVITY -> "Choose a relaxing activity like reading a book or listening to calm music."
            SleepState.RELAX -> "Stay relaxed and remember not to force yourself to sleep."
            SleepState.DROWSY -> "When you start feeling drowsy, you can return to bed."
            SleepState.IDLE -> "Welcome to the sleep relaxation exercise."
        }
        speak(text)
    }
    
    fun release() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}

@Composable
fun rememberSleepInstructor(context: Context): SleepInstructor {
    val speechInstructor = remember { SleepInstructor(context) }
    
    DisposableEffect(Unit) {
        onDispose {
            speechInstructor.release()
        }
    }
    
    return speechInstructor
}