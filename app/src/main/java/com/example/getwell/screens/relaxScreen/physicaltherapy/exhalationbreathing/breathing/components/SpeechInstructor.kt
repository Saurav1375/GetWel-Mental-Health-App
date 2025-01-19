package com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.components
import android.speech.tts.TextToSpeech
import android.content.Context
import androidx.compose.runtime.*
import com.example.getwell.screens.relaxScreen.physicaltherapy.exhalationbreathing.breathing.BreathingState
import java.util.*

class SpeechInstructor(context: Context) {
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

    fun speakBreathingState(state: BreathingState) {
        val text = when (state) {
            BreathingState.INHALE -> "Breathe in slowly, raising your arms"
            BreathingState.HOLD -> "Hold your breath, arms above"
            BreathingState.EXHALE -> "Breathe out slowly, lowering arms"
            BreathingState.IDLE -> "Get ready for breathing exercise"
        }
        speak(text)
    }

    fun release() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}

@Composable
fun rememberSpeechInstructor(context: Context): SpeechInstructor {
    val speechInstructor = remember { SpeechInstructor(context) }

    DisposableEffect(Unit) {
        onDispose {
            speechInstructor.release()
        }
    }

    return speechInstructor
}