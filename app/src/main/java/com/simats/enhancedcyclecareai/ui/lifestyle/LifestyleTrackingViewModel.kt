package com.simats.enhancedcyclecareai.ui.lifestyle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LifestyleTrackingUiEvent {
    data object NavigateBack : LifestyleTrackingUiEvent
    data class ShowToast(val message: String) : LifestyleTrackingUiEvent
}

class LifestyleTrackingViewModel : ViewModel() {

    private val _dateText = MutableStateFlow("22 Oct 2026 · Today")
    val dateText: StateFlow<String> = _dateText.asStateFlow()

    private val _sleepHours = MutableStateFlow(6.5f)
    val sleepHours: StateFlow<Float> = _sleepHours.asStateFlow()

    private val _stressLevel = MutableStateFlow(3f)
    val stressLevel: StateFlow<Float> = _stressLevel.asStateFlow()

    private val _selectedActivity = MutableStateFlow("Walking")
    val selectedActivity: StateFlow<String> = _selectedActivity.asStateFlow()

    private val _activeMinutes = MutableStateFlow("25 minutes")
    val activeMinutes: StateFlow<String> = _activeMinutes.asStateFlow()

    private val _hydrationGlasses = MutableStateFlow(6)
    val hydrationGlasses: StateFlow<Int> = _hydrationGlasses.asStateFlow()

    private val _otherObservations = MutableStateFlow("Busy day at university. Took a short walk between classes.")
    val otherObservations: StateFlow<String> = _otherObservations.asStateFlow()

    private val _selectedTab = MutableStateFlow("Track")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LifestyleTrackingUiEvent>()
    val uiEvent: SharedFlow<LifestyleTrackingUiEvent> = _uiEvent.asSharedFlow()

    fun onDateChange(value: String) {
        _dateText.value = value
    }

    fun onSleepHoursChange(value: Float) {
        _sleepHours.value = value
    }

    fun onStressLevelChange(value: Float) {
        _stressLevel.value = value
    }

    fun onActivityChange(value: String) {
        _selectedActivity.value = value
    }

    fun onActiveMinutesChange(value: String) {
        _activeMinutes.value = value
    }

    fun addHydration() {
        if (_hydrationGlasses.value < 12) {
            _hydrationGlasses.value += 1
        }
    }

    fun removeHydration() {
        if (_hydrationGlasses.value > 0) {
            _hydrationGlasses.value -= 1
        }
    }

    fun onOtherObservationsChange(value: String) {
        _otherObservations.value = value
    }

    fun onSaveAndContinue() {
        viewModelScope.launch {
            _uiEvent.emit(LifestyleTrackingUiEvent.ShowToast("Lifestyle log saved!"))
            _uiEvent.emit(LifestyleTrackingUiEvent.NavigateBack)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(LifestyleTrackingUiEvent.NavigateBack)
        }
    }
}
