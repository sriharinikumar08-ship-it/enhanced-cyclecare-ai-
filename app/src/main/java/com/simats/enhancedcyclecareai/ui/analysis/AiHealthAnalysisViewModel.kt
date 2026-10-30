package com.simats.enhancedcyclecareai.ui.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AiHealthAnalysisUiEvent {
    data object NavigateBack : AiHealthAnalysisUiEvent
    data object NavigateToNextStep : AiHealthAnalysisUiEvent
    data class ShowToast(val message: String) : AiHealthAnalysisUiEvent
}

class AiHealthAnalysisViewModel : ViewModel() {

    private val _analysisProgress = MutableStateFlow(82)
    val analysisProgress: StateFlow<Int> = _analysisProgress.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AiHealthAnalysisUiEvent>()
    val uiEvent: SharedFlow<AiHealthAnalysisUiEvent> = _uiEvent.asSharedFlow()

    fun onNextClicked() {
        viewModelScope.launch {
            _uiEvent.emit(AiHealthAnalysisUiEvent.NavigateToNextStep)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(AiHealthAnalysisUiEvent.NavigateBack)
        }
    }
}
