package com.simats.enhancedcyclecareai.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DataSummaryUiEvent {
    data object NavigateBack : DataSummaryUiEvent
    data class EditSection(val sectionName: String) : DataSummaryUiEvent
    data class ShowToast(val message: String) : DataSummaryUiEvent
}

class DataSummaryViewModel : ViewModel() {

    private val _lastUpdatedText = MutableStateFlow("Updated 22 Oct 2026 · 9:40 AM")
    val lastUpdatedText: StateFlow<String> = _lastUpdatedText.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<DataSummaryUiEvent>()
    val uiEvent: SharedFlow<DataSummaryUiEvent> = _uiEvent.asSharedFlow()

    fun onEditSection(sectionName: String) {
        viewModelScope.launch {
            _uiEvent.emit(DataSummaryUiEvent.EditSection(sectionName))
        }
    }

    fun onViewDetails(sectionName: String) {
        viewModelScope.launch {
            _uiEvent.emit(DataSummaryUiEvent.ShowToast("Viewing details for $sectionName"))
        }
    }

    fun onAnalyzePatternsClicked() {
        viewModelScope.launch {
            _uiEvent.emit(DataSummaryUiEvent.ShowToast("Generating AI Pattern Insights..."))
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(DataSummaryUiEvent.NavigateBack)
        }
    }
}
