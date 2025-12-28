package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.authentication.authentication
import com.example.presentation.ui_state.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUIState())
    val loginUiState: StateFlow<LoginUIState> = _loginUiState

    fun onNameChange(name: String) {
        _loginUiState.value = _loginUiState.value.copy(
            name = name
        )
    }

    fun onPhoneNoChange(phoneNo: String) {
        _loginUiState.value = _loginUiState.value.copy(
            phoneNo = phoneNo
        )
    }

    fun onNext(): String {
        return ""
    }

    fun onGoogle(): String {
        return ""
    }

    suspend fun authenticate(): String? {
        return authentication()
    }
}