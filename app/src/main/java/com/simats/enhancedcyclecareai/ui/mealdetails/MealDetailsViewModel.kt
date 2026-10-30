package com.simats.enhancedcyclecareai.ui.mealdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MealDetailsUiEvent {
    data object NavigateBack : MealDetailsUiEvent
    data object AddToMealLog : MealDetailsUiEvent
    data object ReplaceMeal : MealDetailsUiEvent
    data class ShowToast(val message: String) : MealDetailsUiEvent
}

data class PreparationStep(
    val stepNumber: Int,
    val title: String,
    val description: String
)

data class MealDetailsUiState(
    val subtitle: String = "NUTRITION • A MEAL",
    val title: String = "Meal details",
    val mealTitle: String = "Lentil & spinach rice bowl",
    val description: String = "A warm, home-style bowl with plant protein, vegetables, and a bright lemon finish.",
    val tags: List<String> = listOf("Vegetarian", "No egg ingredients", "Easy - 20 min"),
    val kcal: String = "540",
    val protein: String = "22 g",
    val fiber: String = "12 g",
    val servingNote: String = "Approximate values per standard serving, vary by ingredients and portions. Includes side accompaniment and standard olive oil.",
    val ingredientsSubtitle: String = "Serves 1 - adjust to appetite",
    val ingredients: List<String> = listOf(
        "¾ cup cooked lentils - about 150 g",
        "1 cup cooked brown rice - about 200 g",
        "1 cup spinach + ¼ cup grated carrot",
        "1 tsp olive oil, cumin, lemon, mild spices",
        "½ cup plain yogurt, served alongside"
    ),
    val preparationSteps: List<PreparationStep> = listOf(
        PreparationStep(1, "Cook the base", "Warm lentils and rice separately until tender, or use prepared portions."),
        PreparationStep(2, "Add color", "Warm oil and cumin. Add carrot and spinach; stir until just softened."),
        PreparationStep(3, "Bring it together", "Add lentils, warm brown rice, and finish with lemon. Serve yogurt alongside.")
    ),
    val substitutionTitle: String = "Easy substitutions",
    val substitutionText: String = "Try chickpeas instead of lentils. Use fortified soy yogurt instead of plain yogurt if you prefer; this introduces soy.",
    val substitutionFooter: String = "Compliant with as-shown. No peanuts are included; check ingredient labels and cross-contact information.",
    val footerDisclaimer: String = "For everyday nourishment. This meal is not intended to treat any condition."
)

class MealDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MealDetailsUiState())
    val uiState: StateFlow<MealDetailsUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MealDetailsUiEvent>()
    val uiEvent: SharedFlow<MealDetailsUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealDetailsUiEvent.NavigateBack)
        }
    }

    fun onAddToMealLogClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealDetailsUiEvent.ShowToast("Added meal to log successfully!"))
            _uiEvent.emit(MealDetailsUiEvent.AddToMealLog)
        }
    }

    fun onReplaceMealClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealDetailsUiEvent.ShowToast("Opening meal alternatives..."))
            _uiEvent.emit(MealDetailsUiEvent.ReplaceMeal)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
