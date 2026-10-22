package com.simats.enhancedcyclecareai.ui.cycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CycleTrackingUiEvent {
    data object NavigateBack : CycleTrackingUiEvent
    data class ShowToast(val message: String) : CycleTrackingUiEvent
}

class CycleTrackingViewModel : ViewModel() {

    private val _monthYearText = MutableStateFlow("October 2026")
    val monthYearText: StateFlow<String> = _monthYearText.asStateFlow()

    private val _currentCycleDay = MutableStateFlow(18)
    val currentCycleDay: StateFlow<Int> = _currentCycleDay.asStateFlow()

    private val _typicalCycleLength = MutableStateFlow(32)
    val typicalCycleLength: StateFlow<Int> = _typicalCycleLength.asStateFlow()

    private val _periodStartText = MutableStateFlow("5 Oct")
    val periodStartText: StateFlow<String> = _periodStartText.asStateFlow()

    private val _nextPeriodText = MutableStateFlow("6 November")
    val nextPeriodText: StateFlow<String> = _nextPeriodText.asStateFlow()

    private val _todayDay = MutableStateFlow(22)
    val todayDay: StateFlow<Int> = _todayDay.asStateFlow()

    private val _periodDays = MutableStateFlow(setOf(5, 6, 7, 8, 9))
    val periodDays: StateFlow<Set<Int>> = _periodDays.asStateFlow()

    private val _selectedTab = MutableStateFlow("Track")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CycleTrackingUiEvent>()
    val uiEvent: SharedFlow<CycleTrackingUiEvent> = _uiEvent.asSharedFlow()

    fun onDateSelected(day: Int) {
        _todayDay.value = day
    }

    fun onStartPeriod() {
        viewModelScope.launch {
            _uiEvent.emit(CycleTrackingUiEvent.ShowToast("Period started for day ${_todayDay.value}"))
        }
    }

    fun onEndPeriod() {
        viewModelScope.launch {
            _uiEvent.emit(CycleTrackingUiEvent.ShowToast("Period ended"))
        }
    }

    fun onAddRecord() {
        viewModelScope.launch {
            _uiEvent.emit(CycleTrackingUiEvent.ShowToast("Add new symptom/flow record"))
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(CycleTrackingUiEvent.NavigateBack)
        }
    }
}
