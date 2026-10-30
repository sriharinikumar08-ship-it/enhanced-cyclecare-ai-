package com.simats.enhancedcyclecareai.ui.menstrualpattern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface GeneralMenstrualPatternUiEvent {
    data object NavigateBack : GeneralMenstrualPatternUiEvent
    data object ViewPersonalizedGuidance : GeneralMenstrualPatternUiEvent
    data class ShowToast(val message: String) : GeneralMenstrualPatternUiEvent
}

class GeneralMenstrualPatternViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GeneralMenstrualPatternUiState())
    val uiState: StateFlow<GeneralMenstrualPatternUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Insights")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<GeneralMenstrualPatternUiEvent>()
    val uiEvent: SharedFlow<GeneralMenstrualPatternUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(GeneralMenstrualPatternUiEvent.NavigateBack)
        }
    }

    fun onViewPersonalizedGuidanceClicked() {
        viewModelScope.launch {
            _uiEvent.emit(GeneralMenstrualPatternUiEvent.ShowToast("Opening Personalized Guidance..."))
            _uiEvent.emit(GeneralMenstrualPatternUiEvent.ViewPersonalizedGuidance)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}

data class GeneralMenstrualPatternUiState(
    val subtitle: String = "UNDERSTAND • CYCLE & SYMPTOMS",
    val title: String = "Your menstrual patterns",
    val introText: String = "Your history in plain language. These observations describe your entries, not what your body should do.",
    val cycleLengthMetric: String = "28–38",
    val cycleLengthSub: String = "Cycle length · days",
    val cycleLengthFooter: String = "6 completed cycles",
    val periodDurationMetric: String = "4–6",
    val periodDurationSub: String = "Period duration · days",
    val periodDurationFooter: String = "Latest period: 5 days",
    val cycleVariationBadge: String = "Moderate variation",
    val cycleVariationDescription: String = "Your longest recorded cycle was 38 days; your shortest was 28. Most entries sit closer to your 31-day typical length.",
    val contributingContext: String = "You recorded shorter sleep and moderate stress on same lower-energy days. Missing entries, routine changes, and many unrecorded factors may also matter.",
    val warningBannerText: String = "Seek timely care for severe pain, very heavy bleeding, fainting, or symptoms that feel unusual for you."
)
