package com.example.getwell.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    var _display : MutableState<Boolean> = mutableStateOf(true)
    var selectedEmoji : MutableState<String> = mutableStateOf("")


    fun getDisplayState() : MutableState<Boolean>{
        return _display
    }
    fun setDisplayState(flag : Boolean){
        _display.value = flag
    }



    fun getEmojiState(): MutableState<String>{
        return selectedEmoji
    }
    fun setEmojiState(desc: String){
        selectedEmoji.value = desc
    }
}