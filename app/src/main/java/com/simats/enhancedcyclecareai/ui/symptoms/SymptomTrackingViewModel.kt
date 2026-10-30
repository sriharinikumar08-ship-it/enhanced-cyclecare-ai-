package com.simats.enhancedcyclecareai.ui.symptoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SymptomTrackingUiEvent {
    data object NavigateBack : SymptomTrackingUiEvent
    data class ShowToast(val message: String) : SymptomTrackingUiEvent
}

class SymptomTrackingViewModel : ViewModel() {

    private val _dateText = MutableStateFlow("21 Oct 2026 · Yesterday")
    val dateText: StateFlow<String> = _dateText.asStateFlow()

    private val _selectedSymptoms = MutableStateFlow(setOf("Fatigue"))
    val selectedSymptoms: StateFlow<Set<String>> = _selectedSymptoms.asStateFlow()

    private val _focusedSymptom = MutableStateFlow("Fatigue")
    val focusedSymptom: StateFlow<String> = _focusedSymptom.asStateFlow()

    private val _severityLevel = MutableStateFlow(3f)
    val severityLevel: StateFlow<Float> = _severityLevel.asStateFlow()

    private val _durationText = MutableStateFlow("About 2 hours")
    val durationText: StateFlow<String> = _durationText.asStateFlow()

    private val _notesText = MutableStateFlow("Lower energy in the afternoon after a shorter night's sleep.")
    val notesText: StateFlow<String> = _notesText.asStateFlow()

    private val _selectedTab = MutableStateFlow("Track")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SymptomTrackingUiEvent>()
    val uiEvent: SharedFlow<SymptomTrackingUiEvent> = _uiEvent.asSharedFlow()

    fun toggleSymptom(symptom: String) {
        val current = _selectedSymptoms.value.toMutableSet()
        if (current.contains(symptom)) {
            current.remove(symptom)
        } else {
            current.add(symptom)
            _focusedSymptom.value = symptom
        }
        _selectedSymptoms.value = current
    }

    fun onDateChange(value: String) {
        _dateText.value = value
    }

    fun onSeverityChange(value: Float) {
        _severityLevel.value = value
    }

    fun onDurationChange(value: String) {
        _durationText.value = value
    }

    fun onNotesChange(value: String) {
        _notesText.value = value
    }

    fun onSaveSymptoms() {
        viewModelScope.launch {
            _uiEvent.emit(SymptomTrackingUiEvent.ShowToast("Symptoms saved successfully!"))
            _uiEvent.emit(SymptomTrackingUiEvent.NavigateBack)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(SymptomTrackingUiEvent.NavigateBack)
        }
    }
}
