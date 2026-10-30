package com.simats.enhancedcyclecareai.ui.patterndetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PatternDetectionUiEvent {
    data object NavigateBack : PatternDetectionUiEvent
    data class ShowToast(val message: String) : PatternDetectionUiEvent
    data object NavigateToGuidance : PatternDetectionUiEvent
    data object NavigateToPcosPattern : PatternDetectionUiEvent
    data object NavigateToGeneralMenstrualPattern : PatternDetectionUiEvent
}

class PatternDetectionViewModel : ViewModel() {

    private val _lastUpdatedText = MutableStateFlow("Updated 22 Oct · Based on your entries")
    val lastUpdatedText: StateFlow<String> = _lastUpdatedText.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PatternDetectionUiEvent>()
    val uiEvent: SharedFlow<PatternDetectionUiEvent> = _uiEvent.asSharedFlow()

    fun onViewDetails(patternName: String) {
        viewModelScope.launch {
            if (patternName.contains("PCOS", ignoreCase = true)) {
                _uiEvent.emit(PatternDetectionUiEvent.NavigateToPcosPattern)
            } else if (patternName.contains("Cycle", ignoreCase = true)) {
                _uiEvent.emit(PatternDetectionUiEvent.NavigateToGeneralMenstrualPattern)
            } else {
                _uiEvent.emit(PatternDetectionUiEvent.ShowToast("Viewing details for $patternName"))
            }
        }
    }

    fun onExploreGuidanceClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PatternDetectionUiEvent.ShowToast("Opening Personalized Guidance..."))
            _uiEvent.emit(PatternDetectionUiEvent.NavigateToGuidance)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PatternDetectionUiEvent.NavigateBack)
        }
    }
}
