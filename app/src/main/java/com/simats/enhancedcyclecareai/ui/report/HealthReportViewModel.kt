package com.simats.enhancedcyclecareai.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HealthReportUiEvent {
    data object NavigateBack : HealthReportUiEvent
    data object ExportPdf : HealthReportUiEvent
    data object ShareReport : HealthReportUiEvent
    data object PrintSave : HealthReportUiEvent
    data class ShowToast(val message: String) : HealthReportUiEvent
}

data class HealthReportUiState(
    val brandTitle: String = "CycleCare AI",
    val reportHeader: String = "PCOS-AWARE • HEALTH REPORT",
    val patientName: String = "Ananya Sharma",
    val patientAge: String = "24 years",
    val preparedDate: String = "22 October 2024",
    val dataSummary: String = "1–22 Oct 2024",
    val cycleContext: String = "6 completed cycles · Apr–Oct 2024",
    val encryptionTag: String = "Self-reported data · confidentially encrypted",
    val cycleHistoryItems: List<Pair<String, String>> = listOf(
        Pair("Last completed", "9 Oct · 5 days."),
        Pair("Current cycle", "Day 18 · approx length 31 days."),
        Pair("Recent cycle lengths", "30, 28, 32, 28, 38, 28 days."),
        Pair("Average range", "31.2 days (31.29–38 days)."),
        Pair("Period duration", "4–6 days across completed cycles."),
        Pair("Next estimate", "6 Nov · estimates may change.")
    ),
    val symptomLifestyleItems: List<Pair<String, String>> = listOf(
        Pair("Symptoms", "12 entries · cramps, bloating, fatigue."),
        Pair("Reporting pattern", "Cramps in 4 of 6 periods, luteal peak 9/10 on 8 Oct."),
        Pair("Recent fatigue", "21 Oct · 2010 · about 2 hours."),
        Pair("Lifestyle coverage", "18 daily entries this month."),
        Pair("Latest routine", "6.5 h sleep · moderate stress · 35 min walk · 1.5 L water.")
    ),
    val symptomLifestyleFooter: String = "These are recorded observations, not clinical measurements.",
    val riskPatterns: List<Triple<String, String, ColorType>> = listOf(
        Triple("Cycle Pattern", "Moderate", ColorType.PURPLE),
        Triple("Symptom Pattern", "Attention Needed", ColorType.ORANGE),
        Triple("PCOS-related awareness", "Moderate", ColorType.PURPLE),
        Triple("Lifestyle Pattern", "Low", ColorType.GREEN)
    ),
    val riskPatternFooter: String = "PCOS-relatedness quotient is technical and illustrative, not a validated diagnostic score. Categories do not confirm or exclude a condition.",
    val nutritionItems: List<Pair<String, String>> = listOf(
        Pair("Preferences", "Vegetarian · no eggs · peanut allergy · Indian home-style."),
        Pair("Guidance", "Balanced meals with protein, grains, produce, calcium-containing entries, and hydration."),
        Pair("Match track", "24 of 28 slots logged · 18 followed, 4 replaced, 2 skipped."),
        Pair("Today's breakfast", "Banana overnight oats · followed.")
    ),
    val nutritionFooter: String = "This guidance is not intended to treat or cure disease. Routine variation is normal with ingredient and portions.",
    val progressItems: List<Pair<String, String>> = listOf(
        Pair("Daily check-ins", "6 of 7 days · 85%"),
        Pair("Completed actions", "3 of 5 suggested actions")
    ),
    val progressRecommendation: String = "Continue logging cycle dates and symptom severity. Discuss persistent, therapies, or concerning symptoms with a qualified healthcare professional. Incomplete entries, self-reporting, and technical cloud comparisons/interpretation food-symptom comparisons do not establish causation.",
    val privacyNotice: String = "This report includes personal health information · review it and choose who to share it with.",
    val finalDisclaimer: String = "For health awareness and monitoring, not a medical diagnosis."
)

enum class ColorType {
    PURPLE, ORANGE, GREEN
}

class HealthReportViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HealthReportUiState())
    val uiState: StateFlow<HealthReportUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Progress")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HealthReportUiEvent>()
    val uiEvent: SharedFlow<HealthReportUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthReportUiEvent.NavigateBack)
        }
    }

    fun onExportPdfClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthReportUiEvent.ShowToast("Exporting health report PDF..."))
            _uiEvent.emit(HealthReportUiEvent.ExportPdf)
        }
    }

    fun onShareReportClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthReportUiEvent.ShowToast("Preparing report for sharing..."))
            _uiEvent.emit(HealthReportUiEvent.ShareReport)
        }
    }

    fun onPrintSaveClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HealthReportUiEvent.ShowToast("Opening print / save dialog..."))
            _uiEvent.emit(HealthReportUiEvent.PrintSave)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
