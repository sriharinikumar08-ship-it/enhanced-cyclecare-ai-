package com.simats.enhancedcyclecareai.ui.timeline

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

sealed interface HealthTimelineUiEvent {
    data object NavigateBack : HealthTimelineUiEvent
    data class ShowToast(val message: String) : HealthTimelineUiEvent
    data class ExpandEntry(val title: String) : HealthTimelineUiEvent
}

data class TimelineItem(
    val id: String,
    val timestamp: String,
    val category: String,
    val title: String,
    val subtitle: String,
    val tag: String,
    val iconRes: Int
)

data class HealthTimelineUiState(
    val subtitle: String = "TRACK • CYCLE, SYMPTOM & NUTRITION",
    val title: String = "Your health timeline",
    val introText: String = "One place for your cycle, symptoms, lifestyle, insights, and meals. Expand an entry to see its original record.",
    val categories: List<String> = listOf("All", "Cycle", "Symptoms", "Lifestyle", "Patterns", "Nutrition", "Meals"),
    val selectedCategory: String = "All",
    val monthHeader: String = "October 2024",
    val timelineItems: List<TimelineItem> = listOf(
        TimelineItem(
            id = "1",
            timestamp = "22 Oct · 11:56 AM",
            category = "NUTRITION",
            title = "Today's balanced meal ideas",
            subtitle = "Vegetarian meals with lentils, grains, produce, and yogurt. Peanuts and eggs excluded.",
            tag = "3 suggestions",
            iconRes = R.drawable.ic_leaf
        ),
        TimelineItem(
            id = "2",
            timestamp = "22 Oct · 11:55 AM",
            category = "PATTERN UNLOCKED",
            title = "Pattern summary updated",
            subtitle = "Cycle: Moderate · Symptoms: Attention needed · PCOS related: Moderate · Lifestyle: Low",
            tag = "Plot collapse",
            iconRes = R.drawable.ic_sparkles
        ),
        TimelineItem(
            id = "3",
            timestamp = "22 Oct · 11:25 AM",
            category = "LIFESTYLE",
            title = "Your daily rhythm",
            subtitle = "6.5 h sleep · moderate stress · 35 minute walk · 1.5 L water",
            tag = "Daily check-in",
            iconRes = R.drawable.ic_lifestyle
        ),
        TimelineItem(
            id = "4",
            timestamp = "22 Oct · 11:00 AM",
            category = "MEAL LOG",
            title = "Breakfast followed",
            subtitle = "Banana overnight oats · approx. 380 kcal · Meal feedback: Liked",
            tag = "Followed",
            iconRes = R.drawable.ic_bowl
        ),
        TimelineItem(
            id = "5",
            timestamp = "21 Oct · 9:00 PM",
            category = "SYMPTOMS",
            title = "Mild fatigue recorded",
            subtitle = "Severity: 2/5 · about 2 hours · Lower energy after a shorter nights sleep.",
            tag = "Cycle day 17",
            iconRes = R.drawable.ic_symptoms
        ),
        TimelineItem(
            id = "6",
            timestamp = "20 Oct",
            category = "PERIOD",
            title = "Period ended",
            subtitle = "5 days recorded · moderate flow. Cramps and bloating ended during this period.",
            tag = "5-Plot",
            iconRes = R.drawable.ic_droplet
        ),
        TimelineItem(
            id = "7",
            timestamp = "15 Oct",
            category = "PERIOD",
            title = "A new cycle began",
            subtitle = "Previous cycle: 28 days. This is the start of your current cycle.",
            tag = "Cycle day 1",
            iconRes = R.drawable.ic_calendar
        )
    )
)

class HealthTimelineViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HealthTimelineUiState())
    val uiState: StateFlow<HealthTimelineUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Track")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HealthTimelineUiEvent>()
    val uiEvent: SharedFlow<HealthTimelineUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthTimelineUiEvent.NavigateBack)
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onExpandEntry(title: String) {
        viewModelScope.launch {
            _uiEvent.emit(HealthTimelineUiEvent.ShowToast("Viewing details for $title"))
            _uiEvent.emit(HealthTimelineUiEvent.ExpandEntry(title))
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
