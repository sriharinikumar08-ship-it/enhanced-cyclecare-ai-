package com.simats.enhancedcyclecareai.ui.meallog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MealAndNutritionLogUiEvent {
    data object NavigateBack : MealAndNutritionLogUiEvent
    data object LogMeal : MealAndNutritionLogUiEvent
    data class ShowToast(val message: String) : MealAndNutritionLogUiEvent
}

data class DayItem(
    val dayLetter: String,
    val dayNumber: Int,
    val isSelected: Boolean
)

data class LoggedMealItem(
    val categoryTime: String,
    val title: String,
    val statusTag: String, // Followed, Skipped, Replaced
    val subtitle: String? = null
)

data class LogSection(
    val dateHeader: String,
    val meals: List<LoggedMealItem>,
    val footerNote: String? = null
)

data class MealAndNutritionLogUiState(
    val subtitle: String = "NUTRITION • YOUR MEAL HISTORY",
    val title: String = "Meal & nutrition log",
    val dateRangeHeader: String = "16-22 October",
    val days: List<DayItem> = listOf(
        DayItem("F", 16, false),
        DayItem("S", 17, false),
        DayItem("S", 18, false),
        DayItem("M", 19, false),
        DayItem("T", 20, false),
        DayItem("W", 21, false),
        DayItem("T", 22, true)
    ),
    val loggedSlotsValue: String = "24 / 28",
    val loggedSlotsLabel: String = "Meal slots logged",
    val loggedSlotsSub: String = "This week - 86%",
    val followedValue: String = "18",
    val followedLabel: String = "Followed as planned",
    val followedSub: String = "4 replaced · 2 skipped",
    val categories: List<String> = listOf("All", "Breakfast", "Lunch", "Snack", "Dinner"),
    val selectedCategory: String = "All",
    val sections: List<LogSection> = listOf(
        LogSection(
            dateHeader = "Today · 22 Oct",
            meals = listOf(
                LoggedMealItem(
                    categoryTime = "Breakfast · 8:00 AM · approx. 380 kcal",
                    title = "Banana overnight oats",
                    statusTag = "Followed"
                )
            ),
            footerNote = "Lunch, snack, and dinner haven't been logged yet."
        ),
        LogSection(
            dateHeader = "Yesterday · 21 Oct",
            meals = listOf(
                LoggedMealItem("Breakfast · 8:00 AM", "Banana overnight oats", "Followed"),
                LoggedMealItem("Lunch · 1:00 PM", "Lentil & spinach rice bowl", "Followed"),
                LoggedMealItem("Snack · 4:30 PM", "Guava & roasted chickpeas", "Skipped"),
                LoggedMealItem("Dinner · 7:30 PM", "Spinach roti & plain curd", "Replaced", "Instead of vegetable moong khichdi")
            ),
            footerNote = null
        )
    ),
    val nutritionActivityTitle: String = "Nutrition activity",
    val nutritionActivityText: String = "Protein-containing foods appeared in 19 of 24 logged meal slots this week. Skipped and replaced meals stay visible for an honest record.",
    val footerDisclaimer: String = "Food amounts are self-reported. Log completion is not a measure of dietary quality or health."
)

class MealAndNutritionLogViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MealAndNutritionLogUiState())
    val uiState: StateFlow<MealAndNutritionLogUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MealAndNutritionLogUiEvent>()
    val uiEvent: SharedFlow<MealAndNutritionLogUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealAndNutritionLogUiEvent.NavigateBack)
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onDaySelected(dayNumber: Int) {
        val updatedDays = _uiState.value.days.map { it.copy(isSelected = it.dayNumber == dayNumber) }
        _uiState.value = _uiState.value.copy(days = updatedDays)
    }

    fun onLogMealClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealAndNutritionLogUiEvent.ShowToast("Opening meal logger..."))
            _uiEvent.emit(MealAndNutritionLogUiEvent.LogMeal)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
