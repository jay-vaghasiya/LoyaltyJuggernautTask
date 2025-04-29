package com.jay.loyaltyjuggernauttask.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class StateViewModel:ViewModel() {
    private val _language = mutableStateOf("")
    val language: State<String> get() = _language


    fun setLanguage(newLanguage: String) {
        _language.value = newLanguage
    }
}