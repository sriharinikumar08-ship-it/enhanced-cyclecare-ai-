package com.simats.enhancedcyclecareai.ui.symptomnutrition

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

sealed interface SymptomNutritionAnalysisUiEvent {
    data object NavigateBack : SymptomNutritionAnalysisUiEvent
    data class ShowToast(val message: String) : SymptomNutritionAnalysisUiEvent
}

data class WeeklyActivityItem(
    val weekRange: String,
    val entryCount: String,
    val severityValue: Float, // out of 5
    val severityText: String,
    val mealLoggedValue: Int, // out of 28
    val mealLoggedText: String
)

data class ContextItem(
    val title: String,
    val description: String,
    val iconRes: Int
)

data class SymptomNutritionAnalysisUiState(
    val subtitle: String = "INSIGHTS • SCRIPT & MEAL PATTERN ANALYSIS",
    val title: String = "Symptom & nutrition analytics",
    val dateRangeText: String = "01–21 OCT 2024",
    val symptomCategories: List<String> = listOf("All symptoms", "Cramps", "Fatigue", "Bloating"),
    val selectedSymptomCategory: String = "All symptoms",
    val warningText: String = "These are observations, not proof of food causing symptom changes. Timing overlaps do not establish cause and effect.",
    val activityTitle: String = "Symptoms and meal activity",
    val activitySubtitle: String = "Weekly averages from recorded entries only",
    val severityBadge: String = "Symptom severity: 0–5",
    val mealBadge: String = "Meal slots logged: 0–28",
    val weeklyActivities: List<WeeklyActivityItem> = listOf(
        WeeklyActivityItem("1–7 Oct", "3 symptom entries", 3.2f, "3.2 / 5", 18, "18 / 28"),
        WeeklyActivityItem("8–14 Oct", "5 symptom entries", 2.8f, "2.8 / 5", 20, "20 / 28"),
        WeeklyActivityItem("15–21 Oct", "4 symptom entries", 2.1f, "2.1 / 5", 24, "24 / 28")
    ),
    val activityFooter: String = "Stack bar colors: 2/5 average symptom severity (dark purple bar), 0/28 meal activity measure logging (dark teal bar).",
    val suggestionTitle: String = "What the entries suggest",
    val suggestionBody: String = "Averages recorded symptom severity was lower in later weeks while meal logging increased. Three weeks of incomplete, self-reported entries are too limited to explain why.",
    val suggestionTag: String = "Observation pattern only",
    val contextTitle: String = "Other context matters",
    val contextItems: List<ContextItem> = listOf(
        ContextItem("Sleep and stress vary too", "Your latest sleep entry is 6.5 h with moderate stress.", R.drawable.ic_clock),
        ContextItem("Cycle timing can affect symptoms", "Your period was first due within the last two weeks.", R.drawable.ic_calendar),
        ContextItem("Not all days are recorded", "12 symptom entries across the displayed period.", R.drawable.ic_clipboard_info)
    ),
    val footerDisclaimer: String = "Keep recording how you feel and what you eat without assuming a future responsible for a change. Discuss ongoing symptoms with a professional."
)

class SymptomNutritionAnalysisViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SymptomNutritionAnalysisUiState())
    val uiState: StateFlow<SymptomNutritionAnalysisUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SymptomNutritionAnalysisUiEvent>()
    val uiEvent: SharedFlow<SymptomNutritionAnalysisUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(SymptomNutritionAnalysisUiEvent.NavigateBack)
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedSymptomCategory = category)
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
