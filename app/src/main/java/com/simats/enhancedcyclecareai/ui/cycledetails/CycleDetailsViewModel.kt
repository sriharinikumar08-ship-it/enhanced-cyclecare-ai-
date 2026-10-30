package com.simats.enhancedcyclecareai.ui.cycledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CycleDetailsUiEvent {
    data object NavigateBack : CycleDetailsUiEvent
    data class ShowToast(val message: String) : CycleDetailsUiEvent
}

data class HistoryItem(
    val dateRange: String,
    val periodInfo: String,
    val durationTag: String
)

data class CycleDetailsUiState(
    val subtitle: String = "INSIGHTS • CYCLE PATTERN OVERVIEW",
    val title: String = "Cycle analytics",
    val timeRangeOptions: List<String> = listOf("3 months", "6 months", "1 year"),
    val selectedTimeRange: String = "6 months",
    val description: String = "6 completed cycles ending May–Oct 2024. Only recorded cycles are included.",
    val averageCycleValue: String = "31.2",
    val averageCycleLabel: String = "Average cycle · P25",
    val variationValue: String = "10 days",
    val variationLabel: String = "Max-min variation",
    val variationSub: String = "Longest minus shortest",
    val cycleLengthTitle: String = "Cycle length",
    val cycleLengthSubtitle: String = "Days per completed cycle · month of next period",
    val recordedLengthsText: String = "Recorded lengths: 30, 28, 32, 28, 38, 28",
    val periodDurationTitle: String = "Period duration",
    val periodDurationSubtitle: String = "Bleeding days in each completed cycle",
    val medianDurationText: String = "Median duration is 5 days · Stable vs previous 6M.",
    val lastRecordedTag: String = "Last recorded: 5-Plot (Oct) · 5 days",
    val standoutTitle: String = "What stands out",
    val standoutBullet1: String = "Most lengths are close to 28 days · One 38-day cycle increases the range.",
    val standoutBullet2: String = "Period duration: 4–6 days · The latest recorded period lasted 5 days.",
    val historyItems: List<HistoryItem> = listOf(
        HistoryItem("7 Sep → 5 Oct", "Period duration: 5 days", "28 days"),
        HistoryItem("31 Jul → 7 Sep", "Period duration: 6 days", "38 days"),
        HistoryItem("2 Jul → 31 Jul", "Period duration: 5 days", "29 days")
    ),
    val footerDisclaimer: String = "Cycle analytics are descriptive, not a guarantee of future timing. Add/updating records to improve context."
)

class CycleDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CycleDetailsUiState())
    val uiState: StateFlow<CycleDetailsUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Progress")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CycleDetailsUiEvent>()
    val uiEvent: SharedFlow<CycleDetailsUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(CycleDetailsUiEvent.NavigateBack)
        }
    }

    fun onTimeRangeSelected(range: String) {
        _uiState.value = _uiState.value.copy(selectedTimeRange = range)
        viewModelScope.launch {
            _uiEvent.emit(CycleDetailsUiEvent.ShowToast("Filtered for $range"))
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
