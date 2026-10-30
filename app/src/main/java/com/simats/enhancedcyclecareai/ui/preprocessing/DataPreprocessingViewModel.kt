package com.simats.enhancedcyclecareai.ui.preprocessing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DataPreprocessingUiEvent {
    data object NavigateBack : DataPreprocessingUiEvent
    data object NavigateToNextStep : DataPreprocessingUiEvent
    data class ShowToast(val message: String) : DataPreprocessingUiEvent
}

class DataPreprocessingViewModel : ViewModel() {

    private val _progressPercentage = MutableStateFlow(75)
    val progressPercentage: StateFlow<Int> = _progressPercentage.asStateFlow()

    private val _completedCycles = MutableStateFlow(8)
    val completedCycles: StateFlow<Int> = _completedCycles.asStateFlow()

    private val _dailyCheckIns = MutableStateFlow("18 of 22 daily check-ins")
    val dailyCheckIns: StateFlow<String> = _dailyCheckIns.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<DataPreprocessingUiEvent>()
    val uiEvent: SharedFlow<DataPreprocessingUiEvent> = _uiEvent.asSharedFlow()

    fun onNextClicked() {
        viewModelScope.launch {
            _uiEvent.emit(DataPreprocessingUiEvent.NavigateToNextStep)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(DataPreprocessingUiEvent.NavigateBack)
        }
    }
}
