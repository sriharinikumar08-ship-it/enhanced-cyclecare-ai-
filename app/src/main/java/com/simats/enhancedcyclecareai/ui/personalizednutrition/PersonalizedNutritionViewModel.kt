package com.simats.enhancedcyclecareai.ui.personalizednutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PersonalizedNutritionUiEvent {
    data object NavigateBack : PersonalizedNutritionUiEvent
    data object SeeRecommendedMeals : PersonalizedNutritionUiEvent
    data object BrowseAlternatives : PersonalizedNutritionUiEvent
    data class ShowToast(val message: String) : PersonalizedNutritionUiEvent
}

class PersonalizedNutritionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalizedNutritionUiState())
    val uiState: StateFlow<PersonalizedNutritionUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PersonalizedNutritionUiEvent>()
    val uiEvent: SharedFlow<PersonalizedNutritionUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedNutritionUiEvent.NavigateBack)
        }
    }

    fun onSeeRecommendedMealsClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedNutritionUiEvent.ShowToast("Loading recommended meals..."))
            _uiEvent.emit(PersonalizedNutritionUiEvent.SeeRecommendedMeals)
        }
    }

    fun onBrowseAlternativesClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedNutritionUiEvent.ShowToast("Browsing food alternatives..."))
            _uiEvent.emit(PersonalizedNutritionUiEvent.BrowseAlternatives)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}

data class PersonalizedNutritionUiState(
    val subtitle: String = "NUTRITION • PERSONALIZED FOR YOU",
    val title: String = "Nutrition for your everyday",
    val introText: String = "Flexible food ideas for balanced eating, shaped by your preferences and recorded routine.",
    val heroTitle: String = "Nourish, don't restrict.",
    val heroBody: String = "Pair lentils or beans with grains, vegetables, and a little healthy fat. Keep meals satisfying and easy to repeat.",
    val filterTags: List<String> = listOf("Vegetarian", "No egg ingredients", "Peanuts excluded"),
    val warningText: String = "Foods do not treat or cure PCOS or other conditions. Nutrition ideas support everyday eating, not a medical diet. Check labels for peanuts and cross-contact."
)
