package com.simats.enhancedcyclecareai.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RegisterUiEvent {
    data object NavigateToHome : RegisterUiEvent
    data object NavigateToLogin : RegisterUiEvent
    data object NavigateBack : RegisterUiEvent
    data class ShowError(val message: String) : RegisterUiEvent
}

class RegisterViewModel : ViewModel() {

    private val _fullName = MutableStateFlow("Ananya Sharma")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _emailOrPhone = MutableStateFlow("ananya.sharma@example.com")
    val emailOrPhone: StateFlow<String> = _emailOrPhone.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isTermsAccepted = MutableStateFlow(true)
    val isTermsAccepted: StateFlow<Boolean> = _isTermsAccepted.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible: StateFlow<Boolean> = _isConfirmPasswordVisible.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RegisterUiEvent>()
    val uiEvent: SharedFlow<RegisterUiEvent> = _uiEvent.asSharedFlow()

    fun onFullNameChange(value: String) {
        _fullName.value = value
    }

    fun onEmailOrPhoneChange(value: String) {
        _emailOrPhone.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
    }

    fun onTermsAcceptedChange(value: Boolean) {
        _isTermsAccepted.value = value
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    fun onRegisterClicked() {
        viewModelScope.launch {
            if (!_isTermsAccepted.value) {
                _uiEvent.emit(RegisterUiEvent.ShowError("Please agree to the Terms and Privacy Notice"))
                return@launch
            }
            if (_password.value.isNotEmpty() && _password.value != _confirmPassword.value) {
                _uiEvent.emit(RegisterUiEvent.ShowError("Passwords do not match"))
                return@launch
            }
            _uiEvent.emit(RegisterUiEvent.NavigateToHome)
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _uiEvent.emit(RegisterUiEvent.NavigateToLogin)
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(RegisterUiEvent.NavigateBack)
        }
    }
}
