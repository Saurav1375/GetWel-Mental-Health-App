package com.example.getwell.screens.settingspage.datastorage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.getwell.screens.settingspage.datastorage.AppStorageAnalyzer
import com.example.getwell.screens.settingspage.datastorage.DataUsageInfo
import com.example.getwell.screens.settingspage.datastorage.StorageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Example usage in your Settings ViewModel
class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val storageAnalyzer = AppStorageAnalyzer(application)

    private val _storageInfo = MutableStateFlow<StorageInfo?>(null)
    val storageInfo: StateFlow<StorageInfo?> = _storageInfo.asStateFlow()

    private val _dataUsage = MutableStateFlow<DataUsageInfo?>(null)
    val dataUsage: StateFlow<DataUsageInfo?> = _dataUsage.asStateFlow()

    fun refreshStorageInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _storageInfo.value = storageAnalyzer.getAppStorageUsage()
            _dataUsage.value = storageAnalyzer.getDataUsage()
        }
    }

    fun clearCache() {
        viewModelScope.launch(Dispatchers.IO) {
            storageAnalyzer.clearAppCache()
            refreshStorageInfo()
        }
    }
}