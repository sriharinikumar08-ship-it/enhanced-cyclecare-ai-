package com.simats.enhancedcyclecareai.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiEvent {
    data object NavigateToHome : LoginUiEvent
    data object NavigateToRegister : LoginUiEvent
    data object NavigateBack : LoginUiEvent
    data class ShowError(val message: String) : LoginUiEvent
}

class LoginViewModel : ViewModel() {

    private val _emailOrMobile = MutableStateFlow("ananya.sharma@example.com")
    val emailOrMobile: StateFlow<String> = _emailOrMobile.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> = _uiEvent.asSharedFlow()

    fun onEmailOrMobileChange(value: String) {
        _emailOrMobile.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            if (_emailOrMobile.value.isBlank()) {
                _uiEvent.emit(LoginUiEvent.ShowError("Please enter email or mobile number"))
                return@launch
            }
            _uiEvent.emit(LoginUiEvent.NavigateToHome)
        }
    }

    fun onForgotPasswordClicked() {
        viewModelScope.launch {
            _uiEvent.emit(LoginUiEvent.ShowError("Password reset link sent!"))
        }
    }

    fun onCreateAccountClicked() {
        viewModelScope.launch {
            _uiEvent.emit(LoginUiEvent.NavigateToRegister)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(LoginUiEvent.NavigateBack)
        }
    }
}
