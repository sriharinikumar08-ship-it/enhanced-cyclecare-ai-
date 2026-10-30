package com.simats.enhancedcyclecareai.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProgressDashboardUiEvent {
    data object NavigateBack : ProgressDashboardUiEvent
    data object ViewHealthReport : ProgressDashboardUiEvent
    data class ShowToast(val message: String) : ProgressDashboardUiEvent
}

data class ChecklistItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean
)

data class ProgressDashboardUiState(
    val subtitle: String = "PROGRESS • WILL STEPS COUNT?",
    val title: String = "Your progress",
    val timeRangeOptions: List<String> = listOf("Weekly", "Monthly"),
    val selectedTimeRange: String = "Weekly",
    val dateRangeText: String = "16–22 Oct 2024 · Your activity, not a score of your worth.",
    val checkinPercentage: String = "85%",
    val checkinTitle: String = "You showed up for you.",
    val checkinSubtitle: String = "6 of 7 daily check-ins completed this week.",
    val symptomEntriesValue: String = "5",
    val symptomEntriesLabel: String = "Symptom entries",
    val symptomEntriesSub: String = "7 this week · 12 this month",
    val mealSlotsValue: String = "24 / 28",
    val mealSlotsLabel: String = "Meal slots logged",
    val mealSlotsSub: String = "86% log completion",
    val nutritionAdherenceTitle: String = "Nutrition activity",
    val nutritionAdherenceLabel: String = "Meal adherence",
    val nutritionAdherencePercent: String = "86%",
    val nutritionAdherenceProgress: Float = 0.86f,
    val nutritionAdherenceText: String = "18 follows · 4 replaced · 2 skipped. Four planned meal slots have no entry.",
    val nutritionAdherenceFooter: String = "Logging adherence is not dietary quality or treatment progress.",
    val checklistTitle: String = "Your next-step checklist",
    val checklistProgressText: String = "3 of 3 suggested actions completed",
    val checklists: List<ChecklistItem> = listOf(
        ChecklistItem("1", "Update cycle dates", "Period record tentative on 22 Oct", true),
        ChecklistItem("2", "Save dietary preferences", "Vegetarian · peanuts and eggs excluded", true),
        ChecklistItem("3", "Logged a balanced breakfast", "Banana overnight oats · honey", true),
        ChecklistItem("4", "Add tonight's sleep entry", "Continue your daily routine", false),
        ChecklistItem("5", "Prepare questions for a care visit", "Optional · If symptoms concern you", false)
    ),
    val patternTitle: String = "Recent pattern results",
    val cyclePatternBadge: String = "Moderate",
    val symptomPatternBadge: String = "Attention Needed",
    val patternSubtitle: String = "Updated 22 Oct · PCOS-related Moderate · Lifestyle Low. Categories are for awareness only.",
    val quoteText: String = "Missed a day? Begin again whenever you’re ready. Older entries can assist context, they don’t guarantee an answer."
)

class ProgressDashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressDashboardUiState())
    val uiState: StateFlow<ProgressDashboardUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Progress")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProgressDashboardUiEvent>()
    val uiEvent: SharedFlow<ProgressDashboardUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(ProgressDashboardUiEvent.NavigateBack)
        }
    }

    fun onTimeRangeSelected(range: String) {
        _uiState.value = _uiState.value.copy(selectedTimeRange = range)
    }

    fun onToggleChecklist(id: String) {
        val updated = _uiState.value.checklists.map { item ->
            if (item.id == id) item.copy(isCompleted = !item.isCompleted) else item
        }
        _uiState.value = _uiState.value.copy(checklists = updated)
    }

    fun onViewHealthReportClicked() {
        viewModelScope.launch {
            _uiEvent.emit(ProgressDashboardUiEvent.ShowToast("Generating health report..."))
            _uiEvent.emit(ProgressDashboardUiEvent.ViewHealthReport)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
