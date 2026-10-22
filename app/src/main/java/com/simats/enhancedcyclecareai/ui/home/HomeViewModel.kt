package com.simats.enhancedcyclecareai.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RecentRecord(
    val title: String,
    val subtitle: String,
    val actionText: String,
    val isPinkIcon: Boolean
)

sealed interface HomeUiEvent {
    data class NavigateToFeature(val featureName: String) : HomeUiEvent
    data class NavigateToTab(val tabName: String) : HomeUiEvent
    data object NavigateBack : HomeUiEvent
}

class HomeViewModel : ViewModel() {

    private val _userName = MutableStateFlow("Ananya")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _cycleDay = MutableStateFlow(18)
    val cycleDay: StateFlow<Int> = _cycleDay.asStateFlow()

    private val _lastPeriodText = MutableStateFlow("5-9 Oct")
    val lastPeriodText: StateFlow<String> = _lastPeriodText.asStateFlow()

    private val _nextPeriodText = MutableStateFlow("6 Nov · in 15 days")
    val nextPeriodText: StateFlow<String> = _nextPeriodText.asStateFlow()

    private val _selectedTab = MutableStateFlow("Home")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _recentRecords = MutableStateFlow(
        listOf(
            RecentRecord(
                title = "Mild fatigue",
                subtitle = "Yesterday · 2/5 severity",
                actionText = "New",
                isPinkIcon = true
            ),
            RecentRecord(
                title = "6.5 hours of sleep",
                subtitle = "Last night · moderate stress",
                actionText = "Edit",
                isPinkIcon = false
            )
        )
    )
    val recentRecords: StateFlow<List<RecentRecord>> = _recentRecords.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent: SharedFlow<HomeUiEvent> = _uiEvent.asSharedFlow()

    fun onTabSelected(tabName: String) {
        _selectedTab.value = tabName
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToTab(tabName))
        }
    }

    fun onFeatureClicked(featureName: String) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToFeature(featureName))
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateBack)
        }
    }
}
