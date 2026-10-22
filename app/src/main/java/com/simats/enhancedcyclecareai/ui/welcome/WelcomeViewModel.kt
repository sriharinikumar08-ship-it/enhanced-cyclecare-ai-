package com.simats.enhancedcyclecareai.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface WelcomeEvent {
    data object NavigateToGetStarted : WelcomeEvent
    data object NavigateToLogin : WelcomeEvent
}

class WelcomeViewModel : ViewModel() {

    private val _eventFlow = MutableSharedFlow<WelcomeEvent>()
    val eventFlow: SharedFlow<WelcomeEvent> = _eventFlow.asSharedFlow()

    fun onGetStartedClicked() {
        viewModelScope.launch {
            _eventFlow.emit(WelcomeEvent.NavigateToGetStarted)
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _eventFlow.emit(WelcomeEvent.NavigateToLogin)
        }
    }
}
