package com.simats.enhancedcyclecareai.ui.mealfeedback

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

sealed interface MealFeedbackUiEvent {
    data object NavigateBack : MealFeedbackUiEvent
    data object SubmitFeedback : MealFeedbackUiEvent
    data class ShowToast(val message: String) : MealFeedbackUiEvent
}

data class MealFeedbackUiState(
    val subtitle: String = "NUTRITION • MEAL FEEDBACK",
    val title: String = "How was your meal?",
    val introText: String = "Your feedback helps future suggestions fit real life. There's no perfect way to follow a plan.",
    val mealTitle: String = "Vegetable moong khichdi",
    val mealSubtitle: String = "Dinner · Wed, 21 Oct · 7:30 PM",
    val replacementTag: String = "Replaced with spinach roti & curd",
    val followOptions: List<String> = listOf("Followed", "Skipped", "Replaced"),
    val selectedFollowOption: String = "Replaced",
    val feelingOptions: List<String> = listOf("Liked", "Disliked"),
    val selectedFeeling: String = "Liked",
    val replacementInputText: String = "Spinach roti & plain curd",
    val influenceOptions: List<String> = listOf("Taste", "Availability", "Cost", "Dietary Restriction", "Preparation Time", "Other"),
    val selectedInfluences: Set<String> = setOf("Availability", "Preparation Time"),
    val additionalComments: String = "Used what was already in the kitchen. A quicker option after class.",
    val infoBannerText: String = "Tailors preferences, personalize food ideas only. Feedback is not used to judge your health or label foods as good or bad.",
    val iconRes: Int = R.drawable.ic_clock
)

class MealFeedbackViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MealFeedbackUiState())
    val uiState: StateFlow<MealFeedbackUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MealFeedbackUiEvent>()
    val uiEvent: SharedFlow<MealFeedbackUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealFeedbackUiEvent.NavigateBack)
        }
    }

    fun onSelectFollowOption(option: String) {
        _uiState.value = _uiState.value.copy(selectedFollowOption = option)
    }

    fun onSelectFeeling(feeling: String) {
        _uiState.value = _uiState.value.copy(selectedFeeling = feeling)
    }

    fun onReplacementInputChanged(text: String) {
        _uiState.value = _uiState.value.copy(replacementInputText = text)
    }

    fun onToggleInfluence(influence: String) {
        val current = _uiState.value.selectedInfluences.toMutableSet()
        if (current.contains(influence)) {
            current.remove(influence)
        } else {
            current.add(influence)
        }
        _uiState.value = _uiState.value.copy(selectedInfluences = current)
    }

    fun onAdditionalCommentsChanged(text: String) {
        _uiState.value = _uiState.value.copy(additionalComments = text)
    }

    fun onSubmitFeedbackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(MealFeedbackUiEvent.ShowToast("Feedback submitted successfully! Thank you."))
            _uiEvent.emit(MealFeedbackUiEvent.SubmitFeedback)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
