package com.simats.enhancedcyclecareai.ui.foodalternatives

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

sealed interface FoodAlternativesUiEvent {
    data object NavigateBack : FoodAlternativesUiEvent
    data object SavePreference : FoodAlternativesUiEvent
    data class ShowToast(val message: String) : FoodAlternativesUiEvent
    data class SelectAlternative(val title: String) : FoodAlternativesUiEvent
}

data class FoodAlternativeItem(
    val id: String,
    val insteadOf: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val tags: List<String>,
    val isSelected: Boolean,
    val iconRes: Int
)

data class FoodAlternativesUiState(
    val subtitle: String = "NUTRITION • FLEXIBLE FOCUS",
    val title: String = "Food alternatives",
    val introText: String = "Keep the nutritional role, change the ingredient. Choose the swap that suits your kitchen today.",
    val categories: List<String> = listOf("All foods", "Protein", "Grains", "Dairy"),
    val selectedCategory: String = "All foods",
    val alternatives: List<FoodAlternativeItem> = listOf(
        FoodAlternativeItem(
            id = "1",
            insteadOf = "INSTEAD OF PLAIN YOGURT",
            title = "Fortified soy yogurt",
            subtitle = "Protein + calcium-containing alternative",
            description = "Choose an unsweetened, calcium-fortified option. Contains soy; review the product label.",
            tags = listOf("Vegetarian", "No egg ingredients", "Check allergy label"),
            isSelected = true,
            iconRes = R.drawable.ic_check
        ),
        FoodAlternativeItem(
            id = "2",
            insteadOf = "INSTEAD OF LENTILS",
            title = "Chickpeas",
            subtitle = "Plant protein + fiber",
            description = "Use cooked chickpeas in your rice bowl. A different texture with a similar food-group role.",
            tags = listOf("Vegetarian", "No egg ingredients", "Check allergy label"),
            isSelected = false,
            iconRes = R.drawable.ic_refresh
        ),
        FoodAlternativeItem(
            id = "3",
            insteadOf = "INSTEAD OF BROWN RICE",
            title = "Whole-wheat roti",
            subtitle = "Grain + energy",
            description = "Pair two small rotis with the same vegetables and lentils. Contains wheat and gluten.",
            tags = listOf("Vegetarian", "No egg ingredients", "Check allergy label"),
            isSelected = false,
            iconRes = R.drawable.ic_refresh
        )
    ),
    val warningText: String = "Peanut allergy is active. Peanuts and peanut-based swaps are excluded. Other ingredients and stored equipment still need label checks.",
    val rememberChoiceTitle: String = "Remember this choice",
    val rememberChoiceSubtitle: String = "Use fortified soy yogurt as my preferred yogurt alternative.",
    val isRememberChoiceChecked: Boolean = true
)

class FoodAlternativesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FoodAlternativesUiState())
    val uiState: StateFlow<FoodAlternativesUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Nutrition")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<FoodAlternativesUiEvent>()
    val uiEvent: SharedFlow<FoodAlternativesUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(FoodAlternativesUiEvent.NavigateBack)
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onSelectAlternative(id: String) {
        val currentItems = _uiState.value.alternatives
        val updated = currentItems.map { item ->
            if (item.id == id) {
                item.copy(isSelected = true)
            } else {
                item.copy(isSelected = false)
            }
        }
        _uiState.value = _uiState.value.copy(alternatives = updated)
        val selectedItem = updated.find { it.id == id }
        if (selectedItem != null) {
            viewModelScope.launch {
                _uiEvent.emit(FoodAlternativesUiEvent.ShowToast("Selected: ${selectedItem.title}"))
                _uiEvent.emit(FoodAlternativesUiEvent.SelectAlternative(selectedItem.title))
            }
        }
    }

    fun onToggleRememberChoice(checked: Boolean) {
        _uiState.value = _uiState.value.copy(isRememberChoiceChecked = checked)
    }

    fun onSavePreferenceClicked() {
        viewModelScope.launch {
            _uiEvent.emit(FoodAlternativesUiEvent.ShowToast("Preferences saved successfully!"))
            _uiEvent.emit(FoodAlternativesUiEvent.SavePreference)
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
