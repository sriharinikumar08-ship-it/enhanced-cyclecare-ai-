package com.simats.enhancedcyclecareai.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HealthProfileUiEvent {
    data object NavigateToNextStep : HealthProfileUiEvent
    data object NavigateBack : HealthProfileUiEvent
    data class ShowMessage(val message: String) : HealthProfileUiEvent
}

class HealthProfileViewModel : ViewModel() {

    private val _age = MutableStateFlow("24 years")
    val age: StateFlow<String> = _age.asStateFlow()

    private val _cycleLength = MutableStateFlow("32 days")
    val cycleLength: StateFlow<String> = _cycleLength.asStateFlow()

    private val _periodLength = MutableStateFlow("5 days")
    val periodLength: StateFlow<String> = _periodLength.asStateFlow()

    private val _mostRecentPeriod = MutableStateFlow("05 Oct 2026")
    val mostRecentPeriod: StateFlow<String> = _mostRecentPeriod.asStateFlow()

    private val _dietaryPreference = MutableStateFlow("Vegetarian")
    val dietaryPreference: StateFlow<String> = _dietaryPreference.asStateFlow()

    private val _foodAllergies = MutableStateFlow("Peanuts")
    val foodAllergies: StateFlow<String> = _foodAllergies.asStateFlow()

    private val _foodRestrictions = MutableStateFlow("No eggs")
    val foodRestrictions: StateFlow<String> = _foodRestrictions.asStateFlow()

    private val _preferredMealStyle = MutableStateFlow("Indian · Home-style · Quick meals")
    val preferredMealStyle: StateFlow<String> = _preferredMealStyle.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HealthProfileUiEvent>()
    val uiEvent: SharedFlow<HealthProfileUiEvent> = _uiEvent.asSharedFlow()

    fun onAgeChange(value: String) {
        _age.value = value
    }

    fun onCycleLengthChange(value: String) {
        _cycleLength.value = value
    }

    fun onPeriodLengthChange(value: String) {
        _periodLength.value = value
    }

    fun onMostRecentPeriodChange(value: String) {
        _mostRecentPeriod.value = value
    }

    fun onDietaryPreferenceChange(value: String) {
        _dietaryPreference.value = value
    }

    fun onFoodAllergiesChange(value: String) {
        _foodAllergies.value = value
    }

    fun onFoodRestrictionsChange(value: String) {
        _foodRestrictions.value = value
    }

    fun onPreferredMealStyleChange(value: String) {
        _preferredMealStyle.value = value
    }

    fun onSaveAndContinueClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthProfileUiEvent.NavigateToNextStep)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthProfileUiEvent.NavigateBack)
        }
    }
}
