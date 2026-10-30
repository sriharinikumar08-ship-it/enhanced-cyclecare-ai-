package com.simats.enhancedcyclecareai.ui.pcospattern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PcosUiEvent {
    data object NavigateBack : PcosUiEvent
    data object ContinueTracking : PcosUiEvent
    data object SeekProfessionalAdvice : PcosUiEvent
    data class ShowToast(val message: String) : PcosUiEvent
}

class PcosRelatedPatternViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PcosPatternUiState())
    val uiState: StateFlow<PcosPatternUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PcosUiEvent>()
    val uiEvent: SharedFlow<PcosUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PcosUiEvent.NavigateBack)
        }
    }

    fun onContinueTrackingClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PcosUiEvent.ShowToast("Continuing tracking..."))
            _uiEvent.emit(PcosUiEvent.ContinueTracking)
        }
    }

    fun onSeekProfessionalAdviceClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PcosUiEvent.ShowToast("Opening professional guidance..."))
            _uiEvent.emit(PcosUiEvent.SeekProfessionalAdvice)
        }
    }
}

data class PcosPatternUiState(
    val badgeText: String = "Moderate – Pattern awareness only",
    val warningText: String = "This is a risk-pattern indication, not a medical diagnosis.",
    val whatDoesThisMeanTitle: String = "What does this mean?",
    val whatDoesThisMeanBody: String = "Your cycle records show some variation. Variation can have many explanations, and some patterns overlap with questions a clinician may ask about PCOS.\n\nThese entries cannot tell whether you have PCOS. A professional assessment uses more information than this app collects.",
    val recordedIndicatorsTitle: String = "Recorded indicators",
    val supportingHistoryTitle: String = "Supporting history",
    val certaintyTitle: String = "How certain is this explanation?",
    val confidenceBadge: String = "Limited confidence",
    val confidenceDescription: String = "Illustrative explanation confidence, not a validated diagnostic score or a probability of PCOS. Missing daily entries and limited clinical context reduce certainty.",
    val actionToDoTitle: String = "What you can do",
    val actionToDoBody: String = "Keep recording dates and symptoms. If changes persist, periods are absent, or you have concerns, discuss your history with a qualified healthcare professional."
)
