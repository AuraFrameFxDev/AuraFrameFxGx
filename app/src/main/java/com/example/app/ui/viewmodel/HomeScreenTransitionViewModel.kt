package com.example.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.system.homescreen.HomeScreenTransitionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenTransitionViewModel @Inject constructor(
    private val transitionManager: HomeScreenTransitionManager
) : ViewModel() {
    private val _currentConfig = MutableStateFlow<HomeScreenTransitionConfig?>(null)
    val currentConfig: StateFlow<HomeScreenTransitionConfig?> = _currentConfig

    init {
        viewModelScope.launch {
            transitionManager.currentConfig.collect { config ->
                _currentConfig.value = config
            }
        }
    }

    fun updateTransitionType(type: HomeScreenTransitionType) {
        viewModelScope.launch {
            transitionManager.updateTransitionType(type)
        }
    }

    fun updateTransitionDuration(duration: Int) {
        viewModelScope.launch {
            transitionManager.updateTransitionDuration(duration)
        }
    }

    fun updateTransitionProperties(properties: Map<String, Any>) {
        viewModelScope.launch {
            transitionManager.updateTransitionProperties(properties)
        }
    }

    fun resetToDefault() {
        viewModelScope.launch {
            transitionManager.resetToDefault()
        }
    }
}
