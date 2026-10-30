package com.simats.enhancedcyclecareai.ui.personalizedguidance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PersonalizedGuidanceUiEvent {
    data object NavigateBack : PersonalizedGuidanceUiEvent
    data object NavigateToCycleTracker : PersonalizedGuidanceUiEvent
    data object NavigateToSymptomTracker : PersonalizedGuidanceUiEvent
    data object NavigateToLifestyleTracker : PersonalizedGuidanceUiEvent
    data object NavigateToNutrition : PersonalizedGuidanceUiEvent
    data class ShowToast(val message: String) : PersonalizedGuidanceUiEvent
}

class PersonalizedGuidanceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalizedGuidanceUiState())
    val uiState: StateFlow<PersonalizedGuidanceUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PersonalizedGuidanceUiEvent>()
    val uiEvent: SharedFlow<PersonalizedGuidanceUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedGuidanceUiEvent.NavigateBack)
        }
    }

    fun onOpenCycleTrackerClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedGuidanceUiEvent.ShowToast("Opening cycle tracker..."))
            _uiEvent.emit(PersonalizedGuidanceUiEvent.NavigateToCycleTracker)
        }
    }

    fun onRecordSymptomClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedGuidanceUiEvent.ShowToast("Opening symptom tracker..."))
            _uiEvent.emit(PersonalizedGuidanceUiEvent.NavigateToSymptomTracker)
        }
    }

    fun onCheckLifestyleClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedGuidanceUiEvent.ShowToast("Opening lifestyle tracker..."))
            _uiEvent.emit(PersonalizedGuidanceUiEvent.NavigateToLifestyleTracker)
        }
    }

    fun onExploreNutritionClicked() {
        viewModelScope.launch {
            _uiEvent.emit(PersonalizedGuidanceUiEvent.ShowToast("Opening nutrition preferences..."))
            _uiEvent.emit(PersonalizedGuidanceUiEvent.NavigateToNutrition)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}

data class PersonalizedGuidanceUiState(
    val subtitle: String = "AI INSIGHTS • YOUR NEXT STEPS",
    val title: String = "Personalized guidance",
    val badgeText: String = "CycleCare AI • Based on your entries",
    val mainHeading: String = "What Can You Do Now?",
    val mainSubheading: String = "Start small, Ananya. Here are a few options that fit what you've recorded.",
    val companionTitle: String = "Your awareness companion",
    val companionQuote: String = "\"Your entries show some cycle variation and recurring cramps. Let's focus on consistent tracking and everyday habits, without jumping to a diagnosis.\"",
    val warningText: String = "Consider professional advice if pain persists, disrupts your routine, or your cycle changes worry you. AI guidance does not replace care.",
    val footerNote: String = "You choose what works for you. Suggestions are optional and can be adjusted as your records change."
)
