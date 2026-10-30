package com.simats.enhancedcyclecareai.ui.dietary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DietaryPreferencesUiEvent {
    data object NavigateBack : DietaryPreferencesUiEvent
    data object NavigateToHome : DietaryPreferencesUiEvent
    data class ShowToast(val message: String) : DietaryPreferencesUiEvent
}

class DietaryPreferencesViewModel : ViewModel() {

    private val _eatingStyle = MutableStateFlow("Vegetarian")
    val eatingStyle: StateFlow<String> = _eatingStyle.asStateFlow()

    private val _allergies = MutableStateFlow(setOf("Peanuts"))
    val allergies: StateFlow<Set<String>> = _allergies.asStateFlow()

    private val _dislikedFoods = MutableStateFlow("Mushrooms")
    val dislikedFoods: StateFlow<String> = _dislikedFoods.asStateFlow()

    private val _restrictions = MutableStateFlow("No eggs")
    val restrictions: StateFlow<String> = _restrictions.asStateFlow()

    private val _preferredCuisine = MutableStateFlow("Indian · Home-style")
    val preferredCuisine: StateFlow<String> = _preferredCuisine.asStateFlow()

    private val _selectedPreferences = MutableStateFlow(setOf("Quick meals", "Budget-friendly", "Mild spice"))
    val selectedPreferences: StateFlow<Set<String>> = _selectedPreferences.asStateFlow()

    private val _prepTime = MutableStateFlow("15–30 minutes")
    val prepTime: StateFlow<String> = _prepTime.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<DietaryPreferencesUiEvent>()
    val uiEvent: SharedFlow<DietaryPreferencesUiEvent> = _uiEvent.asSharedFlow()

    fun onEatingStyleChange(value: String) {
        _eatingStyle.value = value
    }

    fun toggleAllergy(allergy: String) {
        val current = _allergies.value.toMutableSet()
        if (current.contains(allergy)) {
            current.remove(allergy)
        } else {
            current.add(allergy)
        }
        _allergies.value = current
    }

    fun onDislikedFoodsChange(value: String) {
        _dislikedFoods.value = value
    }

    fun onRestrictionsChange(value: String) {
        _restrictions.value = value
    }

    fun onPreferredCuisineChange(value: String) {
        _preferredCuisine.value = value
    }

    fun togglePreference(pref: String) {
        val current = _selectedPreferences.value.toMutableSet()
        if (current.contains(pref)) {
            current.remove(pref)
        } else {
            current.add(pref)
        }
        _selectedPreferences.value = current
    }

    fun onPrepTimeChange(value: String) {
        _prepTime.value = value
    }

    fun onSavePreferences() {
        viewModelScope.launch {
            _uiEvent.emit(DietaryPreferencesUiEvent.ShowToast("Dietary preferences saved!"))
            _uiEvent.emit(DietaryPreferencesUiEvent.NavigateToHome)
        }
    }

    fun onFinishLater() {
        viewModelScope.launch {
            _uiEvent.emit(DietaryPreferencesUiEvent.NavigateToHome)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(DietaryPreferencesUiEvent.NavigateBack)
        }
    }
}
