package com.simats.enhancedcyclecareai.ui.personalizedmeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.enhancedcyclecareai.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PersonalizedMealUiEvent {
    data object NavigateBack : PersonalizedMealUiEvent
    data object OpenMealPlanner : PersonalizedMealUiEvent
    data class ViewMealDetails(val mealTitle: String) : PersonalizedMealUiEvent
    data class ReplaceMeal(val mealTitle: String) : PersonalizedMealUiEvent
    data class ShowToast(val message: String) : PersonalizedMealUiEvent
}

class PersonalizedMealViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalizedMealUiState())
    val uiState: StateFlow<PersonalizedMealUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PersonalizedMealUiEvent>()
    val uiEvent: SharedFlow<PersonalizedMealUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedMealUiEvent.NavigateBack)
        }
    }

    fun onOpenMealPlannerClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedMealUiEvent.ShowToast("Opening My Meal Planner..."))
            _uiEvent.emit(PersonalizedMealUiEvent.OpenMealPlanner)
        }
    }

    fun onViewMealDetails(mealTitle: String) {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedMealUiEvent.ShowToast("Viewing details for $mealTitle"))
            _uiEvent.emit(PersonalizedMealUiEvent.ViewMealDetails(mealTitle))
        }
    }

    fun onReplaceMeal(mealTitle: String) {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedMealUiEvent.ShowToast("Replacing $mealTitle..."))
            _uiEvent.emit(PersonalizedMealUiEvent.ReplaceMeal(mealTitle))
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}

data class MealItemData(
    val category: String, // BREAKFAST, LUNCH, SNACK, DINNER
    val title: String,
    val ingredients: String,
    val nutritionHighlight: String,
    val tags: List<String>,
    val iconRes: Int
)

data class PersonalizedMealUiState(
    val subtitle: String = "NUTRITION • RECOMMENDATIONS",
    val title: String = "Meals picked for you",
    val introText: String = "Four everyday ideas that fit your vegetarian, home-style preferences. Portions and estimates are flexible.",
    val topTags: List<String> = listOf("Vegetarian", "Peanuts excluded", "Quick & budget-friendly"),
    val meals: List<MealItemData> = listOf(
        MealItemData(
            category = "BREAKFAST",
            title = "Banana overnight oats",
            ingredients = "Oats, plant yogurt, banana, chia seeds",
            nutritionHighlight = "Protein + calcium · about 380 kcal",
            tags = listOf("Vegetarian · no eggs", "Easy", "15 min + overnight chill"),
            iconRes = R.drawable.ic_bowl
        ),
        MealItemData(
            category = "LUNCH",
            title = "Lentil & spinach rice bowl",
            ingredients = "Lentils, brown rice, spinach, carrot, lemon",
            nutritionHighlight = "Protein + fiber · about 540 kcal",
            tags = listOf("Vegetarian · no eggs", "Easy", "25-minutes"),
            iconRes = R.drawable.ic_bowl
        ),
        MealItemData(
            category = "SNACK",
            title = "Guava & roasted chickpeas",
            ingredients = "Fresh guava, roasted chickpeas, mild spices",
            nutritionHighlight = "Fiber + vitamin C · about 230 kcal",
            tags = listOf("Vegetarian · no eggs", "Easy", "5 minutes"),
            iconRes = R.drawable.ic_sparkles
        ),
        MealItemData(
            category = "DINNER",
            title = "Vegetable moong khichdi",
            ingredients = "Moong dal, rice, peas, carrot, cumin, yogurt",
            nutritionHighlight = "Protein + variety · about 600 kcal",
            tags = listOf("Vegetarian · no eggs", "Easy", "30 minutes"),
            iconRes = R.drawable.ic_bowl
        )
    ),
    val warningText: String = "No peanut ingredients are suggested. Packaged ingredients may have cross-contact risk; check every label. Nutrition values are approximate.",
    val openPlannerButtonText: String = "Open My Meal Planner"
)
